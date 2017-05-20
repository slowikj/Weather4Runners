package com.example.annabujak.weather4runners.Notifiers;

import com.example.annabujak.weather4runners.Listeners.WeeklyPropositionsChangedListener;

/**
 * Created by slowik on 20.05.2017.
 */

public interface WeeklyWeatherPropositionsNotifier {
    void subscribeForWeeklyWeatherPropositionsChanged(WeeklyPropositionsChangedListener listener);
    void unsubscribeForWeeklyWeatherPropositionsChanged(WeeklyPropositionsChangedListener listener);
}
