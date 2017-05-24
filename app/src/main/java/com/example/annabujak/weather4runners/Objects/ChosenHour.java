package com.example.annabujak.weather4runners.Objects;

import java.util.Date;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class ChosenHour {
    long Id;
    Date Date;
    Boolean IsHour;

    public ChosenHour(Date _Date, boolean _Hour){
        Date = _Date;
        IsHour  =_Hour;
    }
    public void setDayAnIsdHour(Date date, boolean hour){
        Date = date;
        IsHour = hour;
    }
    public Date getDate(){
        return Date;
    }
    public boolean getIsHour(){
        return IsHour;
    }
    public void setId(long id){
        Id = id;
    }
    public long getId(){
        return Id;
    }
}
