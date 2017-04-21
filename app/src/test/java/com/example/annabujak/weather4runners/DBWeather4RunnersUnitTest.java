package com.example.annabujak.weather4runners;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;
import android.test.suitebuilder.annotation.SmallTest;

import com.example.annabujak.weather4runners.Database.BestHour;
import com.example.annabujak.weather4runners.Database.ChosenHour;
import com.example.annabujak.weather4runners.Database.DBWeather4Runners;
import com.example.annabujak.weather4runners.Database.Preference;
import com.example.annabujak.weather4runners.Enum.Cloudiness;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class DBWeather4RunnersUnitTest {
    @Mock
    Context context;
    DBWeather4Runners database;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = new MockContext();
       // context.deleteDatabase(database.getDatabaseName());
        database = new DBWeather4Runners(context.getApplicationContext());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Ignore
    @Test
    public void addPreferenceTest() throws Exception {
        Preference preference = new Preference(0,15, Cloudiness.Big,10,18);
        long id = database.addPreference(preference);
        Preference actualPreference = database.getPreference(id);
        assertEquals(15,actualPreference.getTemperature());
        assertEquals(10,actualPreference.getStartHour());
        assertEquals(18,actualPreference.getEndHour());
        assertEquals("Big",actualPreference.getCloudiness().name());
    }

    @Ignore
    @Test
    public void addBestHourTest() throws Exception {
        Date current = new Date();
        BestHour bestHour = new BestHour(current,18);
        long id = database.addBestHour(bestHour);
        BestHour actualBestHour = database.getBestHour(id);
        assertEquals(current,actualBestHour.getDate());
        assertEquals(18,actualBestHour.getDate());
    }
    @Ignore
    @Test
    public void addChosenHourTest() throws Exception {
        Date current = new Date();
        ChosenHour chosenHour = new ChosenHour(current,19);
        long id = database.addChosenHour(chosenHour);
        ChosenHour actualChosenHour = database.getChosenHour(id);
        assertEquals(current,actualChosenHour.getDate());
        assertEquals(19,actualChosenHour.getDate());
    }
}
