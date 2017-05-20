package com.example.annabujak.weather4runners;

import com.example.annabujak.weather4runners.CentralControl.WeeklyPropositionsChangedListener;

/**
 * Created by slowik on 20.05.2017.
 */

public interface WeeklyWeatherPropositionsNotifier {
    void subscribeForWeeklyWeatherPropositionsChanged(WeeklyPropositionsChangedListener listener);
    void unsubscribeForWeeklyWeatherPropositionsChanged(WeeklyPropositionsChangedListener listener);
}
