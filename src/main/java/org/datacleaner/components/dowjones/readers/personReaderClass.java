package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;
import java.util.List;

/**
 * Created by mansink on 31-05-16.
 */
public class personReaderClass {

    public static boolean personReader(XMLStreamReader xsr, OutputRowCollector _personRowCollector,
                                       OutputRowCollector _personNameRowCollector, OutputRowCollector _personDescRowCollector,
                                       OutputRowCollector _personRoleRowCollector, OutputRowCollector _personDateRowCollector, OutputRowCollector _personPlaceRowCollector) throws JAXBException {

        String person_id = "";
        String action = "";
        String date = "";
        String gender = "";
        String activeStatus = "";
        String deceased = "";
        String profileNotes = "";


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
            person_id = person.getId();
            action = person.getAction();
            date = person.getDate();
            gender = person.getGender();
            activeStatus = person.getActiveStatus();
            deceased = person.getDeceased();
            profileNotes = person.getProfileNotes();

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
                    String id = i + "-" + in;
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
                    resultObj = new Object[]{id, person_id, nameType,
                            firstName, middleName, surname, maidenName, suffix, titleHonorific, singleStringName, originalScriptName};
                    _personNameRowCollector.putValues(resultObj);
                }
            }

            List<Description> Desc = person.getDescriptions().getDescription();
            for (int d = 0; d > Desc.size(); d++) {
                String description1 = "";
                String description2 = "";
                String description3 = "";
                String id = String.valueOf(d);
                description1 = Desc.get(d).getDescription1();
                description2 = Desc.get(d).getDescription2();
                description3 = Desc.get(d).getDescription3();
                resultObj = new Object[]{id, person_id, description1, description2, description3};
                _personDescRowCollector.putValues(resultObj);
            }

            List<Roles> Roles = person.getRoleDetail().getRoles();
            for (int r = 0; r > Roles.size(); r++) {
                String roleType = "";
                roleType = Roles.get(r).getRoleType();
                List<OccTitle> occTitles = Roles.get(r).getOccTitle();
                for (int o = 0; o > occTitles.size(); o++) {
                    String occTitle = "";
                    String occCat = "";
                    String sinceDate = "";
                    String toDate = "";
                    String id = r + "-" + o;
                    occTitle = occTitles.get(o).getValue();
                    occCat = occTitles.get(o).getOccCat();
                    sinceDate = occTitles.get(o).getSinceYear() + "-" + occTitles.get(o).getSinceMonth() + "-" + occTitles.get(o).getSinceDay();
                    toDate = occTitles.get(o).getToYear() + "-" + occTitles.get(o).getToMonth() + "-" + occTitles.get(o).getToDay();
                    resultObj = new Object[]{id, person_id, roleType, occTitle, occCat, sinceDate, toDate};
                    _personRoleRowCollector.putValues(resultObj);
                }
            }

            List<Date> Dates = person.getDateDetails().getDate();
            for (int dt = 0; dt > Dates.size(); dt++) {
                String dateType = "";
                String persondate = "";
                String id = String.valueOf(dt);
                dateType = Dates.get(dt).getDateType();
                date = Dates.get(dt).getDateValue().toString();
                resultObj = new Object[]{id, person_id, dateType, persondate};
                _personDateRowCollector.putValues(resultObj);
            }

            List<Place> Places = person.getBirthPlace().getPlace();
            for (int p = 0; p > Places.size(); p++) {
                String birthPlace = "";

                String id = String.valueOf(p);
                birthPlace = Places.get(p).getName();
                resultObj = new Object[]{id, person_id, birthPlace};
                _personPlaceRowCollector.putValues(resultObj);
            }


            resultObj = new Object[]{person_id, action, date, gender, activeStatus, deceased, profileNotes};
            _personRowCollector.putValues(resultObj);

        } catch (Exception e) {
        }
        return true;
    }

}
