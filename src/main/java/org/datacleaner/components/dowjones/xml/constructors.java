package org.datacleaner.components.dowjones.xml;

/**
 * Created by marka on 7/11/2016.
 */
public class constructors {
    public static String constructDate(String Year, String Month, String Day) {
        String date = "";
//        String day = "00";
        String month = "";
//        String year = "0000";
        if (Month == null) {
            month = null;
        } else if (Month.equals("Jan")) {
            month = "01";
        } else if (Month.equals("Feb")) {
            month = "02";
        } else if (Month.equals("Mar")) {
            month = "03";
        } else if (Month.equals("Apr")) {
            month = "04";
        } else if (Month.equals("May")) {
            month = "05";
        } else if (Month.equals("Jun")) {
            month = "06";
        } else if (Month.equals("Jul")) {
            month = "07";
        } else if (Month.equals("Aug")) {
            month = "08";
        } else if (Month.equals("Sep")) {
            month = "09";
        } else if (Month.equals("Oct")) {
            month = "10";
        } else if (Month.equals("Nov")) {
            month = "11";
        } else if (Month.equals("Dec")) {
            month = "12";
        }


        if (Month != null && Day != null && Year != null) {
            date = Year + month + Day;
        } else if (Month != null && Day == null && Year != null) {
            date = Year + month;
        } else if (Month == null && Day == null && Year != null) {
            date = Year;
        } else {
            date = null;
        }
        return date;
    }
}
