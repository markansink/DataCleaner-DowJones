package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.RelationshipList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class relationshipReader {
    public static String relationshipReader(XMLStreamReader xsr, OutputRowCollector _relationshipRowCollector) throws JAXBException {

        String code = "";
        String name = "";

        Object[] resultObj;

        JAXBContext jc = JAXBContext.newInstance(RelationshipList.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<RelationshipList> jx = unmarshaller.unmarshal(xsr, RelationshipList.class);


        RelationshipList relationship = jx.getValue();

        for (int i = 0; i < relationship.getRelationship().size(); i++) {

            code = relationship.getRelationship().get(i).getCode();
            name = relationship.getRelationship().get(i).getName();


            resultObj = new Object[]{code, name};

            _relationshipRowCollector.putValues(resultObj);

        }

        return xsr.getLocalName();
    }

    public static String relationshipReaderXML(XMLStreamReader xsr) throws JAXBException {

        String code = "";
        String name = "";

        Object[] resultObj;

        JAXBContext jc = JAXBContext.newInstance(RelationshipList.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<RelationshipList> jx = unmarshaller.unmarshal(xsr, RelationshipList.class);


        RelationshipList relationship = jx.getValue();

        for (int i = 0; i < relationship.getRelationship().size(); i++) {

            code = relationship.getRelationship().get(i).getCode();
            name = relationship.getRelationship().get(i).getName();
            System.out.println(name);

            resultObj = new Object[]{code, name};


        }
        return xsr.getLocalName();
    }
}
