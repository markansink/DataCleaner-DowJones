package org.datacleaner.components.dowjones;


import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import static org.datacleaner.components.dowjones.readers.countryReader.countryReaderXML;
import static org.datacleaner.components.dowjones.readers.occupationReader.occupationReaderXML;
import static org.datacleaner.components.dowjones.readers.relationshipReader.relationshipReaderXML;


/**
 * Created by mansink on 31-05-16.
 */
public class parseXML {

    /**
     * @param args
     * @throws JAXBException
     */
    public static void main(String[] args) throws XMLStreamException, JAXBException {
        String next = "";
        XMLInputFactory xif = XMLInputFactory.newFactory();
//        StreamSource xml = new StreamSource("src/test/PFA_XSD_22-Dec-07.xsd.xml");
        StreamSource xml = new StreamSource("src/test/PFA2_201605252200_D.xml");
//        StreamSource xml = new StreamSource("src/test/PFA2_201510182200_F.xml");
//        StreamSource xml = new StreamSource("/Users/mansink/Java/DataCleaner_DowJones/src/test/PFA2_201510182200_F.xml");
        XMLStreamReader xsr = xif.createXMLStreamReader(xml);

        xsr.nextTag();
        while (xsr.hasNext()) {
            int eventType = xsr.next();

            switch (eventType) {

                case (XMLStreamReader.START_ELEMENT):
                    String elementName = xsr.getLocalName();
                    if (elementName.equals("CountryList")) {
                        elementName = countryReaderXML(xsr);
                    }
                    if (elementName.equals("OccupationList")) {
                        elementName = occupationReaderXML(xsr);
                    }
                    if (elementName.equals("RelationshipList")) {
                        elementName = relationshipReaderXML(xsr);
                    }

                    break;
            }
        }
        xsr.close();

    }
}
