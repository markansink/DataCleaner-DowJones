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

    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(Person.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    public static boolean personReader(XMLStreamReader xsr, OutputRowCollector _personRowCollector,
                                       OutputRowCollector _personNameRowCollector, OutputRowCollector _personDescRowCollector,
                                       OutputRowCollector _personRoleRowCollector, OutputRowCollector _personDateRowCollector,
                                       OutputRowCollector _personPlaceRowCollector, OutputRowCollector _personSanctionRowCollector,
                                       OutputRowCollector _personAddressRowCollector, OutputRowCollector _personCountryRowCollector,
                                       OutputRowCollector _personIDRowCollector, OutputRowCollector _personSourceRowCollector,
                                       OutputRowCollector _personImageRowCollector) throws JAXBException {

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
        Object[] idObj;
        Object[] sourceObj;
        Object[] imageObj;

        JAXBElement<Person> persons = unmarshaller.unmarshal(xsr, Person.class);

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

            NameDetails nameDetails = person.getNameDetails();
            if (nameDetails != null) {
                for (int i = 0; i < nameDetails.getName().size(); i++) {
                    Name name = nameDetails.getName().get(i);
                    String nameType = name.getNameType();
                    List<NameValue> names = name.getNameValue();
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
                                singleStringName = singleStringName + " - " + names.get(in).getSingleStringName().get(fn);
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
            }

            Descriptions descriptions = person.getDescriptions();
            if (descriptions != null) {
                for (int d = 0; d < descriptions.getDescription().size(); d++) {
                    Description Desc = descriptions.getDescription().get(d);
                    String description1 = "";
                    String description2 = "";
                    String description3 = "";
                    String id = String.valueOf(d);
                    description1 = Desc.getDescription1();
                    description2 = Desc.getDescription2();
                    description3 = Desc.getDescription3();
                    descObj = new Object[]{id, person_id, description1, description2, description3};
                    _personDescRowCollector.putValues(descObj);
                }
            }

            RoleDetail roleDetail = person.getRoleDetail();
            if (roleDetail != null) {
                for (int r = 0; r < roleDetail.getRoles().size(); r++) {
                    Roles roles = roleDetail.getRoles().get(r);
                    String roleType = "";
                    roleType = roles.getRoleType();

                    for (int o = 0; o < roles.getOccTitle().size(); o++) {
                        OccTitle ot = roles.getOccTitle().get(o);
                        String occTitle = "";
                        String occCat = "";
                        String sinceDate = "";
                        String toDate = "";
                        String id = r + "-" + o;
                        occTitle = ot.getValue();
                        occCat = ot.getOccCat();


                        sinceDate = constructors.constructDate(ot.getSinceYear(), ot.getSinceMonth(), ot.getSinceDay());
                        toDate = constructors.constructDate(ot.getToYear(), ot.getToMonth(), ot.getToDay());
                        occTitleObj = new Object[]{id, person_id, roleType, occTitle, occCat, sinceDate, toDate};
                        _personRoleRowCollector.putValues(occTitleObj);
                    }
                }
            }

            DateDetails dateDetails = person.getDateDetails();
            if (dateDetails != null) {
                for (int dt = 0; dt < dateDetails.getDate().size(); dt++) {
                    Date Date = dateDetails.getDate().get(dt);
                    String dateType = "";
                    String persondate = "";
                    dateType = Date.getDateType();
                    for (int pd = 0; pd < Date.getDateValue().size(); pd++) {
                        DateValue dateValue = Date.getDateValue().get(pd);
                        String id = dt + "-" + pd;
                        persondate = constructors.constructDate(dateValue.getYear(), dateValue.getMonth(), dateValue.getDay());
                        dateObj = new Object[]{id, person_id, dateType, persondate};
                        _personDateRowCollector.putValues(dateObj);
                    }
                }
            }

            BirthPlace bp = person.getBirthPlace();
            if (bp != null) {
                for (int p = 0; p < bp.getPlace().size(); p++) {
                    Place place = bp.getPlace().get(p);
                    String birthPlace = "";
                    String id = String.valueOf(p);
                    birthPlace = place.getName();
                    placeObj = new Object[]{id, person_id, birthPlace};
                    _personPlaceRowCollector.putValues(placeObj);
                }
            }

            SanctionsReferences sanctionReference = person.getSanctionsReferences();
            if (sanctionReference != null) {
                for (int sr = 0; sr < sanctionReference.getReference().size(); sr++) {
                    Reference ref = sanctionReference.getReference().get(sr);
                    String id = String.valueOf(sr);
                    String reference = "";
                    String sinceDate = "";
                    String toDate = "";
                    reference = ref.getValue();
                    sinceDate = constructors.constructDate(ref.getSinceYear(), ref.getSinceMonth(), ref.getSinceDay());
                    toDate = constructors.constructDate(ref.getToYear(), ref.getToMonth(), ref.getToDay());
                    sanctionObj = new Object[]{id, person_id, reference, sinceDate, toDate};
                    _personSanctionRowCollector.putValues(sanctionObj);
                }
            }

            for (int a = 0; a < person.getAddress().size(); a++) {
                Address address = person.getAddress().get(a);
                String id = String.valueOf(a);
                String addressLine = "";
                String city = "";
                String country = "";
                String url = "";

                addressLine = address.getAddressLine();
                city = address.getAddressCity();
                country = address.getAddressCountry();
                url = address.getURL();
                addressObj = new Object[]{id, person_id, addressLine, city, country, url};
                _personAddressRowCollector.putValues(addressObj);
            }
            CountryDetails countryDetails = person.getCountryDetails();
            if (countryDetails != null) {
                for (int c = 0; c < person.getCountryDetails().getCountry().size(); c++) {
                    Country Country = person.getCountryDetails().getCountry().get(c);
                    String countryType = "";
                    countryType = Country.getCountryType();


                    for (int cv = 0; cv < Country.getCountryValue().size(); cv++) {
                        CountryValue countryValue = Country.getCountryValue().get(cv);
                        String id = c + "-" + cv;
                        String country = "";
                        country = countryValue.getCode();
                        countryObj = new Object[]{id, person_id, countryType, country};
                        _personCountryRowCollector.putValues(countryObj);
                    }
                }
            }

            IDNumberTypes idNumberTypes = person.getIDNumberTypes();
            if (idNumberTypes != null) {
                for (int i = 0; i < idNumberTypes.getID().size(); i++) {
                    ID idNumber = idNumberTypes.getID().get(i);
                    String idType = "";
                    idType = idNumber.getIDType();
                    for (int iv = 0; iv < idNumber.getIDValue().size(); iv++) {
                        IDValue idValue = idNumber.getIDValue().get(iv);
                        String id = i + "-" + iv;
                        String value = "";
                        String idNotes = "";

                        value = idValue.getValue();
                        idNotes = idValue.getIDnotes();
                        idObj = new Object[]{id, person_id, idType, value, idNotes};
                        _personIDRowCollector.putValues(idObj);
                    }
                }
            }

            SourceDescription sourceDescription = person.getSourceDescription();
            if (sourceDescription != null) {
                for (int i = 0; i < sourceDescription.getSource().size(); i++) {
                    Source source = sourceDescription.getSource().get(i);
                    String id = String.valueOf(i);
                    String sourceName = "";
                    sourceName = source.getName();

                    sourceObj = new Object[]{id, person_id, sourceName};
                    _personSourceRowCollector.putValues(sourceObj);

                }
            }
            Images images = person.getImages();
            if (images != null) {
                for (int i = 0; i < images.getImage().size(); i++) {
                    Image image = images.getImage().get(i);
                    String id = String.valueOf(i);
                    String url = "";
                    url = image.getURL();

                    imageObj = new Object[]{id, person_id, url};
                    _personImageRowCollector.putValues(imageObj);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return true;
    }

}


