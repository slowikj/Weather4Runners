package com.example.annabujak.weather4runners.CentralControl;

import android.content.Context;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.WeatherFilter;

import java.util.List;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class PreferenceManager {
    private DBManager DatabaseManager;

    public PreferenceManager(Context context){
        DatabaseManager = new DBManager(context);
    }
    public List<WeatherInfo> UpdatePreferences(Preference preference, String dailyOrWeekly){// Daily - "D", Weekly - "W"
        DatabaseManager.UpdatePreferences(preference);
        List<WeatherInfo> AllWeatherData = DatabaseManager.GetWeatherData();
        if(dailyOrWeekly == "D")
            return WeatherFilter.GetDailyWeather(AllWeatherData);
        return  WeatherFilter.GetWeeklyWeather(AllWeatherData);
    }

}
