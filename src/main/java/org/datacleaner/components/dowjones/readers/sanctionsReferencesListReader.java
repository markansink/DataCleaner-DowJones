package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.SanctionsReferencesList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class sanctionsReferencesListReader {
    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(SanctionsReferencesList.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static boolean sanctionsReferencesListReader(XMLStreamReader xsr, OutputRowCollector _occupationRowCollector) throws JAXBException {

        String code = "";
        String name = "";
        String status = "";
        String description2Id = "";
        Object[] resultObj;
        JAXBElement<SanctionsReferencesList> je = unmarshaller.unmarshal(xsr, SanctionsReferencesList.class);


        SanctionsReferencesList sanction = je.getValue();

        for (int i = 0; i < sanction.getReferenceName().size(); i++) {
            code = sanction.getReferenceName().get(i).getCode();
            name = sanction.getReferenceName().get(i).getName();
            status = sanction.getReferenceName().get(i).getStatus();
            description2Id = sanction.getReferenceName().get(i).getDescription2Id();

            resultObj = new Object[]{code, name, status, description2Id};
            _occupationRowCollector.putValues(resultObj);

        }

        return true;

    }


}
