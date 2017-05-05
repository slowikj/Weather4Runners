package com.example.annabujak.weather4runners.DBManager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.Filter.WeatherFilter;

import org.junit.After;
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

public class DBManagerAndWeatherFilterUnitTests {

    Context appContext;
    DBManager manager;
    WeatherFilter filter;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        manager = new DBManager(appContext);
        filter = new WeatherFilter(4);
    }

    @After
    public void tearDown() throws Exception {
        manager.ClearAndClose();
    }

    @Test
    public void TestAddingJSON() throws Exception{
        List<WeatherInfo> weather = new ArrayList<WeatherInfo>();
        weather.add(new WeatherInfo(11,12, Cloudiness.Big,13.0,new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),14.0,"Icon"));
        weather.add(new WeatherInfo(15,16, Cloudiness.Medium,17.0,new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),18.0,"Icon1","Description"));
        manager.UpdateWeatherData(weather);
        List<WeatherInfo> newWeather = manager.GetWeatherData();
        assertEquals(newWeather.get(0).getIconName(),"Icon");
        assertEquals(newWeather.get(1).getHumidity(),16);
        assertEquals(newWeather.get(1).getDescription(),"Description");
    }
    @Test
    public void TestAddingDifferentDay() throws Exception{
        Preference preference = new Preference(15, Cloudiness.Big,10,17,12,12.0,12.0);

        List<WeatherInfo> weather = new ArrayList<WeatherInfo>();
        weather.add(new WeatherInfo(11,12, Cloudiness.Big,13.0,new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),14.0,"Icon"));
        weather.add(new WeatherInfo(15,16, Cloudiness.Medium,17.0,new Date((new Date()).getTime() + TimeUnit.HOURS.toMillis(1)),18.0,"Icon1","Description"));
        weather.add(new WeatherInfo(15,16, Cloudiness.Medium,17.0,new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(2)),18.0,"Icon1","Tomorrow"));

        manager.UpdateWeatherData(weather);
        List<WeatherInfo> newWeather = manager.GetWeatherData();
        List<WeatherInfo> dailyWeather = filter.GetDailyWeather(newWeather,preference);

        assertEquals(2,dailyWeather.size());
        assertEquals("Description",dailyWeather.get(1).getDescription());
    }
    @Test
    public void GetWeeklyWeatherTest() throws Exception{
        Preference preference = new Preference(15, Cloudiness.Big,10,17,12,12.0,12.0);

        List<WeatherInfo> weather = new ArrayList<WeatherInfo>();
        weather.add(new WeatherInfo(11,12, Cloudiness.Big,13.0,new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(3)),14.0,"Icon"));
        weather.add(new WeatherInfo(15,16, Cloudiness.Medium,17.0,new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(4)),18.0,"Icon1","Description"));
        weather.add(new WeatherInfo(15,16, Cloudiness.Medium,17.0,new Date((new Date()).getTime() + TimeUnit.DAYS.toMillis(5)),18.0,"Icon1","Tomorrow"));
        manager.UpdateWeatherData(weather);

        List<WeatherInfo> newWeather = manager.GetWeatherData();
        List<WeatherInfo> weeklyWeather = filter.GetWeeklyWeather(newWeather,preference);

        assertEquals(3,weeklyWeather.size());
        assertEquals("Tomorrow",weeklyWeather.get(2).getDescription());
    }
}
