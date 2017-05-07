package com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by slowik on 03.05.2017.
 */

public abstract class JSONWeatherValuesExtractor {

    protected JSONObject weatherJSONItem;

    public JSONWeatherValuesExtractor(JSONObject weatherJSONItem) {
        this.weatherJSONItem = weatherJSONItem;
    }

    public abstract int extractTemperature() throws JSONException;

    public abstract int extractHumidity() throws JSONException;

    public abstract Cloudiness extractCloudiness() throws JSONException;

    public abstract Date extractDate() throws JSONException;

    public abstract double extractWind() throws JSONException;

    public abstract String extractIconName() throws JSONException;

    public abstract String extractDescription() throws JSONException;

    public abstract double extractPrecipitation();

    public abstract double extractRain();

    public abstract double extractSnow();

    protected static String extractWeatherString(
            JSONObject currentJSONObject,
            String[] namesPath) throws JSONException {
        for (int i = 0; i < (namesPath.length - 1); ++i) {
            currentJSONObject = currentJSONObject.getJSONObject(namesPath[i]);
        }

        return currentJSONObject.getString(namesPath[namesPath.length - 1]);
    }
}
