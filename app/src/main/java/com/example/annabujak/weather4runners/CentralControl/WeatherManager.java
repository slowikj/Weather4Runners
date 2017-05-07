package com.example.annabujak.weather4runners.CentralControl;

import android.content.Context;
import android.os.AsyncTask;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.PropositionsUpdatedListener;
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

public class WeatherManager {

    private static final String DEFAULT_LANGUAGE = "en";

    private static final int HOURS_PER_FORECAST = 3;

    private WeatherForecastChangedListener weatherForecastChangedListener;

    private DBManager DatabaseManager;

    private JSONTransformator jsonTransformator;

    public WeatherManager(Context context) {
        DatabaseManager = new DBManager(context);
        this.jsonTransformator = getJSONTransformator();
    }

    public void UpdateWeather(String cityName) {
        (new JSONWeatherDownloaderTask()).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,
                cityName);
    }

    public void setWeatherForecastChangedListener(WeatherForecastChangedListener listener) {
        this.weatherForecastChangedListener = listener;
    }

    private JSONTransformator getJSONTransformator() {
        return (new JSONTransformatorBuilder())
                .setApproximatorFactory(new WeatherInfosLinearApproximatorFactory())
                .setValuesExtractorFactory(new JSONOpenWeatherMapValuesExtractorFactory())
                .setHoursPerForecast(HOURS_PER_FORECAST)
                .build();
    }

    class JSONWeatherDownloaderTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONWeatherDownloader downloader = new JSONWeatherDownloader(params[0], DEFAULT_LANGUAGE);
            try {
                return downloader.getData();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);
            (new JSONTransformatorTask()).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR,
                    jsonArray
            );
        }
    }

    class JSONTransformatorTask extends AsyncTask<JSONArray, Void, ArrayList<WeatherInfo>> {

        @Override
        protected ArrayList<WeatherInfo> doInBackground(JSONArray... params) {
            JSONArray jsonWeatherForecasts = params[0];
            try {
                ArrayList<WeatherInfo> hourlyForecasts = jsonTransformator
                        .getHourlyWeatherInfos(jsonWeatherForecasts);

                return hourlyForecasts;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherInfo> weatherInfos) {
            super.onPostExecute(weatherInfos);
            weatherForecastChangedListener.onWeatherForecastChanged(weatherInfos);
            (new DataBaseUpdaterTask()).executeOnExecutor(
                    AsyncTask.THREAD_POOL_EXECUTOR,
                    weatherInfos);
        }
    }

    class DataBaseUpdaterTask extends AsyncTask<ArrayList<WeatherInfo>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<WeatherInfo>... params) {
            ArrayList<WeatherInfo> hourlyForecasts = params[0];
            DatabaseManager.UpdateWeatherData(hourlyForecasts);

            return null;
        }
    }
}
