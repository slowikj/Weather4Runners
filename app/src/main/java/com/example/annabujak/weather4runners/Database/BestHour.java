package com.example.annabujak.weather4runners.Database;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class BestHour {

    int Id;
    Date Date;
    int Hour;

    public BestHour(Date _Date, int _Hour){
        Date = _Date;
        Hour  =_Hour;
    }
    public void setDayAndHour(Date date, int hour){
        Date = date;
        Hour = hour;
    }
    public Date getDate(){
        return Date;
    }
    public int getHour(){
        return Hour;
    }
    public void setId(int id){
        Id = id;
    }
    public int getId(){
        return Id;
    }
}
