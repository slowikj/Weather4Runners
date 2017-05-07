package com.example.annabujak.weather4runners.CentralControl;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;

/**
 * Created by slowik on 07.05.2017.
 */

public interface WeeklyPropositionsChangedListener {

    void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions);
}
