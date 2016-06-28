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
import static org.datacleaner.components.dowjones.readers.dateTypeReader.dateTypeReader;
import static org.datacleaner.components.dowjones.readers.description1Reader.description1Reader;
import static org.datacleaner.components.dowjones.readers.description2Reader.description2Reader;
import static org.datacleaner.components.dowjones.readers.description3Reader.description3Reader;
import static org.datacleaner.components.dowjones.readers.nameTypeReader.nameTypeReader;
import static org.datacleaner.components.dowjones.readers.occupationReader.occupationReader;
import static org.datacleaner.components.dowjones.readers.personReader.personReader;
import static org.datacleaner.components.dowjones.readers.relationshipReader.relationshipReader;
import static org.datacleaner.components.dowjones.readers.roleTypeReader.roleTypeReader;
import static org.datacleaner.components.dowjones.readers.sanctionsReferencesListReader.sanctionsReferencesListReader;

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
    private OutputRowCollector _sanctionsReferencesRowCollector;
    private OutputRowCollector _description1RowCollector;
    private OutputRowCollector _description2RowCollector;
    private OutputRowCollector _description3RowCollector;
    private OutputRowCollector _dateTypeRowCollector;
    private OutputRowCollector _nameTypeRowCollector;
    private OutputRowCollector _roleTypeRowCollector;
    private OutputRowCollector _personRowCollector;
    private OutputRowCollector _personNameRowCollector;
    private OutputRowCollector _personDescRowCollector;
    private OutputRowCollector _personRoleRowCollector;
    private OutputRowCollector _personDateRowCollector;
    private OutputRowCollector _personPlaceRowCollector;
    private OutputRowCollector _personSanctionRowCollector;
    private OutputRowCollector _personAddressRowCollector;
    private OutputRowCollector _personCountryRowCollector;

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
        try {
            XMLStreamReader xsr = xif.createXMLStreamReader(xml);
            while (xsr.hasNext()) {
                int eventType = xsr.next();

                switch (eventType) {

                    case (XMLStreamReader.START_ELEMENT):
                        String elementName = xsr.getLocalName();

                        if (elementName.equals("CountryList")) {
                            countryReader(xsr, _countryRowCollector);
                        }
                        if (elementName.equals("OccupationList")) {
                            occupationReader(xsr, _occupationRowCollector);
                        }
                        if (elementName.equals("RelationshipList")) {
                            relationshipReader(xsr, _relationshipRowCollector);
                        }
                        if (elementName.equals("SanctionsReferencesList")) {
                            sanctionsReferencesListReader(xsr, _sanctionsReferencesRowCollector);
                        }
                        if (elementName.equals("Description1List")) {
                            description1Reader(xsr, _description1RowCollector);
                        }
                        if (elementName.equals("Description2List")) {
                            description2Reader(xsr, _description2RowCollector);
                        }
                        if (elementName.equals("Description3List")) {
                            description3Reader(xsr, _description3RowCollector);
                        }
                        if (elementName.equals("DateTypeList")) {
                            dateTypeReader(xsr, _dateTypeRowCollector);
                        }
                        if (elementName.equals("NameTypeList")) {
                            nameTypeReader(xsr, _nameTypeRowCollector);
                        }
                        if (elementName.equals("RoleTypeList")) {
                            roleTypeReader(xsr, _roleTypeRowCollector);
                        }
                        if (elementName.equals("Person")) {
                            personReader(xsr, _personRowCollector, _personNameRowCollector,
                                    _personDescRowCollector, _personRoleRowCollector, _personDateRowCollector,
                                    _personPlaceRowCollector, _personSanctionRowCollector, _personAddressRowCollector, _personCountryRowCollector);
                        }

                        break;
                }
            }
            xsr.close();
        } catch (XMLStreamException | JAXBException e) {
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
        occupationStreamBuilder.withColumn("Code", ColumnType.STRING);
        occupationStreamBuilder.withColumn("Name", ColumnType.STRING);

        final OutputDataStreamBuilder relationshipStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_RELATIONSHIPS);
        relationshipStreamBuilder.withColumn("Code", ColumnType.STRING);
        relationshipStreamBuilder.withColumn("Name", ColumnType.STRING);

        final OutputDataStreamBuilder referenceNameStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_REFERENCENAME);
        referenceNameStreamBuilder.withColumn("Code", ColumnType.STRING);
        referenceNameStreamBuilder.withColumn("Name", ColumnType.STRING);
        referenceNameStreamBuilder.withColumn("Status", ColumnType.STRING);
        referenceNameStreamBuilder.withColumn("Description2id", ColumnType.STRING);

        final OutputDataStreamBuilder description1StreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DESCRIPTION1);
        description1StreamBuilder.withColumn("Description1Id", ColumnType.STRING);
        description1StreamBuilder.withColumn("Value", ColumnType.STRING);
        description1StreamBuilder.withColumn("RecordType", ColumnType.STRING);

        final OutputDataStreamBuilder description2StreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DESCRIPTION2);
        description2StreamBuilder.withColumn("Description2Id", ColumnType.STRING);
        description2StreamBuilder.withColumn("Value", ColumnType.STRING);
        description2StreamBuilder.withColumn("Description1Id", ColumnType.STRING);

        final OutputDataStreamBuilder description3StreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DESCRIPTION3);
        description3StreamBuilder.withColumn("Description3Id", ColumnType.STRING);
        description3StreamBuilder.withColumn("Value", ColumnType.STRING);
        description3StreamBuilder.withColumn("Description2Id", ColumnType.STRING);

        final OutputDataStreamBuilder dateTypeStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DATETYPELIST);
        dateTypeStreamBuilder.withColumn("Id", ColumnType.STRING);
        dateTypeStreamBuilder.withColumn("Name", ColumnType.STRING);
        dateTypeStreamBuilder.withColumn("RecordType", ColumnType.STRING);

        final OutputDataStreamBuilder nameTypeStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_NAMETYPELIST);
        nameTypeStreamBuilder.withColumn("NameTypeID", ColumnType.STRING);
        nameTypeStreamBuilder.withColumn("NameType", ColumnType.STRING);
        nameTypeStreamBuilder.withColumn("RecordType", ColumnType.STRING);

        final OutputDataStreamBuilder roleTypeStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ROLETYPELIST);
        roleTypeStreamBuilder.withColumn("Id", ColumnType.STRING);
        roleTypeStreamBuilder.withColumn("Name", ColumnType.STRING);

        final OutputDataStreamBuilder personStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSON);
        personStreamBuilder.withColumn("ID", ColumnType.STRING);
        personStreamBuilder.withColumn("Action", ColumnType.STRING);
        personStreamBuilder.withColumn("Date", ColumnType.STRING);
        personStreamBuilder.withColumn("Gender", ColumnType.STRING);
        personStreamBuilder.withColumn("ActiveStatus", ColumnType.STRING);
        personStreamBuilder.withColumn("Deceased", ColumnType.STRING);
        personStreamBuilder.withColumn("ProfileNotes", ColumnType.STRING);

        final OutputDataStreamBuilder personNameStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONNAME);
        personNameStreamBuilder.withColumn("ID", ColumnType.STRING);
        personNameStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personNameStreamBuilder.withColumn("NameType", ColumnType.STRING);
        personNameStreamBuilder.withColumn("FirstName", ColumnType.STRING);
        personNameStreamBuilder.withColumn("MiddleName", ColumnType.STRING);
        personNameStreamBuilder.withColumn("SurName", ColumnType.STRING);
        personNameStreamBuilder.withColumn("MaidenName", ColumnType.STRING);
        personNameStreamBuilder.withColumn("Suffix", ColumnType.STRING);
        personNameStreamBuilder.withColumn("TitleHonorific", ColumnType.STRING);
        personNameStreamBuilder.withColumn("SingleStringName", ColumnType.STRING);
        personNameStreamBuilder.withColumn("OriginalScriptName", ColumnType.STRING);

        final OutputDataStreamBuilder personDescStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONDESC);
        personDescStreamBuilder.withColumn("ID", ColumnType.STRING);
        personDescStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personDescStreamBuilder.withColumn("Description1", ColumnType.STRING);
        personDescStreamBuilder.withColumn("Description2", ColumnType.STRING);
        personDescStreamBuilder.withColumn("Description3", ColumnType.STRING);

        final OutputDataStreamBuilder personRoleStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONROLE);
        personRoleStreamBuilder.withColumn("ID", ColumnType.STRING);
        personRoleStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personRoleStreamBuilder.withColumn("RoleType", ColumnType.STRING);
        personRoleStreamBuilder.withColumn("OccTitle", ColumnType.STRING);
        personRoleStreamBuilder.withColumn("OccCat", ColumnType.STRING);
        personRoleStreamBuilder.withColumn("SinceDate", ColumnType.STRING);
        personRoleStreamBuilder.withColumn("ToDate", ColumnType.STRING);

        final OutputDataStreamBuilder personDateStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONDATE);
        personDateStreamBuilder.withColumn("ID", ColumnType.STRING);
        personDateStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personDateStreamBuilder.withColumn("DateType", ColumnType.STRING);
        personDateStreamBuilder.withColumn("Date", ColumnType.STRING);

        final OutputDataStreamBuilder personPlaceStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONPLACE);
        personPlaceStreamBuilder.withColumn("ID", ColumnType.STRING);
        personPlaceStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personPlaceStreamBuilder.withColumn("BirthPlace", ColumnType.STRING);

        final OutputDataStreamBuilder personSanctionStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONSANCTION);
        personSanctionStreamBuilder.withColumn("ID", ColumnType.STRING);
        personSanctionStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personSanctionStreamBuilder.withColumn("Reference", ColumnType.STRING);
        personSanctionStreamBuilder.withColumn("SinceDate", ColumnType.STRING);
        personSanctionStreamBuilder.withColumn("ToDate", ColumnType.STRING);

        final OutputDataStreamBuilder personAddressStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONADDRESS);
        personAddressStreamBuilder.withColumn("ID", ColumnType.STRING);
        personAddressStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personAddressStreamBuilder.withColumn("AddressLine", ColumnType.STRING);
        personAddressStreamBuilder.withColumn("City", ColumnType.STRING);
        personAddressStreamBuilder.withColumn("Country", ColumnType.STRING);
        personAddressStreamBuilder.withColumn("URL", ColumnType.STRING);

        final OutputDataStreamBuilder personCountryStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONCOUNTRY);
        personCountryStreamBuilder.withColumn("ID", ColumnType.STRING);
        personCountryStreamBuilder.withColumn("PersonID", ColumnType.STRING);
        personCountryStreamBuilder.withColumn("CountryType", ColumnType.STRING);
        personCountryStreamBuilder.withColumn("Country", ColumnType.STRING);

        return new OutputDataStream[]{
                occupationStreamBuilder.toOutputDataStream(), countryStreamBuilder.toOutputDataStream(),
                relationshipStreamBuilder.toOutputDataStream(), referenceNameStreamBuilder.toOutputDataStream(),
                description1StreamBuilder.toOutputDataStream(), description2StreamBuilder.toOutputDataStream(),
                description3StreamBuilder.toOutputDataStream(), dateTypeStreamBuilder.toOutputDataStream(),
                nameTypeStreamBuilder.toOutputDataStream(), roleTypeStreamBuilder.toOutputDataStream(),
                personStreamBuilder.toOutputDataStream(), personNameStreamBuilder.toOutputDataStream(),
                personDescStreamBuilder.toOutputDataStream(), personRoleStreamBuilder.toOutputDataStream(),
                personDateStreamBuilder.toOutputDataStream(), personPlaceStreamBuilder.toOutputDataStream(),
                personSanctionStreamBuilder.toOutputDataStream(), personAddressStreamBuilder.toOutputDataStream()
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
        if (outputDataStream.getName().equals(OUTPUT_STREAM_REFERENCENAME)) {
            _sanctionsReferencesRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_DESCRIPTION1)) {
            _description1RowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_DESCRIPTION2)) {
            _description2RowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_DESCRIPTION3)) {
            _description3RowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_DATETYPELIST)) {
            _dateTypeRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_NAMETYPELIST)) {
            _nameTypeRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ROLETYPELIST)) {
            _roleTypeRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSON)) {
            _personRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONNAME)) {
            _personNameRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONDESC)) {
            _personDescRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONROLE)) {
            _personRoleRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONDATE)) {
            _personDateRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONPLACE)) {
            _personPlaceRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONSANCTION)) {
            _personSanctionRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONADDRESS)) {
            _personAddressRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONCOUNTRY)) {
            _personCountryRowCollector = outputRowCollector;
        }
    }
}



