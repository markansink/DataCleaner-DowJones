package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.CountryValue;
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
public class personReaderOld {

    public static boolean personReader(XMLStreamReader xsr, OutputRowCollector _personRowCollector) throws JAXBException {


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

            for (int c = 0; c > person.getCountryDetails().getCountry().size(); c++) {
                citizenshipCountry = "";
                jurisdictionCountry = "";
                residentCountry = "";
                reportedAllegationCountry = "";
                String countryType = person.getCountryDetails().getCountry().get(c).getCountryType();
                List<CountryValue> countryValue = person.getCountryDetails().getCountry().get(c).getCountryValue();
                if (countryType.equals("Citizenship")) {
//                    Finish for loop!!!!!!
//                    for (int cv = 0;cv > countryValue.size();cv++ )
                    citizenshipCountry = person.getCountryDetails().getCountry().get(c).getCountryValue().get(0).getCode();
                }
                if (countryType.equals("Jurisdiction")) {
                    jurisdictionCountry = person.getCountryDetails().getCountry().get(c).getCountryValue().get(0).getCode();
                }
                if (countryType.equals("Resident of")) {
                    residentCountry = person.getCountryDetails().getCountry().get(c).getCountryValue().get(0).getCode();
                }
                if (countryType.equals("Country of Reported Allegation")) {
                    reportedAllegationCountry = person.getCountryDetails().getCountry().get(c).getCountryValue().get(0).getCode();
                }
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
                    String dob = "";
                    String dod = "";
                    String iald = "";
                    for (int d = 0; d < person.getDateDetails().getDate().size(); d++) {
                        if (person.getDateDetails().getDate().get(d).getDateType() == "Date of Birth") {
                            String year = person.getDateDetails().getDate().get(d).getDateValue().get(0).getYear();
                            String month = person.getDateDetails().getDate().get(d).getDateValue().get(0).getMonth();
                            String day = person.getDateDetails().getDate().get(d).getDateValue().get(0).getDay();
                            dob = year + "-" + month + "-" + day;
                        } else if (person.getDateDetails().getDate().get(d).getDateType() == "Deceased Date") {
                            String year = person.getDateDetails().getDate().get(d).getDateValue().get(0).getYear();
                            String month = person.getDateDetails().getDate().get(d).getDateValue().get(0).getMonth();
                            String day = person.getDateDetails().getDate().get(d).getDateValue().get(0).getDay();
                            dod = year + "-" + month + "-" + day;
                        } else {
                            String year = person.getDateDetails().getDate().get(d).getDateValue().get(0).getYear();
                            String month = person.getDateDetails().getDate().get(d).getDateValue().get(0).getMonth();
                            String day = person.getDateDetails().getDate().get(d).getDateValue().get(0).getDay();
                            iald = year + "-" + month + "-" + day;
                        }

                    }
                    resultObj = new Object[]{id, org_id, action, nameType,
                            firstName, middleName, surname, maidenName, suffix, titleHonorific, singleStringName, originalScriptName,
                            gender, activeStatus, deceased, description1, description2, description3, dob, dod, iald, citizenshipCountry, jurisdictionCountry, residentCountry, reportedAllegationCountry};

                    _personRowCollector.putValues(resultObj);
                }
            }

        } catch (Exception e) {
        }
        return true;
    }

}
