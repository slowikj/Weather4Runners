package com.example.annabujak.weather4runners.Weather;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherFilter {

    private static Double MilisecondsToDays = 1.0/100000000.0;

    public static List<WeatherInfo> GetDailyWeather(List<WeatherInfo> weather){
        List<WeatherInfo> dailyWeather = new ArrayList<>();
        for (WeatherInfo w: weather) {
            if(IsWeatherForDay(w)){
                dailyWeather.add(w);
            }
        }
        return dailyWeather;
    }
    private static boolean IsWeatherForDay(WeatherInfo w){
        Date now = new Date();
        if(w.getDate().getTime() - now.getTime() < 0)
            return false;
        return (double)(w.getDate().getTime() - now.getTime())*MilisecondsToDays <= 1 ? true : false;
    }

    public static List<WeatherInfo> GetWeeklyWeather(List<WeatherInfo> weather){
        List<WeatherInfo> weeklyWeather = new ArrayList<>();
        for (WeatherInfo w: weather) {
            if(IsWeatherForWeek(w)){
                weeklyWeather.add(w);
            }
        }
        return weeklyWeather;
    }
    private static boolean IsWeatherForWeek(WeatherInfo w){
        Date now = new Date();
        Integer DaysInWeek = 7;
        if(w.getDate().getTime() - now.getTime() < 0)
            return false;
        return (double)(w.getDate().getTime() - now.getTime())*MilisecondsToDays <= DaysInWeek ? true : false;
    }
}
