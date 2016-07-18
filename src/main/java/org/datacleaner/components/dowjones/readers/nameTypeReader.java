package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.NameTypeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class nameTypeReader {
    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(NameTypeList.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static boolean nameTypeReader(XMLStreamReader xsr, OutputRowCollector _nameTypeRowCollector) throws JAXBException {


        String nameTypeId = "";
        String name = "";
        String recordtype = "";

        Object[] resultObj;
        JAXBElement<NameTypeList> je = unmarshaller.unmarshal(xsr, NameTypeList.class);


        NameTypeList dl = je.getValue();

        for (int i = 0; i < dl.getNameType().size(); i++) {

            nameTypeId = dl.getNameType().get(i).getNameTypeID();
            name = dl.getNameType().get(i).getValue();
            recordtype = dl.getNameType().get(i).getRecordType();

            resultObj = new Object[]{nameTypeId, name, recordtype};
            _nameTypeRowCollector.putValues(resultObj);

        }

        return true;

    }


}
