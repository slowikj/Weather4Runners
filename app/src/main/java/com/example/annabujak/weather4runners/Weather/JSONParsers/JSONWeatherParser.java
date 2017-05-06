package com.example.annabujak.weather4runners.Weather.JSONParsers;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONWeatherValuesExtractor;
import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONWeatherValuesExtractorFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by slowik on 02.05.2017.
 */

public class JSONWeatherParser {

    private JSONWeatherValuesExtractorFactory jsonWeatherValuesExtractorFactory;

    public JSONWeatherParser(
            JSONWeatherValuesExtractorFactory jsonWeatherValuesExtractorFactory) {
        this.jsonWeatherValuesExtractorFactory = jsonWeatherValuesExtractorFactory;
    }

    public ArrayList<WeatherInfo> getParsed(JSONArray jsonArray) throws JSONException {
        ArrayList<WeatherInfo> res = new ArrayList<WeatherInfo>();

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONWeatherValuesExtractor jsonWeatherValuesExtractor = this.jsonWeatherValuesExtractorFactory.create(
                    jsonArray.getJSONObject(i)
            );

            res.add(new WeatherInfo(
                    jsonWeatherValuesExtractor.extractTemperature(),
                    jsonWeatherValuesExtractor.extractHumidity(),
                    jsonWeatherValuesExtractor.extractCloudiness(),
                    jsonWeatherValuesExtractor.extractPrecipitation(),
                    jsonWeatherValuesExtractor.extractDate(),
                    jsonWeatherValuesExtractor.extractWind(),
                    jsonWeatherValuesExtractor.extractIconName(),
                    jsonWeatherValuesExtractor.extractDescription()
            ));
        }

        return res;
    }
}
