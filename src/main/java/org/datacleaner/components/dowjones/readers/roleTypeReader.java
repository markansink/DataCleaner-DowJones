package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.RoleTypeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class roleTypeReader {
    public static boolean roleTypeReader(XMLStreamReader xsr, OutputRowCollector _roleTypeRowCollector) throws JAXBException {


        String id = "";
        String name = "";


        Object[] resultObj;


        JAXBContext jc = JAXBContext.newInstance(RoleTypeList.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<RoleTypeList> je = unmarshaller.unmarshal(xsr, RoleTypeList.class);


        RoleTypeList dl = je.getValue();

        for (int i = 0; i < dl.getRoleType().size(); i++) {

            id = dl.getRoleType().get(i).getId();
            name = dl.getRoleType().get(i).getName();


            resultObj = new Object[]{id, name};
            _roleTypeRowCollector.putValues(resultObj);

        }

        return true;

    }


}
