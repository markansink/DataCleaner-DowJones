package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.DateTypeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class dateTypeReader {
    public static boolean dateTypeReader(XMLStreamReader xsr, OutputRowCollector _dateTypeRowCollector) throws JAXBException {


        String id = "";
        String name = "";
        String recordtype = "";

        Object[] resultObj;


        JAXBContext jc = JAXBContext.newInstance(DateTypeList.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<DateTypeList> je = unmarshaller.unmarshal(xsr, DateTypeList.class);


        DateTypeList dl = je.getValue();

        for (int i = 0; i < dl.getDateType().size(); i++) {

            id = dl.getDateType().get(i).getId();
            name = dl.getDateType().get(i).getName();
            recordtype = dl.getDateType().get(i).getRecordType();


            resultObj = new Object[]{id, name, recordtype};
            _dateTypeRowCollector.putValues(resultObj);

        }

        return true;

    }


}
