package com.example.annabujak.weather4runners.Weather.Filter;

import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.PreferenceBalance;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherAlgorithm {


    private Integer PropositionsCount;
    private PreferenceBalance Balance;
    private Preference Preference;
    private final Integer DaysInWeek = 7;

    public WeatherAlgorithm(Integer _PropositionsCount, PreferenceBalance _Balance){
        PropositionsCount = _PropositionsCount;
        Balance = _Balance;
    }

    public ArrayList<WeatherInfo> FindBestDailyWeather(ArrayList<WeatherInfo> weatherInfos, Preference preference){
        Preference = preference;
        return FindBestWeather(weatherInfos,PropositionsCount);
    }
    public ArrayList<WeatherInfo> FindBestWeeklyWeather(ArrayList<WeatherInfo> weatherInfo, Preference preference){
        Preference = preference;
        Map<Integer,ArrayList<WeatherInfo>> WeekDays = InitializeWeekDaysMap();
        for (WeatherInfo w:weatherInfo) {
            WeekDays.get(w.getDate().getDay()%DaysInWeek).add(w);
        }
        ArrayList<WeatherInfo> bestInWeek = new ArrayList<>();
        for(int i = 0; i < DaysInWeek; i ++){
            if(FindBestWeather(WeekDays.get(i),1).size() > 0)
                bestInWeek.add(FindBestWeather(WeekDays.get(i),1).get(0));
        }
        return FindBestWeather(bestInWeek,PropositionsCount);
    }
    private Map<Integer,ArrayList<WeatherInfo>> InitializeWeekDaysMap(){
        Map<Integer,ArrayList<WeatherInfo>> week = new HashMap<>();
        week.put(0,new ArrayList<WeatherInfo>());
        week.put(1,new ArrayList<WeatherInfo>());
        week.put(2,new ArrayList<WeatherInfo>());
        week.put(3,new ArrayList<WeatherInfo>());
        week.put(4,new ArrayList<WeatherInfo>());
        week.put(5,new ArrayList<WeatherInfo>());
        week.put(6,new ArrayList<WeatherInfo>());
        return week;
    }
    private ArrayList<WeatherInfo> FindBestWeather(ArrayList<WeatherInfo> weather, Integer bestWeatherCount){

        List<WeatherPreferencePair<WeatherInfo,Double>> preferenceList = new ArrayList<>();
        for (WeatherInfo w : weather) {
            if(IsInPreferenceTime(w)){
                Double preferedValue = CountWeatherPreference(w);
                preferenceList.add(new WeatherPreferencePair<WeatherInfo, Double>(w,preferedValue));
            }
        }
        Collections.sort(preferenceList, new Comparator<WeatherPreferencePair<WeatherInfo, Double>>() {
            @Override
            public int compare(WeatherPreferencePair<WeatherInfo, Double> o1, WeatherPreferencePair<WeatherInfo, Double> o2) {
                return o1.right.compareTo(o2.right);
            }
        });
        ArrayList<WeatherInfo> finalList = new ArrayList<>();
        for(int i = 0; i < Math.min(bestWeatherCount,preferenceList.size()); i ++)
            finalList.add(preferenceList.get(i).left);
        return finalList;
    }
    private boolean IsInPreferenceTime(WeatherInfo w){
        return w.getDate().getHours() < Preference.getStartHour() || w.getDate().getHours() > Preference.getEndHour() ? false : true;
    }
    private Double CountWeatherPreference(WeatherInfo w){
        Double result = 0.0;
        Double CloudinessImportance = 0.1;


        result += GetTemperatureValue(w) * Balance.GetTemperatureImportance();
        result += GetCloudinessValue(w) * Balance.GetCloudinessImportance(); //* CloudinessImportance;
        result += GetHumidityValue(w) * Balance.GetHumidityImportance();
        result += GetWindSpeedValue(w) * Balance.GetWindSpeedImportance();
        result += GetPrecipitationValue(w) * Balance.GetPrecipitationImportance();

        return result;
    }
    private Double GetTemperatureValue(WeatherInfo w){
        Double MinTemp = -20.0;
        Double MaxTemp = 40.0;

        Double TemporaryTopResult = ((Preference.getTemperature() - MinTemp) - (w.getTemperature()-MinTemp));
        Double TemporaryBottomResult = (MaxTemp - MinTemp);
        return Math.abs(TemporaryTopResult/TemporaryBottomResult);
    }
    private Double GetCloudinessValue(WeatherInfo w){
        Double MinCloud = 0.0;
        Double MaxCloud = 4.0;

        Double TemporaryTopResult = ((Preference.getCloudiness().getValue() - MinCloud) - (w.getCloudiness().getValue()-MinCloud));
        Double TemporaryBottomResult = (MaxCloud - MinCloud);
        return Math.abs(TemporaryTopResult/TemporaryBottomResult);
    }
    private Double GetHumidityValue(WeatherInfo w){
        Double MinHum = 0.0;
        Double MaxHum = 100.0;

        Double TemporaryTopResult = ((Preference.getHumidity() - MinHum) - (w.getHumidity()-MinHum));
        Double TemporaryBottomResult = (MaxHum - MinHum);
        return Math.abs(TemporaryTopResult/TemporaryBottomResult);
    }
    private Double GetWindSpeedValue(WeatherInfo w){
        Double MinWind = 0.0;
        Double MaxWind = 100.0;

        Double TemporaryTopResult = ((Preference.getWindSpeed() - MinWind) - (w.getWindSpeed()-MinWind));
        Double TemporaryBottomResult = (MaxWind - MinWind);
        return Math.abs(TemporaryTopResult/TemporaryBottomResult);
    }
    private Double GetPrecipitationValue(WeatherInfo w){
        Double MinPre = 0.0;
        Double MaxPre = 500.0;

        Double TemporaryTopResult = ((Preference.getPrecipitation() - MinPre) - (w.getPrecipitation()-MinPre));
        Double TemporaryBottomResult = (MaxPre - MinPre);
        return Math.abs(TemporaryTopResult/TemporaryBottomResult);
    }
}
