package com.example.annabujak.weather4runners.Weather.Filter;

import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherAlgorithm {


    private Integer PropositionsCount;
    private Preference Preference;

    public WeatherAlgorithm(Integer _PropositionsCount){
        PropositionsCount = _PropositionsCount;
    }

    public List<WeatherInfo> FindBestWeather(List<WeatherInfo> weather, Preference preference){

        Preference = preference;
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
        List<WeatherInfo> finalList = new ArrayList<>();
        for(int i = 0; i < PropositionsCount; i ++)
            finalList.add(preferenceList.get(i).left);
        return finalList;
    }
    private boolean IsInPreferenceTime(WeatherInfo w){
        return w.getDate().getHours() < Preference.getStartHour() || w.getDate().getHours() > Preference.getEndHour() ? false : true;
    }
    private Double CountWeatherPreference(WeatherInfo w){
        Double result = 0.0;

        result += GetTemperatureValue(w);
        result += GetCloudiness(w);

        return result;
    }
    private Double GetTemperatureValue(WeatherInfo w){
        Double MinTemp = -20.0;
        Double MaxTemp = 40.0;
        Double Divider = (MaxTemp - MinTemp)/4.0;

        Double TemporaryTopResult = ((Preference.getTemperature() - MinTemp) - (w.getTemperature()-MinTemp))/Divider;
        Double TemporaryBottomResult = (MaxTemp - MinTemp)/Divider;
        return Math.abs(TemporaryTopResult/TemporaryBottomResult);
    }
    private Double GetCloudiness(WeatherInfo w){
        Double MinCloud = 0.0;
        Double MaxCloud = 4.0;

        Double TemporaryTopResult = ((Preference.getCloudiness().getValue() - MinCloud) - (w.getCloudiness().getValue()-MinCloud));
        Double TemporaryBottomResult = (MaxCloud - MinCloud);
        return Math.abs(TemporaryTopResult/TemporaryBottomResult);
    }
}
