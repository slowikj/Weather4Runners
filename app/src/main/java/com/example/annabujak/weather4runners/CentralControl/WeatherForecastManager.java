package com.example.annabujak.weather4runners.CentralControl;

import android.content.Context;
import android.os.AsyncTask;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.Approximators.WeatherInfosLinearApproximatorFactory;
import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONOpenWeatherMapValuesExtractorFactory;
import com.example.annabujak.weather4runners.Weather.JSONTransformator;
import com.example.annabujak.weather4runners.Weather.JSONTransformatorBuilder;
import com.example.annabujak.weather4runners.Weather.JSONWeatherDownloader;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherForecastManager {

    private JSONWeatherDownloader jsonWeatherDownloader;

    private JSONTransformator jsonTransformator;

    public WeatherForecastManager(JSONWeatherDownloader jsonWeatherDownloader,
                                  JSONTransformator jsonTransformator) {
        this.jsonWeatherDownloader = jsonWeatherDownloader;
        this.jsonTransformator = jsonTransformator;
    }

    public ArrayList<WeatherInfo> getNewestWeatherForecast() {
        JSONArray jsonWeatherForecasts = getDownloadedWeatherForecasts();
        ArrayList<WeatherInfo> hourlyForecasts = getTransformatedForecast(jsonWeatherForecasts);
        return hourlyForecasts;
    }

    public void setLocation(String cityName) {
        this.jsonWeatherDownloader.setLocation(cityName);
    }

    private JSONArray getDownloadedWeatherForecasts() {
        try {
            return jsonWeatherDownloader.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private ArrayList<WeatherInfo> getTransformatedForecast(JSONArray jsonWeatherForecast) {
        try {
            ArrayList<WeatherInfo> hourlyForecasts = jsonTransformator
                    .getHourlyWeatherInfos(jsonWeatherForecast);

            return hourlyForecasts;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<WeatherInfo>();
        }
    }
}
