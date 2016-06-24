package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.PFA;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class pfaReader {
    public static boolean pfaReader(XMLStreamReader xsr, OutputRowCollector _pfaRowCollector) throws JAXBException {

        String date = "";
        String type = "";
        Object[] resultObj;


        JAXBContext jc = JAXBContext.newInstance(PFA.class);
        Unmarshaller unmarshallerCountry = jc.createUnmarshaller();
        JAXBElement<PFA> je = unmarshallerCountry.unmarshal(xsr, PFA.class);


        PFA pfa = je.getValue();

        date = pfa.getDate();
        type = pfa.getType();


        resultObj = new Object[]{date, type};
        _pfaRowCollector.putValues(resultObj);
        System.out.println(date);

        return true;

    }
}
