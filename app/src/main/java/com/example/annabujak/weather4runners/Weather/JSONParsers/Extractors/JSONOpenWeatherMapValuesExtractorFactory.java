package com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors;

import org.json.JSONObject;

/**
 * Created by slowik on 06.05.2017.
 */

public class JSONOpenWeatherMapValuesExtractorFactory extends JSONWeatherValuesExtractorFactory {

    @Override
    public JSONWeatherValuesExtractor create(JSONObject jsonObject) {
        return new JSONOpenWeatherMapValuesExtractor(jsonObject);
    }
}
