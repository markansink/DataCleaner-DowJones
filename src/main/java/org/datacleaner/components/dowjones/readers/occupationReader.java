package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.OccupationList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class occupationReader {
    public static String occupationReader(XMLStreamReader xsr, OutputRowCollector _occupationRowCollector) throws JAXBException {

        String code = "";
        String name = "";
        Object[] resultObj;


        JAXBContext jc = JAXBContext.newInstance(OccupationList.class);
        Unmarshaller unmarshallerCountry = jc.createUnmarshaller();
        JAXBElement<OccupationList> je = unmarshallerCountry.unmarshal(xsr, OccupationList.class);


        OccupationList occupation = je.getValue();

        for (int i = 0; i < occupation.getOccupation().size(); i++) {
            code = occupation.getOccupation().get(i).getCode();
            name = occupation.getOccupation().get(i).getName();


            resultObj = new Object[]{code, name};
            _occupationRowCollector.putValues(resultObj);

        }

        return xsr.getLocalName();

    }

    public static String occupationReaderXML(XMLStreamReader xsr) throws JAXBException {

        String code = "";
        String name = "";
        Object[] resultObj;


        JAXBContext jc = JAXBContext.newInstance(OccupationList.class);
        Unmarshaller unmarshallerCountry = jc.createUnmarshaller();
        JAXBElement<OccupationList> je = unmarshallerCountry.unmarshal(xsr, OccupationList.class);


        OccupationList occupation = je.getValue();

        for (int i = 0; i < occupation.getOccupation().size(); i++) {
            code = occupation.getOccupation().get(i).getCode();
            name = occupation.getOccupation().get(i).getName();

            System.out.println(name);
        }

        return xsr.getLocalName();
    }
}
