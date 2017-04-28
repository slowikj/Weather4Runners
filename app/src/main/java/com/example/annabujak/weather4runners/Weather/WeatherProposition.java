package com.example.annabujak.weather4runners.Weather;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by slowik on 24.04.2017.
 */

public class WeatherProposition {

    //Jeżeli zajdzie taka potrzeba, to klase możnaby usunąć i korzystać jedynie z WeatherInfo
    private WeatherInfo WeatherInfo;

    public WeatherProposition(WeatherInfo weatherInfo,boolean checked) {
        this.WeatherInfo = weatherInfo;
    }

    public String getDateInfo(SimpleDateFormat dateFormat) {
        return dateFormat.format(this.WeatherInfo.getDate());
    }

    public boolean isChecked() {
        return WeatherInfo.getIsChecked();
    }

}
