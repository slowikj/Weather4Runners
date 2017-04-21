package com.example.annabujak.weather4runners.DatabaseUnitTests;

import com.example.annabujak.weather4runners.Database.Preference;
import com.example.annabujak.weather4runners.Enum.Cloudiness;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class TBPreferencesUnitTests {
    Preference preference = new Preference();

    @Test
    public void setAndGetPreferedTemperatureTest() throws Exception{
        preference.setTemperature(20);
        int preferedTemperature = preference.getTemperature();
        assertEquals(20,preferedTemperature);
    }

    @Test
    public void setAndGetPreferencedCloudiness() throws Exception{
        preference.setCloudiness(Cloudiness.Big);
        Cloudiness prefered = preference.getCloudiness();
        assertEquals("Big",prefered.name());
    }
    @Test
    public void setAndGetPreferedHours() throws Exception{
        preference.setHours(9,20);
        int startHour = preference.getStartHour();
        int endHour = preference.getEndHour();
        assertEquals(9,startHour);
        assertEquals(20,endHour);
    }
    @Test
    public void setIdTest() throws Exception{
        preference.setId(1);
        assertEquals(1,preference.getId());
    }
}
