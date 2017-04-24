package com.example.annabujak.weather4runners;

import com.example.annabujak.weather4runners.Weather.WeatherProposition;

import java.util.List;

/**
 * Created by slowik on 25.04.2017.
 */

public interface PropositionsChangedInterface {

    void OnPropositionsChanged(List<WeatherProposition> propositions);
}
