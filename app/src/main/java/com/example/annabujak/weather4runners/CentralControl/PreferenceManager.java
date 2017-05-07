package com.example.annabujak.weather4runners.CentralControl;

import android.content.Context;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.Filter.WeatherFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class PreferenceManager implements WeatherForecastChangedListener {

    private PropositionsChangedListener propositionsChangedListener;

    private DBManager DatabaseManager;
    private WeatherFilter Filter;

    public PreferenceManager(Context context){
        DatabaseManager = new DBManager(context);
        Filter = new WeatherFilter(4);
    }

    public void setPropositionsChangedListener(PropositionsChangedListener propositionsChangedListener) {
        this.propositionsChangedListener = propositionsChangedListener;
    }

    public List<WeatherInfo> UpdatePreferences(Preference preference, String dailyOrWeekly){// Daily - "D", Weekly - "W"
        DatabaseManager.UpdatePreferences(preference);
        List<WeatherInfo> AllWeatherData = DatabaseManager.GetWeatherData();
        if(dailyOrWeekly.equals("D"))
            return Filter.GetDailyWeather(AllWeatherData,preference);
        return  Filter.GetWeeklyWeather(AllWeatherData,preference);
    }

    @Override
    public void onWeatherForecastChanged(ArrayList<WeatherInfo> hourlyForecasts) {
        
    }
}
