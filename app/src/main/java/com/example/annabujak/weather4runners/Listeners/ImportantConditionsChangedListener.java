package com.example.annabujak.weather4runners.Listeners;

import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;

import java.util.ArrayList;

/**
 * Created by slowik on 24.05.2017.
 */

public interface ImportantConditionsChangedListener {
    void onImportantConditionsChangedListener(
            ArrayList<WeatherConditionsNames> orderedImportantConditions);
}
