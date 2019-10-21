package com.edu.cinstina.db;

import java.util.Date;

/**
 * Created by Honza on 7. 3. 2018.
 */

public class Words implements WordsInterface {
    private long id;
    private String myLang;
    private String myReading;
    private String myForeign;
    private String category;
    private Long dateCreated;
    private Integer state; //values {0,1,2,3,4} where 0 means NEW and 4 means LEARNED
    private Long stateChangeDate;
    private String myLangAscii;
    private String info;

    public Words(long id, String myLang, String myReading, String myForeign, String category, Long dateCreated, Integer state, Long stateChangedDate, String myLangAscii) {
        this.id = id;
        this.myLang = myLang;
        this.myReading = myReading;
        this.myForeign = myForeign;
        this.category = category;
        this.dateCreated = dateCreated;
        this.state = state;
        this.stateChangeDate = stateChangedDate;
        this.myLangAscii = myLangAscii;
    }

    public Words(String myLang, String myReading, String myForeign, String category, Long dateCreated, Integer state, Long stateChangedDate, String myLangAscii) {
        this.myLang = myLang;
        this.myReading = myReading;
        this.myForeign = myForeign;
        this.category = category;
        this.dateCreated = dateCreated;
        this.state = state;
        this.stateChangeDate = stateChangedDate;
        this.myLangAscii = myLangAscii;
    }

    //konstruktor pro běžnou práci
    public Words(String myLang, String myReading, String myForeign, String category, Long dateCreated, Integer state, Long stateChangedDate) {
        this.myLang = myLang;
        this.myReading = myReading;
        this.myForeign = myForeign;
        this.category = category;
        this.dateCreated = dateCreated;
        this.state = state;
        this.stateChangeDate = stateChangedDate;
    }

    public Words() {
    }

    public String getMyLang() {
        return myLang;
    }

    public void setMyLang(String myLang) {
        this.myLang = myLang;
    }

    public String getMyReading() {
        return myReading;
    }

    public String getMyLangAscii() {
        return myLangAscii;
    }

    public void setMyLangAscii(String myLangAscii) {
        this.myLangAscii = myLangAscii;
    }

    public void setMyReading(String myReading) {
        this.myReading = myReading;
    }

    public String getMyForeign() {
        return myForeign;
    }

    public void setMyForeign(String myForeign) {
        this.myForeign = myForeign;
    }

    public String getCategory() {return category;}

    public void setCategory(String category) { this.category = category; }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getStateChangeDate() {
        return stateChangeDate;
    }

    public void setStateChangeDate(Long stateChangeDate) {
        this.stateChangeDate = stateChangeDate;
    }

    public String getInfo() {return this.info;}

    public void setInfo(String info) {this.info = info;}

    @Override
    public String toString() {
        return myLang+"-"+myReading+"-"+myForeign + "-" + category;
    }

    @Override
    public boolean equals(Words words) {
        if (words == null) {
            return false;
        }
        if (!Words.class.isAssignableFrom(words.getClass())) {
            return false;
        }
        final Words other = (Words) words;
        if ((this.myForeign == null) ? (other.myForeign != null) : !this.myForeign.equals(other.myForeign)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + myForeign.hashCode();
        hash = hash * 13 + myLang.hashCode();
        hash = hash * 29 + myReading.hashCode();
        hash = hash * 13 + category.hashCode();
        return hash;
    }
}
