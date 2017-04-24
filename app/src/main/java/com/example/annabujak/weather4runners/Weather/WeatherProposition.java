package com.example.annabujak.weather4runners.Weather;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by slowik on 24.04.2017.
 */

public class WeatherProposition {

    private Date date;
    private WeatherInfo weatherInfo;
    private boolean checked;

    public WeatherProposition(Date date,
                              WeatherInfo weatherInfo,
                              boolean checked) {
        this.date = date;
        this.weatherInfo = weatherInfo;
        this.checked = checked;
    }

    public String getDateInfo(SimpleDateFormat dateFormat) {
        return dateFormat.format(this.date);
    }

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
