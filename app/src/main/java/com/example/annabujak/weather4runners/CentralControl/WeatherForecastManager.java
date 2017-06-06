package com.example.annabujak.weather4runners.CentralControl;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.JSONTransformator;
import com.example.annabujak.weather4runners.Weather.JSONDownloaders.JSONWeatherDownloader;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
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

    public ArrayList<WeatherInfo> getNewestWeatherForecast() throws IOException {
        JSONArray jsonWeatherForecasts = getDownloadedWeatherForecasts();
        ArrayList<WeatherInfo> hourlyForecasts = getTransformatedForecast(jsonWeatherForecasts);
        return hourlyForecasts;
    }

    public void setWeatherDownloader(JSONWeatherDownloader jsonWeatherDownloader) {
        this.jsonWeatherDownloader = jsonWeatherDownloader;
    }

    public void setJsonTransformator(JSONTransformator jsonTransformator) {
        this.jsonTransformator = jsonTransformator;
    }

    private JSONArray getDownloadedWeatherForecasts() throws IOException {
        try {
            return jsonWeatherDownloader.getData();
        } catch (MalformedURLException | JSONException e) {
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
