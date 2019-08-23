package com.example.sphapplication;

import java.math.BigDecimal;

public class YearListItem {

    private String year;
    private BigDecimal volume;

    public boolean isDecilned() {
        return decilned;
    }

    public void setDecilned(boolean decilned) {
        this.decilned = decilned;
    }

    private boolean decilned = false;

    public YearListItem(String year, BigDecimal totaldata, boolean declined) {
        this.year = year;
        this.volume = totaldata;
        this.decilned = declined;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
