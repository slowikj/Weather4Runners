package com.example.annabujak.weather4runners.Weather;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.Approximators.WeatherInfosApproximator;
import com.example.annabujak.weather4runners.Weather.Approximators.WeatherInfosApproximatorFactory;
import com.example.annabujak.weather4runners.Weather.JSONParsers.JSONWeatherParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by slowik on 05.05.2017.
 */

public class JSONTransformator {

    private JSONWeatherParser jsonWeatherParser;

    private WeatherInfosApproximatorFactory weatherInfosApproximatorFactory;

    private int hoursPerForecast;

    public JSONTransformator(JSONWeatherParser jsonWeatherParser,
                             WeatherInfosApproximatorFactory weatherInfosApproximatorFactory,
                             int hoursPerForecast) {
        this.jsonWeatherParser = jsonWeatherParser;
        this.weatherInfosApproximatorFactory = weatherInfosApproximatorFactory;
        this.hoursPerForecast = hoursPerForecast;
    }

    public ArrayList<WeatherInfo> getHourlyWeatherInfos(JSONArray jsonArray) throws JSONException {
        ArrayList<WeatherInfo> parsedJson = jsonWeatherParser.getParsed(jsonArray);
        ArrayList<WeatherInfo> res = new ArrayList<>();
        for(int i = 0; i < parsedJson.size() - 1; ++i) {
            res.addAll(getApproximator(
                    parsedJson.get(i),
                    parsedJson.get(i + 1),
                    this.hoursPerForecast + 1).getApproximated());
        }

        res.add(parsedJson.get(parsedJson.size() - 1));

        return res;
    }

    private WeatherInfosApproximator getApproximator(
            WeatherInfo begin, WeatherInfo end, int itemsCnt) {
        return this.weatherInfosApproximatorFactory.create(
                begin, end, itemsCnt);
    }
}
