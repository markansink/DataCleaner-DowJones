package org.datacleaner.components.dowjones.readers;

/**
 * Created by mansink on 31-05-16.
 */
public class pfa {
    private String dj_date;
    private String type;

    public String getDate() {
        return dj_date;
    }

    public void setDate(String dj_date) {
        this.dj_date = dj_date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return dj_date + "|" + type;
    }
}


