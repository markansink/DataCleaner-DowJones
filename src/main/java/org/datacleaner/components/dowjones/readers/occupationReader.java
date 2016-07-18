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
    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(OccupationList.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    public static boolean occupationReader(XMLStreamReader xsr, OutputRowCollector _occupationRowCollector) throws JAXBException {

        String code = "";
        String name = "";
        Object[] resultObj;

        JAXBElement<OccupationList> je = unmarshaller.unmarshal(xsr, OccupationList.class);


        OccupationList occupation = je.getValue();

        for (int i = 0; i < occupation.getOccupation().size(); i++) {
            code = occupation.getOccupation().get(i).getCode();
            name = occupation.getOccupation().get(i).getName();


            resultObj = new Object[]{code, name};
            _occupationRowCollector.putValues(resultObj);

        }

        return true;

    }

}
