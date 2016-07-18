package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.Description3List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class description3Reader {

    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(Description3List.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    public static boolean description3Reader(XMLStreamReader xsr, OutputRowCollector _description3RowCollector) throws JAXBException {


        String description3Id = "";
        String description2Id = "";
        String value = "";

        Object[] resultObj;
        JAXBElement<Description3List> je = unmarshaller.unmarshal(xsr, Description3List.class);


        Description3List dl = je.getValue();

        for (int i = 0; i < dl.getDescription3Name().size(); i++) {

            description3Id = dl.getDescription3Name().get(i).getDescription3Id();
            value = dl.getDescription3Name().get(i).getValue();
            description2Id = dl.getDescription3Name().get(i).getDescription2Id();

            resultObj = new Object[]{description3Id, value, description2Id};
            _description3RowCollector.putValues(resultObj);

        }

        return true;

    }


}
