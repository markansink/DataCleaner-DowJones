package org.datacleaner.components.dowjones.readers;

import org.datacleaner.api.OutputRowCollector;
import org.datacleaner.components.dowjones.xml.CountryList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * Created by mansink on 31-05-16.
 */
public class countryReader {

    private static Unmarshaller unmarshaller;

    static {
        try {
            JAXBContext jc = JAXBContext.newInstance(CountryList.class);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    public static boolean countryReader(XMLStreamReader xsr, OutputRowCollector _countryRowCollector) throws JAXBException, XMLStreamException {

        String code;
        String name;
        String isTerritory;
        String profileURL;
        Object[] resultObj;


        JAXBElement<CountryList> countries = unmarshaller.unmarshal(xsr, CountryList.class);


        CountryList country = countries.getValue();

        for (int ic = 0; ic < country.getCountryName().size(); ic++) {

            code = country.getCountryName().get(ic).getCode();
            name = country.getCountryName().get(ic).getName();
            isTerritory = country.getCountryName().get(ic).getIsTerritory();
            profileURL = country.getCountryName().get(ic).getProfileURL();

            resultObj = new Object[]{code, name, isTerritory, profileURL};

            _countryRowCollector.putValues(resultObj);

        }
        return true;

    }


}
