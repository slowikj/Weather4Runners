package com.example.annabujak.weather4runners.Objects;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class Preference {
    private long Id;
    private int Temperature = 15;
    private double Precipitation = 10;
    private int Humidity = 0;
    private Cloudiness Cloudiness = com.example.annabujak.weather4runners.Enum.Cloudiness.Sunny;
    private int StartHour = 10;
    private int EndHour = 17;

    public Preference(){
    }
    public Preference(int _Temprature, Cloudiness _Cloudiness, int _StartHour, int _EndHour, int _Humidity, double _Precipitation){
        Temperature =_Temprature;
        Cloudiness = _Cloudiness;
        StartHour = _StartHour;
        EndHour = _EndHour;
        Humidity = _Humidity;
        Precipitation = _Precipitation;
    }
    public void setTemperature(int temperature){
        Temperature = temperature;
    }
    public int getTemperature(){
        return Temperature;
    }

    public void setCloudiness(Cloudiness cloudiness){
        Cloudiness = cloudiness;
    }
    public Cloudiness getCloudiness(){
        return Cloudiness;
    }

    public void setHours(int startHour, int endHour){
        StartHour = startHour;
        EndHour = endHour;
    }

    public int getStartHour(){
        return StartHour;
    }
    public int getEndHour(){
        return EndHour;
    }

    public void setId(long id){
        Id = id;
    }
    public long getId(){
        return Id;
    }

    public double getPrecipitation(){return Precipitation;}
    public void setPrecipitation(double precipitation){Precipitation = precipitation;}

    public int getHumidity(){return Humidity;}
    public void setHumidity(int humidity){Humidity = humidity;}
}
