package com.example.annabujak.weather4runners.DatabaseUnitTests;

import com.example.annabujak.weather4runners.Database.BestHour;
import com.example.annabujak.weather4runners.Database.ChosenHour;
import com.example.annabujak.weather4runners.Database.DBWeather4Runners;
import com.example.annabujak.weather4runners.Database.Preference;
import com.example.annabujak.weather4runners.Database.User;
import com.example.annabujak.weather4runners.Enum.Cloudiness;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;


import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class DBWeather4RunnersUnitTest {
    DBWeather4Runners database;

    @Before
    public void setUp() throws Exception {
        database = new DBWeather4Runners(null);
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Ignore
    @Test
    public void addPreferenceTest() throws Exception {
        Preference preference = new Preference(15, Cloudiness.Big,10,18);
        database.addPreference(preference);
        Preference actualPreference = database.getPreference(preference.getId());
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
        database.addBestHour(bestHour);
        BestHour actualBestHour = database.getBestHour(bestHour.getId());
        assertEquals(current,actualBestHour.getDate());
        assertEquals(18,actualBestHour.getDate());
    }
    @Ignore
    @Test
    public void addChosenHourTest() throws Exception {
        Date current = new Date();
        ChosenHour chosenHour = new ChosenHour(current,19);
        database.addChosenHour(chosenHour);
        ChosenHour actualChosenHour = database.getChosenHour(chosenHour.getId());
        assertEquals(current,actualChosenHour.getDate());
        assertEquals(19,actualChosenHour.getDate());
    }
    @Ignore
    @Test
    public void addUserTest() throws Exception {
        User user = new User("Ania","Bujak");
        long id = database.addUser(user);
        User actualUser = database.getUser(id);
        assertEquals("Ania",actualUser.getName());
        assertEquals("Bujak",actualUser.getSurname());
    }
    @Ignore
    @Test
    public void getAllChosenHoursTest() throws Exception {
        ChosenHour hourToAdd = new ChosenHour(new Date(),12);
        database.addChosenHour(hourToAdd);
        List<ChosenHour> allChosenHours = database.getAllChosenHours();
        assertTrue(allChosenHours.contains(hourToAdd));
    }

    @Ignore
    @Test
    public void getAllBestHoursTest() throws Exception {
        BestHour bestToAdd = new BestHour(new Date(),12);
        database.addBestHour(bestToAdd);
        List<BestHour> allBestHours = database.getAllBestHours();
        assertTrue(allBestHours.contains(bestToAdd));
    }

    @Ignore
    @Test
    public void updateUserTest() throws Exception {
        User ania = new User("Ania","Bujak");
        Long id = database.addUser(ania);
        ania.setNameAndSurname("Ania","Mazurkiewicz");
        database.updateUser(ania);
        User currentAnia = database.getUser(id);
        assertEquals("Mazurkiewicz",currentAnia.getSurname());
    }

    @Ignore
    @Test
    public void updateBestHourTest() throws Exception {
        Date current = new Date();
        BestHour hour = new BestHour(current,12);
        database.addBestHour(hour);
        hour.setDayAndHour(current,13);
        database.updateBestHour(hour);
        BestHour currentHour = database.getBestHour(hour.getId());
        assertEquals(13,currentHour.getHour());
    }

    @Ignore
    @Test
    public void updateChosenHourTest() throws Exception {
        Date current = new Date();
        ChosenHour hour = new ChosenHour(current,12);
        database.addChosenHour(hour);
        hour.setDayAndHour(current,13);
        database.updateChosenHour(hour);
        ChosenHour currentHour = database.getChosenHour(hour.getId());
        assertEquals(13,currentHour.getHour());
    }

    @Ignore
    @Test
    public void updatePreferenceTest() throws Exception {
        Preference preference = new Preference(15,Cloudiness.Big,14,16);
        database.addPreference(preference);
        preference.setCloudiness(Cloudiness.Medium);
        database.updatePreference(preference);
        Preference currentPreference = database.getPreference(preference.getId());
        assertEquals("Medium",currentPreference.getCloudiness().name());
    }

    @Ignore
    @Test
    public void clearPreferencesTest() throws Exception {
        Preference preference = new Preference(15,Cloudiness.Big,14,16);
        database.clearPreferences();
        database.addPreference(preference);
        assertEquals(1,preference.getId());
    }

    @Ignore
    @Test
    public void clearBestHoursTest() throws Exception {
        BestHour bestHour = new BestHour(new Date(),15);
        database.clearBestHours();
        database.addBestHour(bestHour);
        assertEquals(1,bestHour.getId());
    }

    @Ignore
    @Test
    public void clearChosenHoursTest() throws Exception {
        ChosenHour chosenHour = new ChosenHour(new Date(),15);
        database.clearChosenHours();
        database.addChosenHour(chosenHour);
        assertEquals(1,chosenHour.getId());
    }

    @Ignore
    @Test
    public void clearUserTest() throws Exception {
        User user = new User();
        database.clearUser();
        database.addUser(user);
        assertEquals(1,user.getId());
    }

    @Ignore
    @Test
    public void deleteChosenHourTest() throws Exception{
        ChosenHour hour = new ChosenHour(new Date(),12);
        database.addChosenHour(hour);
        database.deleteChosenHour(hour.getId());
        ChosenHour getted = database.getChosenHour(hour.getId());
        assertNull(getted);
    }

    @Ignore
    @Test
    public void deleteBestHourTest() throws Exception{
        BestHour hour = new BestHour(new Date(),12);
        database.addBestHour(hour);
        database.deleteBestHour(hour.getId());
        BestHour getted = database.getBestHour(hour.getId());
        assertNull(getted);
    }
}
