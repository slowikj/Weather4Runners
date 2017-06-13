package com.example.annabujak.weather4runners.Objects;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class Preference {

    public static final int DEFAULT_TEMPERATURE = 20;

    public static final double DEFAULT_PRECIPITATION = 0;

    public static final int DEFAULT_HUMIDITY = 65;

    public static final Cloudiness DEFAULT_CLOUDINESS = com.example.annabujak.weather4runners.Enum.Cloudiness.Sunny;

    public static final int DEFAULT_START_HOUR = 0;

    public static final int DEFAULT_END_HOUR = 23;

    public static final double DEFAULT_WIND_SPEED = 10;

    private long Id;

    private int Temperature;

    private double Precipitation;

    private int Humidity;

    private Cloudiness Cloudiness;

    private double WindSpeed;

    private int StartHour;

    private int EndHour;

    private PreferenceBalance Balance;

    public Preference(){
        this.Temperature = DEFAULT_TEMPERATURE;
        this.Precipitation = DEFAULT_PRECIPITATION;
        this.Humidity = DEFAULT_HUMIDITY;
        this.Cloudiness = DEFAULT_CLOUDINESS;
        this.WindSpeed = DEFAULT_WIND_SPEED;
        this.StartHour = DEFAULT_START_HOUR;
        this.EndHour = DEFAULT_END_HOUR;

        Balance = new PreferenceBalance();
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
