package com.example.annabujak.weather4runners.Weather.Approximators;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;

/**
 * Created by slowik on 05.05.2017.
 */

public abstract class WeatherInfosApproximator {

    protected WeatherInfo begin, end;
    protected int allItemsCnt;

    public WeatherInfosApproximator(WeatherInfo begin, WeatherInfo end, int allItemsCnt) {

        this.begin = begin;
        this.end = end;
        this.allItemsCnt = allItemsCnt;
    }

    // it returns allItemsCnt - 1 elements (all without the last one)
    public abstract ArrayList<WeatherInfo> getApproximated();
}
