package com.example.annabujak.weather4runners.Objects;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

import java.util.Date;

/**
 * Created by slowik on 24.04.2017.
 */

public class WeatherInfo {

    private Date Date;
    private long Id;
    private int Temperature;
    private int Humidity;
    private Cloudiness Cloudiness;
    private double Precipitation;
    private boolean isChecked;

    public WeatherInfo(int _Temperature, int _Humidity, Cloudiness _Cloudiness, double _Precipitation, Date _Date) {
        Temperature = _Temperature;
        Humidity = _Humidity;
        Cloudiness = _Cloudiness;
        Precipitation = _Precipitation;
        Date = _Date;
        isChecked = false;
    }

    public int getTemperature() {
        return Temperature;
    }

    public int getHumidity() {
        return Humidity;
    }

    public Cloudiness getCloudiness() {return Cloudiness;}

    public double getPrecipitation() {
        return Precipitation;
    }

    public java.util.Date getDate(){return Date;}

    public void setId(long id){Id = id;}
    public long getId(){return Id;}

    public void setIsChecked(boolean _isChecked){
        isChecked = _isChecked;
    }
    public boolean getIsChecked(){return isChecked;}
}
