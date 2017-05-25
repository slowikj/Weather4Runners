package com.example.annabujak.weather4runners.Weather.Filter;

import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.PreferenceBalance;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherFilter {

    private Double MilisecondsToDays = 1.0/100000000.0;
    private WeatherAlgorithm Algorithm;

    public WeatherFilter(int _WeatherPropositions, PreferenceBalance _Balance){
        Algorithm = new WeatherAlgorithm(_WeatherPropositions, _Balance);
    }

    public ArrayList<WeatherInfo> GetDailyWeather(List<WeatherInfo> weather, Preference preference){
        ArrayList<WeatherInfo> dailyWeather = new ArrayList<>();
        for (WeatherInfo w: weather) {
            if(IsWeatherForDay(w)){
                dailyWeather.add(w);
            }
        }
        return Algorithm.FindBestDailyWeather(dailyWeather,preference);
    }
    private boolean IsWeatherForDay(WeatherInfo w){
        Date now = new Date();
        Date forecastTime = new Date(w.getDate());
        if(forecastTime.getTime() - now.getTime() < 0)
            return false;
        return (double)(forecastTime.getTime() - now.getTime())*MilisecondsToDays <= 1 ? true : false;
    }

    public ArrayList<WeatherInfo> GetWeeklyWeather(List<WeatherInfo> weather, Preference preference){
        ArrayList<WeatherInfo> weeklyWeather = new ArrayList<>();
        for (WeatherInfo w: weather) {
            if(IsWeatherForWeek(w)){
                weeklyWeather.add(w);
            }
        }
        return Algorithm.FindBestWeeklyWeather(weeklyWeather,preference);
    }
    private boolean IsWeatherForWeek(WeatherInfo w){
        Date now = new Date();
        Date forecastTime = new Date(w.getDate());
        Integer DaysInWeek = 7;
        if(forecastTime.getTime() - now.getTime() < 0)
            return false;
        return (double)(forecastTime.getTime() - now.getTime())*MilisecondsToDays <= DaysInWeek ? true : false;
    }
}
