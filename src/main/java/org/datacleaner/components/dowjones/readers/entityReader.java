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

    public static boolean entityReader(XMLStreamReader xsr, OutputRowCollector _entityRowCollector,
                                       OutputRowCollector _entityNameRowCollector, OutputRowCollector _entityDescRowCollector,
                                       OutputRowCollector _entityDateRowCollector, OutputRowCollector _entitySanctionRowCollector,
                                       OutputRowCollector _entityAddressRowCollector, OutputRowCollector _entityCountryRowCollector,
                                       OutputRowCollector _entityIDRowCollector, OutputRowCollector _entitySourceRowCollector) throws JAXBException {

        String entityId = "";
        String action = "";
        String activeStatus = "";
        String profileNotes = "";

        Object[] entityObj;
        Object[] entityNameObj;
        Object[] descObj;
        Object[] dateObj;
        Object[] sanctionObj;
        Object[] addressObj;
        Object[] countryObj;
        Object[] idObj;
        Object[] sourceObj;

        JAXBElement<Entity> entities = unmarshaller.unmarshal(xsr, Entity.class);

        Entity entity = entities.getValue();
        try {

            entityId = entity.getId();
            action = entity.getAction();
            activeStatus = entity.getActiveStatus();
            profileNotes = entity.getProfileNotes();

            entityObj = new Object[]{entityId, action, activeStatus, profileNotes};
            _entityRowCollector.putValues(entityObj);

            if (entity.getNameDetails() != null) {
                List<Name> names = entity.getNameDetails().getName();
                if (names != null) {
                    for (int e = 0; e < names.size(); e++) {
                        String nameType = names.get(e).getNameType();
                        List<NameValue> nameValues = names.get(e).getNameValue();
                        for (int n = 0; n < nameValues.size(); n++) {

                            List<String> entityNames = nameValues.get(n).getEntityName();
                            for (int en = 0; en < entityNames.size(); en++) {
                                String id = e + "-" + n;
                                String entityName = entityNames.get(en).toString();
                                entityNameObj = new Object[]{id, entityId, entityName};
                                _entityNameRowCollector.putValues(entityNameObj);
                            }
                        }
                    }
                }
            }
            Descriptions descriptions = entity.getDescriptions();
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
                    descObj = new Object[]{id, entityId, description1, description2, description3};
                    _entityDescRowCollector.putValues(descObj);
                }
            }

            DateDetails dateDetails = entity.getDateDetails();
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
                        dateObj = new Object[]{id, entityId, dateType, persondate};
                        _entityDateRowCollector.putValues(dateObj);
                    }
                }
            }
            SanctionsReferences sanctionReference = entity.getSanctionsReferences();
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
                    sanctionObj = new Object[]{id, entityId, reference, sinceDate, toDate};
                    _entitySanctionRowCollector.putValues(sanctionObj);
                }
            }
            List<CompanyDetails> companyDetails = entity.getCompanyDetails();
            if (companyDetails != null) {
                for (int c = 0; c < companyDetails.size(); c++) {

                    String id = String.valueOf(c);
                    String addressLine = "";
                    String city = "";
                    String country = "";
                    String url = "";

                    addressLine = companyDetails.get(c).getAddressLine();
                    city = companyDetails.get(c).getAddressCity();
                    country = companyDetails.get(c).getAddressCountry();
                    url = companyDetails.get(c).getURL();
                    addressObj = new Object[]{id, entityId, addressLine, city, country, url};
                    _entityAddressRowCollector.putValues(addressObj);

                }
            }
//TODO Company Details

            CountryDetails countryDetails = entity.getCountryDetails();
            if (countryDetails != null) {
                for (int c = 0; c < countryDetails.getCountry().size(); c++) {
                    Country Country = countryDetails.getCountry().get(c);
                    String countryType = "";
                    countryType = Country.getCountryType();


                    for (int cv = 0; cv < Country.getCountryValue().size(); cv++) {
                        CountryValue countryValue = Country.getCountryValue().get(cv);
                        String id = c + "-" + cv;
                        String country = "";
                        country = countryValue.getCode();
                        countryObj = new Object[]{id, entityId, countryType, country};
                        _entityCountryRowCollector.putValues(countryObj);
                    }
                }
            }

            IDNumberTypes idNumberTypes = entity.getIDNumberTypes();
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
                        idObj = new Object[]{id, entityId, idType, value, idNotes};
                        _entityIDRowCollector.putValues(idObj);
                    }
                }
            }

            SourceDescription sourceDescription = entity.getSourceDescription();
            if (sourceDescription != null) {
                for (int i = 0; i < sourceDescription.getSource().size(); i++) {
                    Source source = sourceDescription.getSource().get(i);
                    String id = String.valueOf(i);
                    String sourceName = "";
                    sourceName = source.getName();

                    sourceObj = new Object[]{id, entityId, sourceName};
                    _entitySourceRowCollector.putValues(sourceObj);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();

        }


        return true;

    }


}
