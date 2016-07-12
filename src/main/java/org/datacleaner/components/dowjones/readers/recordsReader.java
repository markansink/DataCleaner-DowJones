package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.Records;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

import static org.datacleaner.components.dowjones.readers.entityReader.entityReader;
import static org.datacleaner.components.dowjones.readers.personReader.personReader;

/**
 * Created by mansink on 31-05-16.
 */
public class recordsReader {
    public static boolean recordsReader(XMLStreamReader xsr, OutputRowCollector _personRowCollector,
                                        OutputRowCollector _personNameRowCollector, OutputRowCollector _personDescRowCollector,
                                        OutputRowCollector _personRoleRowCollector, OutputRowCollector _personDateRowCollector,
                                        OutputRowCollector _personPlaceRowCollector, OutputRowCollector _personSanctionRowCollector,
                                        OutputRowCollector _personAddressRowCollector, OutputRowCollector _personCountryRowCollector,
                                        OutputRowCollector _personIDRowCollector, OutputRowCollector _personSourceRowCollector,
                                        OutputRowCollector _personImageRowCollector, OutputRowCollector _entityRowCollector) throws JAXBException {


        JAXBContext recordsJC = JAXBContext.newInstance(Records.class);
        Unmarshaller unmarshallerCountry = recordsJC.createUnmarshaller();
        JAXBElement<Records> records = unmarshallerCountry.unmarshal(xsr, Records.class);

        Records record = records.getValue();

        for (int p = 0; p < record.getPerson().size(); p++) {

            personReader(xsr, _personRowCollector, _personNameRowCollector,
                    _personDescRowCollector, _personRoleRowCollector, _personDateRowCollector,
                    _personPlaceRowCollector, _personSanctionRowCollector, _personAddressRowCollector,
                    _personCountryRowCollector, _personIDRowCollector, _personSourceRowCollector, _personImageRowCollector);

        }

        for (int e = 0; e < record.getEntity().size(); e++) {
            entityReader(xsr, _entityRowCollector);
        }

        return true;

    }


}
