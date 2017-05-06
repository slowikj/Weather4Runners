package com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors;

import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONWeatherValuesExtractor;

import org.json.JSONObject;

/**
 * Created by slowik on 06.05.2017.
 */

public abstract class JSONWeatherValuesExtractorFactory {

    public abstract JSONWeatherValuesExtractor create(JSONObject jsonObject);
}
