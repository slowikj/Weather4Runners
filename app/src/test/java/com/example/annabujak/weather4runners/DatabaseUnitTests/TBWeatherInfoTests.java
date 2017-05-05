package com.example.annabujak.weather4runners.DatabaseUnitTests;

import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 28.04.2017.
 */

public class TBWeatherInfoTests {
    WeatherInfo weather;

    @Test
    public void getWeather() {
        Date now = new Date();
        WeatherInfo wf = new WeatherInfo(20,14, Cloudiness.Big,23.0,now, 25.0, "NazwaIkony");
        assertEquals(wf.getTemperature(),20);
        assertEquals(wf.getCloudiness().toString(),"Big");
        assertEquals(wf.getDate().toString(),now.toString());
        assertEquals(wf.getHumidity(),14);
        assertEquals(wf.getPrecipitation(),23.0);
        assertEquals(wf.getWindSpeed(), 25.0);
        assertEquals(wf.getIconName(),"NazwaIkony");
        assertEquals(wf.getDescription(),"");
    }
}
