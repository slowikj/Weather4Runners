package com.example.annabujak.weather4runners.CentralControl;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.JSONDownloaders.WeatherDownloadersManager;
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

    private static final String DEFAULT_CITY_NAME = "Warsaw";

    private static final String DEFAULT_COUNTRY_NAME = "Poland";

    private static final String DEFAULT_LANGUAGE = "en";

    private static double DEFAULT_LONGITUDE = 21.0042;

    private static double DEFAULT_LATITUDE = 52.1347;

    private WeatherDownloadersManager weatherDownloadersManager;

    private JSONTransformator jsonTransformator;

    public WeatherForecastManager(JSONTransformator jsonTransformator) {
        this.weatherDownloadersManager = new WeatherDownloadersManager(
                DEFAULT_CITY_NAME,
                DEFAULT_COUNTRY_NAME,
                DEFAULT_LONGITUDE,
                DEFAULT_LATITUDE,
                DEFAULT_LANGUAGE
        );

        this.jsonTransformator = jsonTransformator;
    }

    public ArrayList<WeatherInfo> getNewestWeatherForecast() throws IOException {
        JSONArray jsonWeatherForecasts = getDownloadedWeatherForecasts();
        ArrayList<WeatherInfo> hourlyForecasts = getTransformatedForecast(jsonWeatherForecasts);
        return hourlyForecasts;
    }

    public void setByNameWeatherDownloader() {
        this.weatherDownloadersManager.setDownloader(
                WeatherDownloadersManager.BY_NAME_DOWNLOADER
        );
    }

    public void setByCoordinatesWeatherDownloader() {
        this.weatherDownloadersManager.setDownloader(
                WeatherDownloadersManager.BY_COORDINATES_DOWNLOADER
        );
    }

    public void setLocation(String city, String country) {
        this.weatherDownloadersManager.setLocation(
                city, country
        );
    }

    public void setLocation(double longitude,
                            double latitude) {
        this.weatherDownloadersManager.setLocation(
                longitude, latitude
        );
    }

    public void setJsonTransformator(JSONTransformator jsonTransformator) {
        this.jsonTransformator = jsonTransformator;
    }

    private JSONArray getDownloadedWeatherForecasts() throws IOException {
        try {
            return weatherDownloadersManager.getCurrentWeatherDownloader().getData();
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
