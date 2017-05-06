package com.example.annabujak.weather4runners.Weather.Approximators;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;

/**
 * Created by slowik on 05.05.2017.
 */

public abstract class WeatherInfosApproximatorFactory {

    public abstract WeatherInfosApproximator create(
            WeatherInfo begin, WeatherInfo end, int cnt);
}
