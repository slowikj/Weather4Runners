package com.example.annabujak.weather4runners.Weather;

import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slowik on 02.05.2017.
 */

public class JSONWeatherParser {

    public JSONWeatherParser() {
    }

    public List<WeatherInfo> getHourlyForecasts(JSONArray jsonArray) throws JSONException {

        List<WeatherInfo> for3hours = getParsed(jsonArray);


    }

    private List<WeatherInfo> getParsed(JSONArray jsonArray) throws JSONException {

        ArrayList<WeatherInfo> res = new ArrayList<WeatherInfo>();

        for (int i = 0; i < jsonArray.length(); ++i) {
            JSONWeatherValuesExtracter jsonWeatherValuesExtracter = new JSONWeatherValuesExtracter(
                    jsonArray.getJSONObject(i)
            );

            res.add(new WeatherInfo(
                    jsonWeatherValuesExtracter.extractTemperature(),
                    jsonWeatherValuesExtracter.extractHumidity(),
                    jsonWeatherValuesExtracter.extractCloudiness(),
                    jsonWeatherValuesExtracter.extractRain(),
                    jsonWeatherValuesExtracter.extractDate()
            ));
        }

        return res;
    }


}
