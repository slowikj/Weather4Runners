package com.example.annabujak.weather4runners.Listeners;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;

/**
 * Created by slowik on 07.05.2017.
 */

public interface DailyPropositionsChangedListener {

    void onDailyPropositionsChanged(ArrayList<WeatherInfo> propositions);

}
