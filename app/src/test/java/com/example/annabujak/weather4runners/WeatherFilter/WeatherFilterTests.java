package com.example.annabujak.weather4runners.WeatherFilter;

import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.Filter.WeatherFilter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;


/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherFilterTests {
//    Temperature = _Temperature;
//    Humidity = _Humidity;
//    Cloudiness = _Cloudiness;
//    Precipitation = _Precipitation;
//    Date = _Date;
//    isChecked = false;
//    WindSpeed = _WindSpeed;
//    IconName = _IconName;
//    Description = _Description;
    WeatherFilter filter;
    @Before
    public void setUp() throws Exception {
        filter = new WeatherFilter(4);
    }

    @Test
    public void GetBestWeatherFirstTest(){
        Preference preference = new Preference(15, Cloudiness.Medium,10,17,12,12.0,12.0);
        List<WeatherInfo> weather = new ArrayList<>();
        weather.add(new WeatherInfo(13,12,Cloudiness.Small,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(14)),12.0,"Name","Second"));
        weather.add(new WeatherInfo(0,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(15)),12.0,"Name"));
        weather.add(new WeatherInfo(25,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(16)),12.0,"Name","Third"));
        weather.add(new WeatherInfo(-20,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(17)),12.0,"Name"));
        weather.add(new WeatherInfo(19,12,Cloudiness.Medium,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(18)),12.0,"Name","First"));

        List<WeatherInfo> result = filter.GetDailyWeather(weather,preference);
        if(result.size() == 5) {
            assertEquals("First", result.get(0).getDescription());
            assertEquals("Second", result.get(1).getDescription());
            assertEquals("Third", result.get(2).getDescription());
        }

    }
}
