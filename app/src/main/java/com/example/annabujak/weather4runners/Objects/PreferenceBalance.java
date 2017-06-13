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

    private static final double EPS = 0.0001;

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
        int conditionsImportanceListSize = conditionsImportanceOrder.size();
        for(int i = 0; i < conditionsImportanceListSize; ++i) {
            double importanceValue = (conditionsImportanceListSize - i) * (1.0 / (conditionsImportanceListSize + 1));
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
        weatherImportance.add(new Pair<>(this.CloudinessImportance, WeatherConditionsNames.Cloudiness));
        weatherImportance.add(new Pair<>(this.PrecipitationImportance, WeatherConditionsNames.Precipitation));
        weatherImportance.add(new Pair<>(this.WindSpeedImportance, WeatherConditionsNames.Wind));
        weatherImportance.add(new Pair<>(this.HumidityImportance, WeatherConditionsNames.Humidity));
        weatherImportance.add(new Pair<>(this.TemperatureImportance, WeatherConditionsNames.Temperature));

        Collections.sort(weatherImportance, new Comparator<Pair<Double, WeatherConditionsNames>>() {
            @Override
            public int compare(Pair<Double, WeatherConditionsNames> o1, Pair<Double, WeatherConditionsNames> o2) {
                double r = -(o1.first - o2.first);
                if (r > 0) {
                    return 1;
                } else if (r < 0) {
                    return -1;
                } else {
                    return 0;
                }
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferenceBalance that = (PreferenceBalance) o;

        if (Math.abs(that.TemperatureImportance - TemperatureImportance) > EPS) return false;
        if (Math.abs(that.CloudinessImportance - CloudinessImportance) > EPS) return false;
        if (Math.abs(that.HumidityImportance - HumidityImportance) > EPS) return false;
        if (Math.abs(that.PrecipitationImportance - PrecipitationImportance) >  EPS)
            return false;
        return Math.abs(that.WindSpeedImportance - WindSpeedImportance) > EPS;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(TemperatureImportance);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(CloudinessImportance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(HumidityImportance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(PrecipitationImportance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(WindSpeedImportance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
