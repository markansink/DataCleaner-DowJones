package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.Entity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class entityReader {

    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(Entity.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static boolean entityReader(XMLStreamReader xsr, OutputRowCollector _entityRowCollector) throws JAXBException {

        String entityId = "";
        String action = "";
        String activeStatus = "";
        String profileNotes = "";

        Object[] resultObj;

        JAXBElement<Entity> entities = unmarshaller.unmarshal(xsr, Entity.class);

        Entity entity = entities.getValue();
        try {

            entityId = entity.getId();
            action = entity.getAction();
            activeStatus = entity.getActiveStatus();
            profileNotes = entity.getProfileNotes();

            resultObj = new Object[]{entityId, action, activeStatus, profileNotes};
            _entityRowCollector.putValues(resultObj);

            for (int e = 0; e < entity.getNameDetails().getName().size(); e++) {


            }


        } catch (Exception e) {
            e.printStackTrace();

        }


        return true;

    }


}
