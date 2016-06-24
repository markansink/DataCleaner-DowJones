package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class personReader {

    public static boolean personReader(XMLStreamReader xsr, OutputRowCollector _personRowCollector) throws JAXBException {

        String id = "";
        String action = "";
        String date = "";
        String gender = "";
        String activeStatus = "";
        String deceased = "";
        String profileNotes = "";

        String description1 = "";
        String description2 = "";
        String description3 = "";
        String citizenshipCountry = "";
        String jurisdictionCountry = "";
        String residentCountry = "";
        String reportedAllegationCountry = "";


        Object[] resultObj;

        JAXBContext jc = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshallerCountry = jc.createUnmarshaller();
        JAXBElement<Person> persons = unmarshallerCountry.unmarshal(xsr, Person.class);

        Person person = persons.getValue();

        try {
            id = person.getId();
            action = person.getAction();
            date = person.getDate();
            gender = person.getGender();
            activeStatus = person.getActiveStatus();
            deceased = person.getDeceased();
            profileNotes = person.getProfileNotes();
            // for loop not really needed since there can be only 1 Description, but to be safe I used an for loop.
            for (int d = 0; d > person.getDescriptions().getDescription().size(); d++) {
                description1 = person.getDescriptions().getDescription().get(d).getDescription1();
                description2 = person.getDescriptions().getDescription().get(d).getDescription2();
                description3 = person.getDescriptions().getDescription().get(d).getDescription3();
            }
            resultObj = new Object[]{id, action, date, gender, activeStatus, deceased, profileNotes, description1, description2, description3};
            _personRowCollector.putValues(resultObj);

        } catch (Exception e) {
        }
        return true;
    }

}
