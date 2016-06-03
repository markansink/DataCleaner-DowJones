package org.datacleaner.components.dowjones;

import org.apache.metamodel.query.Query;
import org.apache.metamodel.schema.ColumnType;
import org.datacleaner.api.*;
import org.datacleaner.components.categories.ReferenceDataCategory;
import org.datacleaner.job.output.OutputDataStreamBuilder;
import org.datacleaner.job.output.OutputDataStreams;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import static org.datacleaner.components.dowjones.outputDataStreams.*;
import static org.datacleaner.components.dowjones.readers.countryReader.countryReader;
import static org.datacleaner.components.dowjones.readers.occupationReader.occupationReader;
import static org.datacleaner.components.dowjones.readers.relationshipReader.relationshipReader;
import static org.datacleaner.components.dowjones.readers.personReader.personReader;

@Named("DowJones Parser")
@Alias("DowJones")
@Description("Parse the Dow Jones xml feed")
@Categorized(ReferenceDataCategory.class)


public class parseDowJones implements Transformer, HasOutputDataStreams {

    @Inject
    @Configured("inputURL")
    InputColumn<String> url;

    @Inject
    @Provided
    OutputRowCollector outputRowCollector;

    private OutputRowCollector _countryRowCollector;
    private OutputRowCollector _occupationRowCollector;
    private OutputRowCollector _relationshipRowCollector;
    private OutputRowCollector _personRowCollector;

    @Override
    public OutputColumns getOutputColumns() {
//  The default output will display the date and type of the DowJones List
        String[] columnNames = {"date", "type"};
        Class<?>[] columnTypes = new Class[]{String.class, String.class};
        return new OutputColumns(columnNames, columnTypes);
    }

    @Override
    public Object[] transform(InputRow inputRow) {
        String fileURL = inputRow.getValue(url);
        String filename = fileURL.substring(fileURL.indexOf("PFA2_") + 5);
        String date = filename.substring(0, filename.indexOf("_"));
        String type = filename.substring(filename.indexOf("_") + 1, filename.indexOf("."));

        XMLInputFactory xif = XMLInputFactory.newFactory();
        StreamSource xml = new StreamSource(fileURL);
//        XMLStreamReader xsr = null;
//        XMLStreamReader xsr = xif.createXMLStreamReader(xml);

        try {
            XMLStreamReader xsr = xif.createXMLStreamReader(xml);
//            xsr.nextTag();
            while (xsr.hasNext()) {
                int eventType = xsr.next();

                switch (eventType) {

                    case (XMLStreamReader.START_ELEMENT):
                        String elementName = xsr.getLocalName();

                        if (elementName.equals("CountryList")) {
                            elementName = countryReader(xsr, _countryRowCollector);
                        }
                        if (elementName.equals("OccupationList")) {
                            elementName = occupationReader(xsr, _occupationRowCollector);
                        }
                        if (elementName.equals("RelationshipList")) {
                            elementName = relationshipReader(xsr, _relationshipRowCollector);
                        }
                        if (elementName.equals("Person")) {
                            elementName = personReader(xsr, _personRowCollector);
                        }

                        break;
                }
            }
            xsr.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new Object[]{date, type};
    }

    @Override
    public OutputDataStream[] getOutputDataStreams() {
        final OutputDataStreamBuilder countryStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_COUNTRY);

        countryStreamBuilder.withColumn("Code", ColumnType.STRING);
        countryStreamBuilder.withColumn("Name", ColumnType.STRING);
        countryStreamBuilder.withColumn("IsTerritory", ColumnType.STRING);
        countryStreamBuilder.withColumn("ProfileURL", ColumnType.STRING);

        final OutputDataStreamBuilder occupationStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_OCCUPATION);
        occupationStreamBuilder.withColumn("code", ColumnType.STRING);
        occupationStreamBuilder.withColumn("name", ColumnType.STRING);

        final OutputDataStreamBuilder relationshipStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_RELATIONSHIPS);
        relationshipStreamBuilder.withColumn("code", ColumnType.STRING);
        relationshipStreamBuilder.withColumn("name", ColumnType.STRING);


        final OutputDataStreamBuilder personStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSON);
        personStreamBuilder.withColumn("ID", ColumnType.STRING);
        personStreamBuilder.withColumn("ORG_ID", ColumnType.STRING);
        personStreamBuilder.withColumn("Action", ColumnType.STRING);
        personStreamBuilder.withColumn("NameType", ColumnType.STRING);
        personStreamBuilder.withColumn("FirstName", ColumnType.STRING);
        personStreamBuilder.withColumn("MiddleName", ColumnType.STRING);
        personStreamBuilder.withColumn("SurName", ColumnType.STRING);
        personStreamBuilder.withColumn("MaidenName", ColumnType.STRING);
        personStreamBuilder.withColumn("Suffix", ColumnType.STRING);
        personStreamBuilder.withColumn("TitleHonorific", ColumnType.STRING);
        personStreamBuilder.withColumn("SingleStringName", ColumnType.STRING);
        personStreamBuilder.withColumn("OriginalScriptName", ColumnType.STRING);
        personStreamBuilder.withColumn("Gender", ColumnType.STRING);
        personStreamBuilder.withColumn("ActiveStatus", ColumnType.STRING);
        personStreamBuilder.withColumn("Deceased", ColumnType.STRING);
        personStreamBuilder.withColumn("Description1", ColumnType.STRING);
        personStreamBuilder.withColumn("Description2", ColumnType.STRING);
        personStreamBuilder.withColumn("Description3", ColumnType.STRING);





        return new OutputDataStream[]{
                occupationStreamBuilder.toOutputDataStream(), countryStreamBuilder.toOutputDataStream(), relationshipStreamBuilder.toOutputDataStream(), personStreamBuilder.toOutputDataStream()
        };

    }


    @Override
    public void initializeOutputDataStream(OutputDataStream outputDataStream, Query query, OutputRowCollector outputRowCollector) {

        if (outputDataStream.getName().equals(OUTPUT_STREAM_OCCUPATION)) {
            _occupationRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_RELATIONSHIPS)) {
            _relationshipRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_COUNTRY)) {
            _countryRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSON)) {
            _personRowCollector = outputRowCollector;
        }
    }
}



