package com.example.annabujak.weather4runners.Objects;

import java.util.Date;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class ChosenProposition {
    long Id;
    long Date;
    Boolean IsHour;

    public ChosenProposition(long _Date, boolean _Hour){
        Date = _Date;
        IsHour  =_Hour;
    }
    public void setDayAnIsdHour(long date, boolean hour){
        Date = date;
        IsHour = hour;
    }
    public long getDate(){
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
