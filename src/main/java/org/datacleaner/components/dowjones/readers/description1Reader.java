package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.Description1List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class description1Reader {

    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(Description1List.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static boolean description1Reader(XMLStreamReader xsr, OutputRowCollector _description1RowCollector) throws JAXBException {

        String value = "";
        String description1Id = "";
        String recordType = "";

        Object[] resultObj;

        JAXBElement<Description1List> je = unmarshaller.unmarshal(xsr, Description1List.class);


        Description1List dl = je.getValue();

        for (int i = 0; i < dl.getDescription1Name().size(); i++) {

            description1Id = dl.getDescription1Name().get(i).getDescription1Id();
            value = dl.getDescription1Name().get(i).getValue();
            recordType = dl.getDescription1Name().get(i).getRecordType();

            resultObj = new Object[]{description1Id, value, recordType};
            _description1RowCollector.putValues(resultObj);

        }

        return true;

    }


}
