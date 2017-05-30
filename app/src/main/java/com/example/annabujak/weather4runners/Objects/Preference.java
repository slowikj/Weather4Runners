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
    private double WindSpeed = 20.0;
    private int StartHour = 10;
    private int EndHour = 17;
    private PreferenceBalance Balance;

    public Preference(){
        Balance = new PreferenceBalance(1,0.1,1,1,1);
    }
    public Preference(int _Temprature, Cloudiness _Cloudiness, int _StartHour, int _EndHour, int _Humidity, double _Precipitation, double _WindSpeed){
        Temperature =_Temprature;
        Cloudiness = _Cloudiness;
        StartHour = _StartHour;
        EndHour = _EndHour;
        Humidity = _Humidity;
        Precipitation = _Precipitation;
        WindSpeed = _WindSpeed;
        Balance = new PreferenceBalance(1,0.1,1,1,1);
    }
    public Preference(int _Temprature, Cloudiness _Cloudiness, int _StartHour, int _EndHour, int _Humidity, double _Precipitation, double _WindSpeed, PreferenceBalance _Balanse){
        Temperature =_Temprature;
        Cloudiness = _Cloudiness;
        StartHour = _StartHour;
        EndHour = _EndHour;
        Humidity = _Humidity;
        Precipitation = _Precipitation;
        WindSpeed = _WindSpeed;
        Balance = _Balanse;
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

    public double getWindSpeed(){return WindSpeed;}
    public void setWindSpeed(double _WindSpeed){WindSpeed = _WindSpeed;}

    public PreferenceBalance getPreferenceBalance(){return  Balance;}
    public void setPreferenceBalance(PreferenceBalance _Balance){Balance = _Balance;}
}
