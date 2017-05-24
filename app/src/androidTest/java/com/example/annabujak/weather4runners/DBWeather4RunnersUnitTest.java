package com.example.annabujak.weather4runners;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.annabujak.weather4runners.Objects.BestHour;
import com.example.annabujak.weather4runners.Objects.ChosenHour;
import com.example.annabujak.weather4runners.Database.DBWeather4Runners;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;


import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by pawel.bujak on 21.04.2017.
 */
@RunWith(AndroidJUnit4.class)
public class DBWeather4RunnersUnitTest {
    DBWeather4Runners database;
    Context appContext;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        database = new DBWeather4Runners(appContext);
    }

    @After
    public void tearDown() throws Exception {
        database.clearBestHours();
        database.clearUser();
        database.clearChosenHours();
        database.clearPreferences();
        database.close();
    }

    @Test
    public void addPreferenceTest() throws Exception {
        Preference preference = new Preference(15, Cloudiness.Big,10,18,17,20L,22.0);
        database.addPreference(preference);
        Preference actualPreference = database.getPreference(preference.getId());
        assertEquals(15,actualPreference.getTemperature());
        assertEquals(10,actualPreference.getStartHour());
        assertEquals(18,actualPreference.getEndHour());
        assertEquals(17,actualPreference.getHumidity());
        assertEquals(20.0, actualPreference.getPrecipitation());
        assertEquals("Big",actualPreference.getCloudiness().name());
        assertEquals(22.0,actualPreference.getWindSpeed());
    }

    @Test
    public void addBestHourTest() throws Exception {
        Date current = new Date();
        BestHour bestHour = new BestHour(current,18);
        database.addBestHour(bestHour);
        BestHour actualBestHour = database.getBestHour(bestHour.getId());
        assertEquals(current.toString(),actualBestHour.getDate().toString());
        assertEquals(18,actualBestHour.getHour());
    }

    @Test
    public void addChosenHourTest() throws Exception {
        Date current = new Date();
        ChosenHour chosenHour = new ChosenHour(current,false);
        database.addChosenHour(chosenHour);
        ChosenHour actualChosenHour = database.getChosenHour(chosenHour.getId());
        assertEquals(current.toString(),actualChosenHour.getDate().toString());
        assertEquals(false,actualChosenHour.getIsHour());
    }

    @Test
    public void addUserTest() throws Exception {
        User user = new User("Ania","Bujak");
        long id = database.addUser(user);
        User actualUser = database.getUser(id);
        assertEquals("Ania",actualUser.getName());
        assertEquals("Bujak",actualUser.getSurname());
    }

    @Test
    public void getAllChosenHoursTest() throws Exception {

        final ChosenHour chosenToAdd = new ChosenHour(new Date(),true);
        List<ChosenHour> allChosenHours;
        boolean tookAll = false;

        database.addChosenHour(chosenToAdd);
        allChosenHours = database.getAllChosenHours();

        for(ChosenHour hour : allChosenHours) {
            if(new Long(hour.getId()).toString().equals(new Long(chosenToAdd.getId()).toString())) {
                tookAll = true;
            }
        }
        assertTrue(tookAll);
    }


    @Test
    public void getAllBestHoursTest() throws Exception {

        final BestHour bestToAdd = new BestHour(new Date(),12);
        List<BestHour> allBestHours;
        boolean tookAll = false;

        database.addBestHour(bestToAdd);
        allBestHours = database.getAllBestHours();

        for(BestHour hour : allBestHours) {
            if(new Long(hour.getId()).toString().equals(new Long(bestToAdd.getId()).toString())) {
                tookAll = true;
            }
        }
        assertTrue(tookAll);
    }


    @Test
    public void updateUserTest() throws Exception {
        User ania = new User("Ania","Bujak");
        Long id = database.addUser(ania);
        ania.setNameAndSurname("Ania","Mazurkiewicz");
        database.updateUser(ania);
        User currentAnia = database.getUser(id);
        assertEquals("Mazurkiewicz",currentAnia.getSurname());
    }


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


    @Test
    public void updateChosenHourTest() throws Exception {
        Date current = new Date();
        ChosenHour hour = new ChosenHour(current,true);
        database.addChosenHour(hour);
        hour.setDayAnIsdHour(current,false);
        database.updateChosenHour(hour);
        ChosenHour currentHour = database.getChosenHour(hour.getId());
        assertEquals(false,currentHour.getIsHour());
    }


    @Test
    public void updatePreferenceTest() throws Exception {
        Preference preference = new Preference(15,Cloudiness.Big,14,16,19,31L,22.0);
        database.addPreference(preference);
        preference.setCloudiness(Cloudiness.Medium);
        preference.setHumidity(21);
        preference.setWindSpeed(21.0);
        database.updatePreference(preference);
        Preference currentPreference = database.getPreference(preference.getId());
        assertEquals("Medium",currentPreference.getCloudiness().name());
        assertEquals(21,currentPreference.getHumidity());
        assertEquals(21.0,currentPreference.getWindSpeed());
    }


    @Test
    public void clearPreferencesTest() throws Exception {
        Preference preference = new Preference(15,Cloudiness.Big,14,16,19,31L,22.0);
        database.clearPreferences();
        database.addPreference(preference);
        assertEquals(1,preference.getId());
    }


    @Test
    public void clearBestHoursTest() throws Exception {
        BestHour bestHour = new BestHour(new Date(),15);
        database.clearBestHours();
        database.addBestHour(bestHour);
        assertEquals(1,bestHour.getId());
    }


    @Test
    public void clearChosenHoursTest() throws Exception {
        ChosenHour chosenHour = new ChosenHour(new Date(),false);
        database.clearChosenHours();
        database.addChosenHour(chosenHour);
        assertEquals(1,chosenHour.getId());
    }


    @Test
    public void clearUserTest() throws Exception {
        User user = new User();
        database.clearUser();
        database.addUser(user);
        assertEquals(1,user.getId());
    }


    @Test
    public void deleteChosenHourTest() throws Exception{
        ChosenHour hour = new ChosenHour(new Date(),false);
        database.addChosenHour(hour);
        database.deleteChosenHour(hour.getId());
        ChosenHour getted = database.getChosenHour(hour.getId());
        assertNull(getted);
    }


    @Test
    public void deleteBestHourTest() throws Exception{
        BestHour hour = new BestHour(new Date(),12);
        database.addBestHour(hour);
        database.deleteBestHour(hour.getId());
        BestHour gotten = database.getBestHour(hour.getId());
        assertNull(gotten);
    }


    @Test
    public void addWeatherInfo() throws Exception{
        Date now = new Date();
        WeatherInfo weatherInfo = new WeatherInfo(15,14,Cloudiness.Big,20.0,now,22.0,"Icon","Desc");
        weatherInfo.setIsChecked(true);
        database.addWeatherInfo(weatherInfo);
        WeatherInfo newWeather = database.getWeatherInfo(weatherInfo.getId());
        assertEquals(newWeather.getPrecipitation(),weatherInfo.getPrecipitation());
        assertEquals(newWeather.getHumidity(),weatherInfo.getHumidity());
        assertEquals(newWeather.getCloudiness().toString(),"Big");
        assertEquals(weatherInfo.getDate().toString(),newWeather.getDate().toString());
        assertEquals(weatherInfo.getTemperature(),newWeather.getTemperature());
        assertEquals(weatherInfo.getIsChecked(),true);
        assertEquals(weatherInfo.getWindSpeed(),22.0);
        assertEquals("Icon",weatherInfo.getIconName());
        assertEquals("Desc",weatherInfo.getDescription());
    }

    @Test
    public void deleteWatherInfo() throws Exception{
        WeatherInfo weatherInfo = new WeatherInfo(15,14,Cloudiness.Big,20.0, new Date(),22.0,"Icon");
        database.addWeatherInfo(weatherInfo);
        database.deleteWeatherInfo(weatherInfo.getId());
        WeatherInfo gotten = database.getWeatherInfo(weatherInfo.getId());
        assertNull(gotten);
    }

    @Test
    public void clearWeatherInfoTest() throws Exception {
        WeatherInfo weatherInfo = new WeatherInfo(15,14,Cloudiness.Big,20.0, new Date(),22.0,"Icon");
        database.clearWeatherInfo();
        database.addWeatherInfo(weatherInfo);
        assertEquals(1,weatherInfo.getId());
    }

    @Test
    public void updateWeatherInfoTest() throws Exception {
        WeatherInfo weatherInfo = new WeatherInfo(15,14,Cloudiness.Big,20.0, new Date(),22.0,"Icon");
        database.addWeatherInfo(weatherInfo);
        weatherInfo.setIsChecked(true);
        weatherInfo.setIconName("NewIcon");
        database.updateWeatherInfo(weatherInfo);
        WeatherInfo currentWeatherInfo = database.getWeatherInfo(weatherInfo.getId());
        assertEquals(true,currentWeatherInfo.getIsChecked());
        assertEquals(22.0,currentWeatherInfo.getWindSpeed());
        assertEquals("NewIcon",currentWeatherInfo.getIconName());
    }

    @Test
    public void getAllWeatherInfos() throws Exception{
        WeatherInfo weatherInfo = new WeatherInfo(15,14,Cloudiness.Big,20.0, new Date(),22.0,"Icon");
        List<WeatherInfo> allWeatherInfos;
        boolean tookAll = false;

        database.addWeatherInfo(weatherInfo);
        allWeatherInfos = database.getAllWeatherInfos();

        for(WeatherInfo weather : allWeatherInfos) {
            if(new Long(weather.getId()).toString().equals(new Long(weatherInfo.getId()).toString())) {
                tookAll = true;
            }
        }
        assertTrue(tookAll);
    }
}
