package org.datacleaner.components.dowjones.xml;

/**
 * Created by marka on 7/11/2016.
 */
public class constructors {
    public static String constructDate(String Year, String Month, String Day) {
        String date = "";
        String day = "00";
        String month = "00";
        String year = "0000";

        if (Month == null) {
            month = "   ";
        } else {
            month = Month;
        }
        if (Year == null) {
            year = "0000";
        } else {
            year = Year;
        }
        if (Day == null) {
            day = "00";
        } else {
            day = Day;
        }

        date = year + "-" + month + "-" + day;

        return date;
    }
}
