package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.Description2List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class description2Reader {
    public static boolean description2Reader(XMLStreamReader xsr, OutputRowCollector _description2RowCollector) throws JAXBException {


        String description1Id = "";
        String description2Id = "";
        String value = "";

        Object[] resultObj;


        JAXBContext jc = JAXBContext.newInstance(Description2List.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<Description2List> je = unmarshaller.unmarshal(xsr, Description2List.class);


        Description2List dl = je.getValue();

        for (int i = 0; i < dl.getDescription2Name().size(); i++) {

            description2Id = dl.getDescription2Name().get(i).getDescription2Id();
            value = dl.getDescription2Name().get(i).getValue();
            description1Id = dl.getDescription2Name().get(i).getDescription1Id();

            resultObj = new Object[]{description2Id, value, description1Id};
            _description2RowCollector.putValues(resultObj);

        }

        return true;

    }


}
