package org.datacleaner.components.dowjones.xml;

import org.datacleaner.api.OutputRowCollector;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

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

/**
 * Created by marka on 7/11/2016.
 */
public class parseXML {
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

    //
    public void parseXML(String fileURL, String elementName, XMLInputFactory xif) {
        String elementNameNew;
        try {
            XMLStreamReader xsr = xif
                    .createXMLStreamReader(new FileReader(fileURL));

            // start with the first element:
            xsr.nextTag();

            while (xsr.hasNext()) {


                // get new elemenName only works on start elements, so we need a try catch
                try {
                    elementNameNew = xsr.getLocalName();
                    // check elementname with previous elementname.
                    // Sometimes (delta file) we need to tell the parser to go to the next element and sometimes it is already at the next element.

                    if (elementNameNew.equals(elementName) && !Objects.equals(elementNameNew, "Person")) {
                        xsr.nextTag();
                    }
                } catch (Exception e) {
                    xsr.nextTag();
                }


                int eventType = xsr.getEventType();
                switch (eventType) {

                    case (XMLStreamReader.START_ELEMENT):


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
                        break;
                }

            }
            xsr.close();
        } catch (XMLStreamException | JAXBException | FileNotFoundException e) {
            e.printStackTrace();

        }
    }
}
