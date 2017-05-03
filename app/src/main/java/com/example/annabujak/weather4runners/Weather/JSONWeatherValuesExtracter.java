package com.example.annabujak.weather4runners.Weather;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

/**
 * Created by slowik on 03.05.2017.
 */

public class JSONWeatherValuesExtracter {

    private JSONObject weatherJSONItem;

    public JSONWeatherValuesExtracter(JSONObject weatherJSONItem) {
        this.weatherJSONItem = weatherJSONItem;
    }

    public int extractTemperature() throws JSONException {

        String strTemp = extractWeatherString(
                new String[]{"main", "temp"}
        );

        return convertToCelsius(Integer.parseInt(strTemp));
    }

    public int extractHumidity() throws JSONException {

        String strHumidity = extractWeatherString(
                new String[]{"main", "humidity"}
        );

        return Integer.parseInt(strHumidity);
    }

    public Cloudiness extractCloudiness() throws JSONException {

        String strCloudiness = extractWeatherString(
                new String[] {"clouds", "all"}
        );

        if(strCloudiness == null) {
            return null;
        }

        int cloudinessPercentage = Integer.parseInt(strCloudiness);

        return Cloudiness.fromId(
                Cloudiness.values().length - 1 - (cloudinessPercentage / (100 / Cloudiness.values().length)));
    }

    public Date extractDate() throws JSONException {

        String strDate = extractWeatherString(
                new String[] {"dt"}
        );

        return new Date(Integer.parseInt(strDate));
    }

    public double extractWind() throws JSONException {

        String strWind = extractWeatherString(
                new String[] {"wind"}
        );

        return Double.parseDouble(strWind);
    }

    public double extractRain() {

        final double NO_RAIN = 0.0;

        try {
            return Double.parseDouble(extractWeatherString(
                    new String[] {"rain", "3h"}));
        } catch(JSONException e) {
            return NO_RAIN;
        }
    }

    public double extractSnow() {

        final double NO_SNOW = 0.0;

        try {
            return Double.parseDouble(extractWeatherString(
                    new String[] {"snow", "3h"}));
        } catch(JSONException e) {
            return NO_SNOW;
        }
    }

    private String extractWeatherString(String[] namesPath) throws JSONException {

        JSONObject currentJSONObject = weatherJSONItem;
        for (int i = 0; i < (namesPath.length - 1); ++i) {
            currentJSONObject = currentJSONObject.getJSONObject(namesPath[i]);
        }

        return currentJSONObject.getString(namesPath[namesPath.length - 1]);
    }

    private int convertToCelsius(int kelvinTemperature) {
        return kelvinTemperature - 273;
    }
}
