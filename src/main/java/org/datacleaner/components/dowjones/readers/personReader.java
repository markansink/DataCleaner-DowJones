package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.Name;
import org.datacleaner.components.dowjones.xml.NameValue;
import org.datacleaner.components.dowjones.xml.Person;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import java.util.List;

/**
 * Created by mansink on 31-05-16.
 */
public class personReader {

    public static String personReader(XMLStreamReader xsr, OutputRowCollector _personRowCollector) throws JAXBException {


        String description1 = "";
        String description2 = "";
        String description3 = "";
        Object[] resultObj;

        JAXBContext jc = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshallerCountry = jc.createUnmarshaller();
        JAXBElement<Person> persons = unmarshallerCountry.unmarshal(xsr, Person.class);

        Person person = persons.getValue();

        try {
            String org_id = person.getId();
            String action = person.getAction();
            String gender = person.getGender();
            String activeStatus = person.getActiveStatus();
            String deceased = person.getDeceased();
            // for loop not really needed since there can be only 1 Description, but to be safe I used an for loop.
            for (int d = 0; d > person.getDescriptions().getDescription().size(); d++) {
                description1 = person.getDescriptions().getDescription().get(d).getDescription1();
                description2 = person.getDescriptions().getDescription().get(d).getDescription2();
                description3 = person.getDescriptions().getDescription().get(d).getDescription3();
            }
            person.getNameDetails().getName().size();
            List<Name> nameDetails = person.getNameDetails().getName();

            for (int i = 0; i < nameDetails.size(); i++) {


                String nameType = nameDetails.get(i).getNameType();
                List<NameValue> names = nameDetails.get(i).getNameValue();
                for (int in = 0; in < names.size(); in++) {
                    String firstName = "";
                    String middleName = "";
                    String surname = "";
                    String maidenName = "";
                    String suffix = "";
                    String titleHonorific = "";
                    String singleStringName = "";
                    String originalScriptName = "";
                    String id = org_id + "-" + i + "-" + in;
                    for (int fn = 0; fn < names.get(in).getFirstName().size(); fn++) {
                        firstName = names.get(in).getFirstName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getMiddleName().size(); fn++) {
                        middleName = names.get(in).getMiddleName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getSurname().size(); fn++) {
                        surname = names.get(in).getSurname().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getMaidenName().size(); fn++) {
                        maidenName = names.get(in).getMaidenName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getSuffix().size(); fn++) {
                        suffix = names.get(in).getSuffix().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getTitleHonorific().size(); fn++) {
                        titleHonorific = names.get(in).getTitleHonorific().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getSingleStringName().size(); fn++) {
                        if (singleStringName.equals("")) {
                            singleStringName = names.get(in).getSingleStringName().get(fn);
                        } else
                            originalScriptName = originalScriptName + " - " + names.get(in).getOriginalScriptName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getOriginalScriptName().size(); fn++) {
                        if (originalScriptName.equals("")) {
                            originalScriptName = names.get(in).getOriginalScriptName().get(fn);
                        } else
                            originalScriptName = originalScriptName + " - " + names.get(in).getOriginalScriptName().get(fn);
                    }
                    resultObj = new Object[]{id, org_id, action, nameType, firstName, middleName, surname, maidenName, suffix, titleHonorific, singleStringName, originalScriptName, gender, activeStatus, deceased, description1, description2, description3};

                    _personRowCollector.putValues(resultObj);
                }
            }

        } catch (Exception e) {
        }
        return xsr.getLocalName();
    }

    public static String personReaderXML(XMLStreamReader xsr) throws JAXBException {

        String firstName = "";
        String middleName = "";
        String surname = "";
        String maidenName = "";
        String suffix = "";
        String titleHonorific = "";
        String singleStringName = "";
        String originalScriptName = "";
        String description1 = "";
        String description2 = "";
        String description3 = "";
        Object[] resultObj;

        JAXBContext jc = JAXBContext.newInstance(Person.class);
        Unmarshaller unmarshallerCountry = jc.createUnmarshaller();
        JAXBElement<Person> persons = unmarshallerCountry.unmarshal(xsr, Person.class);

        Person person = persons.getValue();

        String org_id = person.getId();
        String action = person.getAction();
        String gender = person.getGender();
        String activeStatus = person.getActiveStatus();
        String deceased = person.getDeceased();
        // for loop not really needed since there can be only 1 Description, but to be safe I used an for loop.
        for (int d = 0; d > person.getDescriptions().getDescription().size(); d++) {
            description1 = person.getDescriptions().getDescription().get(d).getDescription1();
            description2 = person.getDescriptions().getDescription().get(d).getDescription2();
            description3 = person.getDescriptions().getDescription().get(d).getDescription3();
        }
        try {
            person.getNameDetails().getName().size();


            List<Name> nameDetails = person.getNameDetails().getName();

            for (int i = 0; i < nameDetails.size(); i++) {

                String nameType = nameDetails.get(i).getNameType();
                List<NameValue> names = nameDetails.get(i).getNameValue();
                for (int in = 0; in < names.size(); in++) {

                    String id = org_id + "-" + i + "-" + in;
                    for (int fn = 0; fn < names.get(in).getFirstName().size(); fn++) {
                        firstName = names.get(in).getFirstName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getMiddleName().size(); fn++) {
                        middleName = names.get(in).getMiddleName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getSurname().size(); fn++) {
                        surname = names.get(in).getSurname().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getMaidenName().size(); fn++) {
                        maidenName = names.get(in).getMaidenName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getSuffix().size(); fn++) {
                        suffix = names.get(in).getSuffix().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getTitleHonorific().size(); fn++) {
                        titleHonorific = names.get(in).getTitleHonorific().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getSingleStringName().size(); fn++) {
                        singleStringName = names.get(in).getSingleStringName().get(fn);
                    }
                    for (int fn = 0; fn < names.get(in).getOriginalScriptName().size(); fn++) {
                        if (originalScriptName.equals("")) {
                            originalScriptName = names.get(in).getOriginalScriptName().get(fn);
                        } else
                            originalScriptName = originalScriptName + " - " + names.get(in).getOriginalScriptName().get(fn);
                    }
                    resultObj = new Object[]{id, org_id, action,
                            nameType, firstName, middleName, surname, maidenName, suffix, titleHonorific, singleStringName, originalScriptName,
                            gender, activeStatus, deceased, description1, description2, description3};

                    System.out.println(id + "|" + org_id + "|" + action + "|" + nameType + "|" + firstName + "|" + middleName + "|" + surname + "|" + maidenName + "|" + suffix + "|" + titleHonorific + "|" + singleStringName + "|" + originalScriptName + "|" + gender + "|" + activeStatus + "|" + deceased + "|" + description1 + "|" + description2 + "|" + description3);
                }

            }

        } catch (Exception e) {
        }
        return xsr.getLocalName();
    }
}
