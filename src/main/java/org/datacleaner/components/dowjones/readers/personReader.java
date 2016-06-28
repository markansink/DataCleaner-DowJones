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
public class personReader {

    public static boolean personReader(XMLStreamReader xsr, OutputRowCollector _personRowCollector,
                                       OutputRowCollector _personNameRowCollector, OutputRowCollector _personDescRowCollector,
                                       OutputRowCollector _personRoleRowCollector, OutputRowCollector _personDateRowCollector,
                                       OutputRowCollector _personPlaceRowCollector, OutputRowCollector _personSanctionRowCollector,
                                       OutputRowCollector _personAddressRowCollector, OutputRowCollector _personCountryRowCollector) throws JAXBException {

        String person_id = "";
        String action = "";
        String date = "";
        String gender = "";
        String activeStatus = "";
        String deceased = "";
        String profileNotes = "";

        Object[] personObj;
        Object[] nameObj;
        Object[] descObj;
        Object[] occTitleObj;
        Object[] dateObj;
        Object[] placeObj;
        Object[] sanctionObj;
        Object[] addressObj;
        Object[] countryObj;

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

            personObj = new Object[]{person_id, action, date, gender, activeStatus, deceased, profileNotes};
            _personRowCollector.putValues(personObj);

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
                    nameObj = new Object[]{id, person_id, nameType,
                            firstName, middleName, surname, maidenName, suffix, titleHonorific, singleStringName, originalScriptName};
                    _personNameRowCollector.putValues(nameObj);
                }
            }

            List<Description> Desc = person.getDescriptions().getDescription();
            for (int d = 0; d < Desc.size(); d++) {
                String description1 = "";
                String description2 = "";
                String description3 = "";
                String id = String.valueOf(d);
                description1 = Desc.get(d).getDescription1();
                description2 = Desc.get(d).getDescription2();
                description3 = Desc.get(d).getDescription3();
                descObj = new Object[]{id, person_id, description1, description2, description3};
                _personDescRowCollector.putValues(descObj);
            }

            List<Roles> Roles = person.getRoleDetail().getRoles();
            for (int r = 0; r < Roles.size(); r++) {
                String roleType = "";
                roleType = Roles.get(r).getRoleType();
                List<OccTitle> occTitles = Roles.get(r).getOccTitle();
                for (int o = 0; o < occTitles.size(); o++) {
                    String occTitle = "";
                    String occCat = "";
                    String sinceDate = "";
                    String toDate = "";
                    String id = r + "-" + o;
                    occTitle = occTitles.get(o).getValue();
                    occCat = occTitles.get(o).getOccCat();
                    String sinceYear = "";
                    String sinceMonth = "";
                    String sinceDay = "";
                    String toYear = "";
                    String toMonth = "";
                    String toDay = "";


                    if (occTitles.get(o).getSinceYear() != null) {
                        sinceYear = occTitles.get(o).getSinceYear();
                    }
                    if (occTitles.get(o).getSinceMonth() != null) {
                        sinceMonth = occTitles.get(o).getSinceMonth();
                    }
                    if (occTitles.get(o).getSinceDay() != null) {
                        sinceDay = occTitles.get(o).getSinceDay();
                    }
                    if (occTitles.get(o).getToYear() != null) {
                        toYear = occTitles.get(o).getToYear();
                    }
                    if (occTitles.get(o).getToMonth() != null) {
                        toMonth = occTitles.get(o).getToMonth();
                    }
                    if (occTitles.get(o).getToDay() != null) {
                        toDay = occTitles.get(o).getToDay();
                    }

                    sinceDate = sinceYear + "-" + sinceMonth + "-" + sinceDay;
                    toDate = toYear + "-" + toMonth + "-" + toDay;
                    occTitleObj = new Object[]{id, person_id, roleType, occTitle, occCat, sinceDate, toDate};
                    _personRoleRowCollector.putValues(occTitleObj);
                }
            }

            List<Date> Dates = person.getDateDetails().getDate();
            for (int dt = 0; dt < Dates.size(); dt++) {
                String dateType = "";
                String persondate = "";

                dateType = Dates.get(dt).getDateType();
                for (int pd = 0; pd < Dates.get(dt).getDateValue().size(); pd++) {
                    String id = dt + "-" + pd;

                    persondate = Dates.get(dt).getDateValue().get(pd).getDay();
                    dateObj = new Object[]{id, person_id, dateType, persondate};
                    _personDateRowCollector.putValues(dateObj);
                }
            }

            List<Place> Places = person.getBirthPlace().getPlace();
            for (int p = 0; p < Places.size(); p++) {
                String birthPlace = "";

                String id = String.valueOf(p);
                birthPlace = Places.get(p).getName();
                placeObj = new Object[]{id, person_id, birthPlace};
                _personPlaceRowCollector.putValues(placeObj);
            }

            List<Reference> References = person.getSanctionsReferences().getReference();
            for (int sr = 0; sr < References.size(); sr++) {
                String id = String.valueOf(sr);
                String reference = "";
                String sinceDate = "";
                String toDate = "";
                String sinceYear = "";
                String sinceMonth = "";
                String sinceDay = "";
                String toYear = "";
                String toMonth = "";
                String toDay = "";

                reference = References.get(sr).getValue();
                if (References.get(sr).getSinceYear() != null) {
                    sinceYear = References.get(sr).getSinceYear();
                }
                if (References.get(sr).getSinceMonth() != null) {
                    sinceMonth = References.get(sr).getSinceMonth();
                }
                if (References.get(sr).getSinceDay() != null) {
                    sinceDay = References.get(sr).getSinceDay();
                }
                if (References.get(sr).getToYear() != null) {
                    toYear = References.get(sr).getToYear();
                }
                if (References.get(sr).getToMonth() != null) {
                    toMonth = References.get(sr).getToMonth();
                }
                if (References.get(sr).getToDay() != null) {
                    toDay = References.get(sr).getToDay();
                }

                sinceDate = sinceYear + "-" + sinceMonth + "-" + sinceDay;
                toDate = toYear + "-" + toMonth + "-" + toDay;
                sanctionObj = new Object[]{id, person_id, reference, sinceDate, toDate};
                _personSanctionRowCollector.putValues(sanctionObj);
            }

            List<Address> Address = person.getAddress();
            for (int a = 0; a < Address.size(); a++) {
                String id = String.valueOf(a);
                String addressLine = "";
                String city = "";
                String country = "";
                String url = "";

                addressLine = Address.get(a).getAddressLine();
                city = Address.get(a).getAddressCity();
                country = Address.get(a).getAddressCountry();
                url = Address.get(a).getURL();
                addressObj = new Object[]{id, person_id, addressLine, city, country, url};
                _personAddressRowCollector.putValues(addressObj);
            }

            List<Country> Country = person.getCountryDetails().getCountry();
            for (int c = 0; c < Country.size(); c++) {
                String countryType = "";
                countryType = Country.get(c).getCountryType();

                List<CountryValue> countryValue = Country.get(c).getCountryValue();
                for (int cv = 0; cv < countryValue.size(); cv++) {
                    String id = c + "-" + cv;
                    String country;
                    country = countryValue.get(cv).getCode();
                    countryObj = new Object[]{id, person_id, countryType, country};
                    _personCountryRowCollector.putValues(countryObj);
                }
            }

        } catch (Exception e) {

        }
        return true;
    }

}
