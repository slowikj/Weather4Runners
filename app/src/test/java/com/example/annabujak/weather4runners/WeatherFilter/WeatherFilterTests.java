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
    public void GetBestWeatherTemperatureTest(){
        Preference preference = new Preference(15, Cloudiness.Medium,10,17,12,12.0,12.0);
        List<WeatherInfo> weather = new ArrayList<>();
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(14)),12.0,"Name","First"));
        weather.add(new WeatherInfo(0,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(15)),12.0,"Name"));
        weather.add(new WeatherInfo(25,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(16)),12.0,"Name","Third"));
        weather.add(new WeatherInfo(-20,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(17)),12.0,"Name"));
        weather.add(new WeatherInfo(19,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(18)),12.0,"Name","Second"));

        List<WeatherInfo> result = filter.GetDailyWeather(weather,preference);
        if(result.size() == 4) {
            assertEquals("First", result.get(0).getDescription());
            assertEquals("Second", result.get(1).getDescription());
            assertEquals("Third", result.get(2).getDescription());
        }

    }

    @Test
    public void GetBestWeatherCloudinessTest() throws Exception {
        Preference preference = new Preference(15, Cloudiness.Medium, 10, 17, 12, 12.0, 12.0);
        List<WeatherInfo> weather = new ArrayList<>();
        weather.add(new WeatherInfo(13, 12, Cloudiness.Medium, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name", "First"));
        weather.add(new WeatherInfo(13, 12, Cloudiness.Sunny, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name"));
        weather.add(new WeatherInfo(13, 12, Cloudiness.Sunny, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name", "Third"));
        weather.add(new WeatherInfo(13, 12, Cloudiness.Sunny, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name"));
        weather.add(new WeatherInfo(13, 12, Cloudiness.Small, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name", "Second"));
        List<WeatherInfo> result = filter.GetDailyWeather(weather, preference);
        if (result.size() == 4) {
            assertEquals("First", result.get(0).getDescription());
            assertEquals("Second", result.get(1).getDescription());
        }
    }

    @Test
    public void GetBestWeatherHumidity() throws Exception{
        Preference preference = new Preference(15, Cloudiness.Medium, 0, 23, 37, 12.0, 12.0);
        List<WeatherInfo> weather = new ArrayList<>();
        weather.add(new WeatherInfo(13, 36, Cloudiness.Medium, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name", "First"));
        weather.add(new WeatherInfo(13, 50, Cloudiness.Medium, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name"));
        weather.add(new WeatherInfo(13, 46, Cloudiness.Medium, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name", "Third"));
        weather.add(new WeatherInfo(13, 0, Cloudiness.Medium, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name"));
        weather.add(new WeatherInfo(13, 40, Cloudiness.Medium, 12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)), 12.0, "Name", "Second"));
        List<WeatherInfo> result = filter.GetDailyWeather(weather, preference);
        if (result.size() == 4) {
            assertEquals("First", result.get(0).getDescription());
            assertEquals("Second", result.get(1).getDescription());
            assertEquals("Third", result.get(2).getDescription());
        }
    }

    @Test
    public  void GetBestWeatherWindSpeed() throws Exception{
        Preference preference = new Preference(15, Cloudiness.Medium, 0, 23, 37, 12.0, 12.0);
        List<WeatherInfo> weather = new ArrayList<>();
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),17.0,"Name","Third"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),40.0,"Name"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),11.0,"Name","Second"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),0.0,"Name"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),12.0,"Name","First"));
        List<WeatherInfo> result = filter.GetDailyWeather(weather, preference);
        if (result.size() == 4) {
            assertEquals("First", result.get(0).getDescription());
            assertEquals("Second", result.get(1).getDescription());
            assertEquals("Third", result.get(2).getDescription());
        }
    }

    @Test
    public void GetBestWeatherPrecipitation() throws Exception{
        Preference preference = new Preference(15, Cloudiness.Medium, 0, 23, 37, 10.0, 12.0);
        List<WeatherInfo> weather = new ArrayList<>();
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12.0, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),0,"Name","First"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,7.0, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),0,"Name","Second"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,14.0, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),0,"Name","Third"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,0.0, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),0,"Name"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,100.0, new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),0,"Name"));
        List<WeatherInfo> result = filter.GetDailyWeather(weather, preference);
        if (result.size() == 4) {
            assertEquals("First", result.get(0).getDescription());
            assertEquals("Second", result.get(1).getDescription());
            assertEquals("Third", result.get(2).getDescription());
        }
    }
    @Test
    public void GetWeeklyBestWeather() throws Exception{
        Preference preference = new Preference(15, Cloudiness.Medium, 0, 23, 37, 10.0, 12.0);
        List<WeatherInfo> weather = new ArrayList<>();
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,12.0, new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(1)),0,"Name","First"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,7.0, new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(2)),0,"Name","Second"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,14.0, new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(3)),0,"Name","Third"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,0.0, new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(2)),0,"Name"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,100.0, new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(1)),0,"Name"));
        weather.add(new WeatherInfo(13,12,Cloudiness.Big,100.0, new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(3)),0,"Name"));
        List<WeatherInfo> result = filter.GetWeeklyWeather(weather, preference);
        if (result.size() == 4) {
            assertEquals("First", result.get(0).getDescription());
            assertEquals("Second", result.get(1).getDescription());
            assertEquals("Third", result.get(2).getDescription());
        }
    }

}
