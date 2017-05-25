package com.example.annabujak.weather4runners.Objects;

import android.util.Pair;

import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pawel.bujak on 14.05.2017.
 */

public class PreferenceBalance {

    private static int CONDITIONS_COUNT = 5;

    private double TemperatureImportance;
    private double CloudinessImportance;
    private double HumidityImportance;
    private double PrecipitationImportance;
    private double WindSpeedImportance;

    public PreferenceBalance(double _temperatureImportance, double _cloudinessImportance, double _humidityImportance,
                             double _precipitationImportance, double _windSpeedImportance)
    {
        TemperatureImportance = _temperatureImportance;
        CloudinessImportance = _cloudinessImportance;
        HumidityImportance = _humidityImportance;
        PrecipitationImportance = _precipitationImportance;
        WindSpeedImportance = _windSpeedImportance;
    }

    public PreferenceBalance() {
        this(new ArrayList<WeatherConditionsNames>(
                Arrays.asList(WeatherConditionsNames.values())));
    }

    public PreferenceBalance(ArrayList<WeatherConditionsNames> conditionsImportanceOrder) {
        for(int i = 0; i < conditionsImportanceOrder.size(); ++i) {
            double importanceValue = i * (1.0 / CONDITIONS_COUNT);
            WeatherConditionsNames conditionName = conditionsImportanceOrder.get(i);
            switch(conditionName) {
                case Cloudiness:
                    this.CloudinessImportance = importanceValue;
                    break;
                case Precipitation:
                    this.PrecipitationImportance = importanceValue;
                    break;
                case Wind:
                    this.WindSpeedImportance = importanceValue;
                    break;
                case Humidity:
                    this.HumidityImportance = importanceValue;
                    break;
                case Temperature:
                    this.TemperatureImportance = importanceValue;
                    break;
            }
        }
    }

    public ArrayList<WeatherConditionsNames> getWeatherConditionsOrder() {
        ArrayList<Pair<Double, WeatherConditionsNames>> weatherImportance = new ArrayList<>();
        weatherImportance.add(new Pair<Double, WeatherConditionsNames>(this.CloudinessImportance, WeatherConditionsNames.Cloudiness));
        weatherImportance.add(new Pair<Double, WeatherConditionsNames>(this.PrecipitationImportance, WeatherConditionsNames.Precipitation));
        weatherImportance.add(new Pair<Double, WeatherConditionsNames>(this.WindSpeedImportance, WeatherConditionsNames.Wind));
        weatherImportance.add(new Pair<Double, WeatherConditionsNames>(this.HumidityImportance, WeatherConditionsNames.Humidity));
        weatherImportance.add(new Pair<Double, WeatherConditionsNames>(this.TemperatureImportance, WeatherConditionsNames.Temperature));

        Collections.sort(weatherImportance, new Comparator<Pair<Double, WeatherConditionsNames>>() {
            @Override
            public int compare(Pair<Double, WeatherConditionsNames> o1, Pair<Double, WeatherConditionsNames> o2) {
                return (int)(o1.first - o2.first);
            }
        });

        ArrayList<WeatherConditionsNames> res = new ArrayList<>();
        final double EPS = 0.0001;
        for(Pair<Double, WeatherConditionsNames> weatherConditionPair: weatherImportance) {
            if(Math.abs(weatherConditionPair.first) >= EPS) {
                res.add(weatherConditionPair.second);
            }
        }

        return res;
    }

    public double GetTemperatureImportance(){return TemperatureImportance;}
    public double GetCloudinessImportance(){return CloudinessImportance;}
    public double GetHumidityImportance(){return HumidityImportance;}
    public double GetPrecipitationImportance(){return PrecipitationImportance;}
    public double GetWindSpeedImportance(){return WindSpeedImportance;}
}
