package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.CountryList;

import javax.xml.bind.*;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class countryReader {
    public static String countryReader(XMLStreamReader xsr, OutputRowCollector _countryRowCollector) throws JAXBException {

        String code = "";
        String name = "";
        String isTerritory = "";
        String profileURL = "";
        Object[] resultObj;

        JAXBContext jcCountry = JAXBContext.newInstance(CountryList.class);
        Unmarshaller unmarshallerCountry = jcCountry.createUnmarshaller();
        JAXBElement<CountryList> countries = unmarshallerCountry.unmarshal(xsr, CountryList.class);


        CountryList country = countries.getValue();

        for (int ic = 0; ic < country.getCountryName().size(); ic++) {

            code = country.getCountryName().get(ic).getCode();
            name = country.getCountryName().get(ic).getName();
            isTerritory = country.getCountryName().get(ic).getIsTerritory();
            profileURL = country.getCountryName().get(ic).getProfileURL();

            resultObj = new Object[]{code, name, isTerritory, profileURL};

            _countryRowCollector.putValues(resultObj);

        }

        return xsr.getLocalName();
    }

    public static String countryReaderXML(XMLStreamReader xsr) throws JAXBException, XMLStreamException {
        String code = "";
        String name = "";
        String isTerritory = "";
        String profileURL = "";
        Object[] resultObj;

        JAXBContext jcCountry = JAXBContext.newInstance(CountryList.class);
        Unmarshaller unmarshallerCountry = jcCountry.createUnmarshaller();
        JAXBElement<CountryList> countries = unmarshallerCountry.unmarshal(xsr, CountryList.class);

        CountryList country = countries.getValue();
        System.out.println(xsr.getLocalName());
        for (int ic = 0; ic < country.getCountryName().size(); ic++) {

            code = country.getCountryName().get(ic).getCode();
            name = country.getCountryName().get(ic).getName();
            isTerritory = country.getCountryName().get(ic).getIsTerritory();
            profileURL = country.getCountryName().get(ic).getProfileURL();

            resultObj = new Object[]{code, name, isTerritory, profileURL};
            System.out.println(name);

        }

        return xsr.getLocalName();
    }
}
