package com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by slowik on 05.05.2017.
 */

public class JSONOpenWeatherMapValuesExtractor extends JSONWeatherValuesExtractor {

    public JSONOpenWeatherMapValuesExtractor(JSONObject weatherJSONItem) {
        super(weatherJSONItem);
    }

    @Override
    public int extractTemperature() throws JSONException {
        String strTemp = extractWeatherString(
                this.weatherJSONItem,
                new String[]{"main", "temp"}
        );

        return ConversionsHelper.convertTemperatureToCelsius((int)Double.parseDouble(strTemp));
    }

    @Override
    public int extractHumidity() throws JSONException {

        String strHumidity = extractWeatherString(
                this.weatherJSONItem,
                new String[]{"main", "humidity"}
        );

        return Integer.parseInt(strHumidity);
    }

    @Override
    public Cloudiness extractCloudiness() throws JSONException {
        String strCloudiness = extractWeatherString(
                this.weatherJSONItem,
                new String[] {"clouds", "all"}
        );

        int cloudinessPercentage = Integer.parseInt(strCloudiness);

        return Cloudiness.fromId(
                Cloudiness.values().length - 1 - (cloudinessPercentage / (100 / Cloudiness.values().length)));
    }

    @Override
    public long extractDate() throws JSONException {
        String strDate = extractWeatherString(
                this.weatherJSONItem,
                new String[] {"dt"}
        );

        return TimeUnit.SECONDS.toMillis(Long.parseLong(strDate));
    }

    @Override
    public double extractWind() throws JSONException {
        String strWind = extractWeatherString(
                this.weatherJSONItem,
                new String[] {"wind", "speed"}
        );

        return ConversionsHelper.convertSpeedToKpH(Double.parseDouble(strWind));
    }

    @Override
    public String extractIconName() throws JSONException {
        return extractWeatherString(
                this.weatherJSONItem.getJSONArray("weather").getJSONObject(0),
                new String[] { "icon" }
        );
    }

    @Override
    public String extractDescription() throws JSONException {
        return extractWeatherString(
                this.weatherJSONItem.getJSONArray("weather").getJSONObject(0),
                new String[] { "description" }
        );
    }

    @Override
    public double extractPrecipitation() {
        return extractRain() + extractSnow();
    }

    @Override
    public double extractRain() {
        final double NO_RAIN = 0.0;

        try {
            return Double.parseDouble(extractWeatherString(
                    this.weatherJSONItem,
                    new String[] {"rain", "3h"}));
        } catch(JSONException|NumberFormatException e) {
            return NO_RAIN;
        }
    }

    @Override
    public double extractSnow() {
        final double NO_SNOW = 0.0;

        try {
            return Double.parseDouble(extractWeatherString(
                    this.weatherJSONItem,
                    new String[] {"snow", "3h"}));
        } catch(JSONException|NumberFormatException e) {
            return NO_SNOW;
        }
    }
}
