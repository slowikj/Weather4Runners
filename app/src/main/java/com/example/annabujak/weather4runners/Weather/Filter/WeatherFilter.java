package com.example.annabujak.weather4runners.Weather.Filter;

import com.example.annabujak.weather4runners.Objects.Preference;
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

    public WeatherFilter(int _WeatherPropositions){
        Algorithm = new WeatherAlgorithm(_WeatherPropositions);
    }

    public List<WeatherInfo> GetDailyWeather(List<WeatherInfo> weather, Preference preference){
        List<WeatherInfo> dailyWeather = new ArrayList<>();
        for (WeatherInfo w: weather) {
            if(IsWeatherForDay(w)){
                dailyWeather.add(w);
            }
        }
        return Algorithm.FindBestDailyWeather(dailyWeather,preference);
    }
    private boolean IsWeatherForDay(WeatherInfo w){
        Date now = new Date();
        if(w.getDate().getTime() - now.getTime() < 0)
            return false;
        return (double)(w.getDate().getTime() - now.getTime())*MilisecondsToDays <= 1 ? true : false;
    }

    public List<WeatherInfo> GetWeeklyWeather(List<WeatherInfo> weather, Preference preference){
        List<WeatherInfo> weeklyWeather = new ArrayList<>();
        for (WeatherInfo w: weather) {
            if(IsWeatherForWeek(w)){
                weeklyWeather.add(w);
            }
        }
        return Algorithm.FindBestWeeklyWeather(weeklyWeather,preference);
    }
    private boolean IsWeatherForWeek(WeatherInfo w){
        Date now = new Date();
        Integer DaysInWeek = 7;
        if(w.getDate().getTime() - now.getTime() < 0)
            return false;
        return (double)(w.getDate().getTime() - now.getTime())*MilisecondsToDays <= DaysInWeek ? true : false;
    }
}
