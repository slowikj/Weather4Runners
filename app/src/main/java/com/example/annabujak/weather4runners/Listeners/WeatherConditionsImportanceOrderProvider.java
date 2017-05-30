package com.example.annabujak.weather4runners.Listeners;

import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;

import java.util.ArrayList;

/**
 * Created by slowik on 30.05.2017.
 */

public interface WeatherConditionsImportanceOrderProvider {
    ArrayList<WeatherConditionsNames> getWeatherConditionsImportanceOrder();
}
