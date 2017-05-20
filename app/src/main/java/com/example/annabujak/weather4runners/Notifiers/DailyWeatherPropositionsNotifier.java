package com.example.annabujak.weather4runners.Notifiers;

import com.example.annabujak.weather4runners.Listeners.DailyPropositionsChangedListener;

/**
 * Created by slowik on 20.05.2017.
 */

public interface DailyWeatherPropositionsNotifier {
    void subscribeForDailyWeatherPropositionsChanged(DailyPropositionsChangedListener listener);
    void unsubscribeForDailyWeatherPropositionsChanged(DailyPropositionsChangedListener listener);
}
