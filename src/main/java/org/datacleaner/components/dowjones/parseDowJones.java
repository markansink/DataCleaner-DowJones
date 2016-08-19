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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.datacleaner.components.dowjones.outputDataStreams.*;
import static org.datacleaner.components.dowjones.readers.countryReader.countryReader;
import static org.datacleaner.components.dowjones.readers.dateTypeReader.dateTypeReader;
import static org.datacleaner.components.dowjones.readers.description1Reader.description1Reader;
import static org.datacleaner.components.dowjones.readers.description2Reader.description2Reader;
import static org.datacleaner.components.dowjones.readers.description3Reader.description3Reader;
import static org.datacleaner.components.dowjones.readers.entityReader.entityReader;
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
    // reference output rows
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
    // person output rows
    private OutputRowCollector _personRowCollector;
    private OutputRowCollector _personNameRowCollector;
    private OutputRowCollector _personDescRowCollector;
    private OutputRowCollector _personRoleRowCollector;
    private OutputRowCollector _personDateRowCollector;
    private OutputRowCollector _personPlaceRowCollector;
    private OutputRowCollector _personSanctionRowCollector;
    private OutputRowCollector _personAddressRowCollector;
    private OutputRowCollector _personCountryRowCollector;
    private OutputRowCollector _personIDRowCollector;
    private OutputRowCollector _personSourceRowCollector;
    private OutputRowCollector _personImageRowCollector;
    // entity output rows
    private OutputRowCollector _entityRowCollector;
    private OutputRowCollector _entityNameRowCollector;
    private OutputRowCollector _entityDescRowCollector;
    private OutputRowCollector _entityDateRowCollector;
    private OutputRowCollector _entitySanctionRowCollector;
    private OutputRowCollector _entityAddressRowCollector;
    private OutputRowCollector _entityCountryRowCollector;
    private OutputRowCollector _entityIDRowCollector;
    private OutputRowCollector _entitySourceRowCollector;

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
        String elementNameNew = "";
        String elementName = "";
        XMLInputFactory xif = XMLInputFactory.newFactory();

        StreamSource xml = new StreamSource(fileURL);

        try {
            XMLStreamReader xsr = xif.createXMLStreamReader(
                    new InputStreamReader(
                            new FileInputStream(fileURL),
                            Charset.forName("UTF8")));

            // start with the first element:
            xsr.nextTag();

            while (xsr.hasNext()) {


                // get new elemenName only works on start elements, so we need a try catch
                try {
                    elementNameNew = xsr.getLocalName();
                    // check elementname with previous elementname.
                    // Sometimes (delta file) we need to tell the parser to go to the next element and sometimes it is already at the next element.
                    // need to skip this on the person and entity tags
                    if (elementNameNew.equals(elementName)) {
                        if
                                (!Arrays.asList("Entity", "Person").contains(elementNameNew)) {
                            xsr.next();
                        }

                    }
                } catch (Exception e) {
                    xsr.next();
                }

                int eventType = xsr.getEventType();
                if (XMLStreamReader.START_ELEMENT == eventType) {
                    elementName = xsr.getLocalName();

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
                                _personPlaceRowCollector, _personSanctionRowCollector, _personAddressRowCollector,
                                _personCountryRowCollector, _personIDRowCollector, _personSourceRowCollector, _personImageRowCollector);

                    }
                    if (elementName.equals("Entity")) {
                        entityReader(xsr, _entityRowCollector, _entityNameRowCollector, _entityDescRowCollector,
                                _entityDateRowCollector, _entitySanctionRowCollector,
                                _entityAddressRowCollector, _entityCountryRowCollector,
                                _entityIDRowCollector, _entitySourceRowCollector);

                    }
                    ;
                } else {
                    xsr.next();
                }
            }
            xsr.close();
        } catch (XMLStreamException | JAXBException | FileNotFoundException e) {
            e.printStackTrace();

        }
        return new Object[]{date, type};
    }

    @Override
    public OutputDataStream[] getOutputDataStreams() {
        final OutputDataStreamBuilder countryStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_COUNTRY);

        countryStreamBuilder.withColumn("Code", ColumnType.NVARCHAR);
        countryStreamBuilder.withColumn("Name", ColumnType.NVARCHAR);
        countryStreamBuilder.withColumn("IsTerritory", ColumnType.NVARCHAR);
        countryStreamBuilder.withColumn("ProfileURL", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder occupationStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_OCCUPATION);
        occupationStreamBuilder.withColumn("Code", ColumnType.NVARCHAR);
        occupationStreamBuilder.withColumn("Name", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder relationshipStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_RELATIONSHIPS);
        relationshipStreamBuilder.withColumn("Code", ColumnType.NVARCHAR);
        relationshipStreamBuilder.withColumn("Name", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder referenceNameStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_REFERENCENAME);
        referenceNameStreamBuilder.withColumn("Code", ColumnType.NVARCHAR);
        referenceNameStreamBuilder.withColumn("Name", ColumnType.NVARCHAR);
        referenceNameStreamBuilder.withColumn("Status", ColumnType.NVARCHAR);
        referenceNameStreamBuilder.withColumn("Description2id", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder description1StreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DESCRIPTION1);
        description1StreamBuilder.withColumn("Description1Id", ColumnType.NVARCHAR);
        description1StreamBuilder.withColumn("Value", ColumnType.NVARCHAR);
        description1StreamBuilder.withColumn("RecordType", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder description2StreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DESCRIPTION2);
        description2StreamBuilder.withColumn("Description2Id", ColumnType.NVARCHAR);
        description2StreamBuilder.withColumn("Value", ColumnType.NVARCHAR);
        description2StreamBuilder.withColumn("Description1Id", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder description3StreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DESCRIPTION3);
        description3StreamBuilder.withColumn("Description3Id", ColumnType.NVARCHAR);
        description3StreamBuilder.withColumn("Value", ColumnType.NVARCHAR);
        description3StreamBuilder.withColumn("Description2Id", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder dateTypeStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_DATETYPELIST);
        dateTypeStreamBuilder.withColumn("Id", ColumnType.NVARCHAR);
        dateTypeStreamBuilder.withColumn("Name", ColumnType.NVARCHAR);
        dateTypeStreamBuilder.withColumn("RecordType", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder nameTypeStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_NAMETYPELIST);
        nameTypeStreamBuilder.withColumn("NameTypeID", ColumnType.NVARCHAR);
        nameTypeStreamBuilder.withColumn("NameType", ColumnType.NVARCHAR);
        nameTypeStreamBuilder.withColumn("RecordType", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder roleTypeStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ROLETYPELIST);
        roleTypeStreamBuilder.withColumn("Id", ColumnType.NVARCHAR);
        roleTypeStreamBuilder.withColumn("Name", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSON);
        personStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personStreamBuilder.withColumn("Action", ColumnType.NVARCHAR);
        personStreamBuilder.withColumn("Date", ColumnType.NVARCHAR);
        personStreamBuilder.withColumn("Gender", ColumnType.NVARCHAR);
        personStreamBuilder.withColumn("ActiveStatus", ColumnType.NVARCHAR);
        personStreamBuilder.withColumn("Deceased", ColumnType.NVARCHAR);
        personStreamBuilder.withColumn("ProfileNotes", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personNameStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONNAME);
        personNameStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("NameType", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("FirstName", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("MiddleName", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("SurName", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("MaidenName", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("Suffix", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("TitleHonorific", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("SingleStringName", ColumnType.NVARCHAR);
        personNameStreamBuilder.withColumn("OriginalScriptName", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personDescStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONDESC);
        personDescStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personDescStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personDescStreamBuilder.withColumn("Description1", ColumnType.NVARCHAR);
        personDescStreamBuilder.withColumn("Description2", ColumnType.NVARCHAR);
        personDescStreamBuilder.withColumn("Description3", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personRoleStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONROLE);
        personRoleStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personRoleStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personRoleStreamBuilder.withColumn("RoleType", ColumnType.NVARCHAR);
        personRoleStreamBuilder.withColumn("OccTitle", ColumnType.NVARCHAR);
        personRoleStreamBuilder.withColumn("OccCat", ColumnType.NVARCHAR);
        personRoleStreamBuilder.withColumn("SinceDate", ColumnType.NVARCHAR);
        personRoleStreamBuilder.withColumn("ToDate", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personDateStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONDATE);
        personDateStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personDateStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personDateStreamBuilder.withColumn("DateType", ColumnType.NVARCHAR);
        personDateStreamBuilder.withColumn("Date", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personPlaceStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONPLACE);
        personPlaceStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personPlaceStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personPlaceStreamBuilder.withColumn("BirthPlace", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personSanctionStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONSANCTION);
        personSanctionStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personSanctionStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personSanctionStreamBuilder.withColumn("Reference", ColumnType.NVARCHAR);
        personSanctionStreamBuilder.withColumn("SinceDate", ColumnType.NVARCHAR);
        personSanctionStreamBuilder.withColumn("ToDate", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personAddressStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONADDRESS);
        personAddressStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personAddressStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personAddressStreamBuilder.withColumn("AddressLine", ColumnType.NVARCHAR);
        personAddressStreamBuilder.withColumn("City", ColumnType.NVARCHAR);
        personAddressStreamBuilder.withColumn("Country", ColumnType.NVARCHAR);
        personAddressStreamBuilder.withColumn("URL", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personCountryStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONCOUNTRY);
        personCountryStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personCountryStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personCountryStreamBuilder.withColumn("CountryType", ColumnType.NVARCHAR);
        personCountryStreamBuilder.withColumn("Country", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personIdStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONID);
        personIdStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personIdStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personIdStreamBuilder.withColumn("IDType", ColumnType.NVARCHAR);
        personIdStreamBuilder.withColumn("IDValue", ColumnType.NVARCHAR);
        personIdStreamBuilder.withColumn("IDNotes", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personSourceStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONSOURCEDESCRIPTION);
        personSourceStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personSourceStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personSourceStreamBuilder.withColumn("Source", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder personImageStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_PERSONIMAGES);
        personImageStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        personImageStreamBuilder.withColumn("PersonID", ColumnType.NVARCHAR);
        personImageStreamBuilder.withColumn("URL", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entityStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITY);
        entityStreamBuilder.withColumn("EntityId", ColumnType.NVARCHAR);
        entityStreamBuilder.withColumn("Action", ColumnType.NVARCHAR);
        entityStreamBuilder.withColumn("ActiveStatus", ColumnType.NVARCHAR);
        entityStreamBuilder.withColumn("ProfileNotes", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entityNameStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYNAME);
        entityNameStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entityNameStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entityNameStreamBuilder.withColumn("NameType", ColumnType.NVARCHAR);
        entityNameStreamBuilder.withColumn("EntityName", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entityDescStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYDESC);
        entityDescStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entityDescStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entityDescStreamBuilder.withColumn("Description1", ColumnType.NVARCHAR);
        entityDescStreamBuilder.withColumn("Description2", ColumnType.NVARCHAR);
        entityDescStreamBuilder.withColumn("Description3", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entityDateStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYDATE);
        entityDateStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entityDateStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entityDateStreamBuilder.withColumn("DateType", ColumnType.NVARCHAR);
        entityDateStreamBuilder.withColumn("Date", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entitySanctionStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYSANCTION);
        entitySanctionStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entitySanctionStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entitySanctionStreamBuilder.withColumn("Reference", ColumnType.NVARCHAR);
        entitySanctionStreamBuilder.withColumn("SinceDate", ColumnType.NVARCHAR);
        entitySanctionStreamBuilder.withColumn("ToDate", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entityAddressStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYADDRESS);
        entityAddressStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entityAddressStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entityAddressStreamBuilder.withColumn("AddressLine", ColumnType.NVARCHAR);
        entityAddressStreamBuilder.withColumn("City", ColumnType.NVARCHAR);
        entityAddressStreamBuilder.withColumn("Country", ColumnType.NVARCHAR);
        entityAddressStreamBuilder.withColumn("URL", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entityCountryStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYCOUNTRY);
        entityCountryStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entityCountryStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entityCountryStreamBuilder.withColumn("CountryType", ColumnType.NVARCHAR);
        entityCountryStreamBuilder.withColumn("Country", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entityIdStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYID);
        entityIdStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entityIdStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entityIdStreamBuilder.withColumn("IDType", ColumnType.NVARCHAR);
        entityIdStreamBuilder.withColumn("IDValue", ColumnType.NVARCHAR);
        entityIdStreamBuilder.withColumn("IDNotes", ColumnType.NVARCHAR);

        final OutputDataStreamBuilder entitySourceStreamBuilder = OutputDataStreams.pushDataStream(OUTPUT_STREAM_ENTITYSOURCEDESCRIPTION);
        entitySourceStreamBuilder.withColumn("ID", ColumnType.NVARCHAR);
        entitySourceStreamBuilder.withColumn("EntityID", ColumnType.NVARCHAR);
        entitySourceStreamBuilder.withColumn("Source", ColumnType.NVARCHAR);

        return new OutputDataStream[]{
                occupationStreamBuilder.toOutputDataStream(), countryStreamBuilder.toOutputDataStream(),
                relationshipStreamBuilder.toOutputDataStream(), referenceNameStreamBuilder.toOutputDataStream(),
                description1StreamBuilder.toOutputDataStream(), description2StreamBuilder.toOutputDataStream(),
                description3StreamBuilder.toOutputDataStream(), dateTypeStreamBuilder.toOutputDataStream(),
                nameTypeStreamBuilder.toOutputDataStream(), roleTypeStreamBuilder.toOutputDataStream(),
                personStreamBuilder.toOutputDataStream(), personNameStreamBuilder.toOutputDataStream(),
                personDescStreamBuilder.toOutputDataStream(), personRoleStreamBuilder.toOutputDataStream(),
                personDateStreamBuilder.toOutputDataStream(), personPlaceStreamBuilder.toOutputDataStream(),
                personSanctionStreamBuilder.toOutputDataStream(), personAddressStreamBuilder.toOutputDataStream(),
                personIdStreamBuilder.toOutputDataStream(), personImageStreamBuilder.toOutputDataStream(),
                personSourceStreamBuilder.toOutputDataStream(), personCountryStreamBuilder.toOutputDataStream(),
                entityStreamBuilder.toOutputDataStream(), entityNameStreamBuilder.toOutputDataStream(),
                entityDescStreamBuilder.toOutputDataStream(), entityDateStreamBuilder.toOutputDataStream(),
                entitySanctionStreamBuilder.toOutputDataStream(), entityAddressStreamBuilder.toOutputDataStream(),
                entityCountryStreamBuilder.toOutputDataStream(), entityIdStreamBuilder.toOutputDataStream(),
                entitySourceStreamBuilder.toOutputDataStream()
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
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONID)) {
            _personIDRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONSOURCEDESCRIPTION)) {
            _personSourceRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_PERSONIMAGES)) {
            _personImageRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITY)) {
            _entityRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYNAME)) {
            _entityNameRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYDESC)) {
            _entityDescRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYDATE)) {
            _entityDateRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYSANCTION)) {
            _entitySanctionRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYADDRESS)) {
            _entityAddressRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYCOUNTRY)) {
            _entityCountryRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYID)) {
            _entityIDRowCollector = outputRowCollector;
        }
        if (outputDataStream.getName().equals(OUTPUT_STREAM_ENTITYSOURCEDESCRIPTION)) {
            _entitySourceRowCollector = outputRowCollector;
        }
    }
}



