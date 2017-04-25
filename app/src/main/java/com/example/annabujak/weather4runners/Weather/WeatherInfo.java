package com.example.annabujak.weather4runners.Weather;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

/**
 * Created by slowik on 24.04.2017.
 */

public class WeatherInfo {
    private int temperature;
    private int humidity;
    private Cloudiness cloudiness;
    private double precipitation;

    public WeatherInfo(int temperature, int humidity, Cloudiness cloudiness, double precipitation) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.cloudiness = cloudiness;
        this.precipitation = precipitation;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public Cloudiness getCloudiness() {
        return cloudiness;
    }

    public double getPrecipitation() {
        return precipitation;
    }
}
