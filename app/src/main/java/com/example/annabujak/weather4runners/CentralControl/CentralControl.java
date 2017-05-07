package com.example.annabujak.weather4runners.CentralControl;

import android.content.Context;
import android.os.AsyncTask;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.Approximators.WeatherInfosLinearApproximatorFactory;
import com.example.annabujak.weather4runners.Weather.Filter.WeatherFilter;
import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONOpenWeatherMapValuesExtractorFactory;
import com.example.annabujak.weather4runners.Weather.JSONTransformator;
import com.example.annabujak.weather4runners.Weather.JSONTransformatorBuilder;
import com.example.annabujak.weather4runners.Weather.JSONWeatherDownloader;

import java.util.ArrayList;

/**
 * Created by slowik on 07.05.2017.
 */

public class CentralControl {

    private static final String DEFAULT_CITY_NAME = "Warsaw,Poland";

    private static final String DEFAULT_LANGUAGE = "en";

    private static final int HOURS_PER_FORECAST = 3;

    private static final int BEST_WEATHER_PROPOSITIONS = 4;

    private DBManager databaseManager;

    private WeatherForecastManager weatherForecastManager;

    private WeatherFilter weatherFilter;

    private DailyPropositionsChangedListener dailyPropositionsChangedListener;

    private WeeklyPropositionsChangedListener weeklyPropositionsChangedListener;

    private UpdatingFinishedListener updatingFinishedListener;

    public CentralControl(Context context) {
        this.databaseManager = new DBManager(context);
        this.weatherForecastManager = new WeatherForecastManager(
                getDefaultJSONDownloader(DEFAULT_CITY_NAME, DEFAULT_LANGUAGE),
                getDefaultJSONTransformator());
        this.weatherFilter = new WeatherFilter(BEST_WEATHER_PROPOSITIONS);
    }

    public void setDailyPropositionsChangedListener(DailyPropositionsChangedListener listener) {
        this.dailyPropositionsChangedListener = listener;
    }

    public void setWeeklyPropositionsChangedListener(WeeklyPropositionsChangedListener listener) {
        this.weeklyPropositionsChangedListener = listener;
    }

    public void setUpdatingFinishedListener(UpdatingFinishedListener listener) {
        this.updatingFinishedListener = listener;
    }

    public void setCityName(String cityName) {
        this.weatherForecastManager.setLocation(cityName);
    }

    public void updateWeatherForecast() {
        (new WeatherForecastUpdaterTask()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void updatePropositions() {
        recomputePropositions(this.databaseManager.GetWeatherData());
    }

    private void recomputePropositions(ArrayList<WeatherInfo> weatherForecast) {
        (new PropositionsComputer()).executeOnExecutor(
                AsyncTask.THREAD_POOL_EXECUTOR,
                weatherForecast);
    }

    private JSONTransformator getDefaultJSONTransformator() {
        return (new JSONTransformatorBuilder())
                .setApproximatorFactory(new WeatherInfosLinearApproximatorFactory())
                .setValuesExtractorFactory(new JSONOpenWeatherMapValuesExtractorFactory())
                .setHoursPerForecast(HOURS_PER_FORECAST)
                .build();
    }

    private JSONWeatherDownloader getDefaultJSONDownloader(String cityName,
                                                           String language) {
        return new JSONWeatherDownloader(cityName, language);
    }

    class WeatherForecastUpdaterTask extends AsyncTask<Void, Void, ArrayList<WeatherInfo>> {

        @Override
        protected ArrayList<WeatherInfo> doInBackground(Void... params) {
            ArrayList<WeatherInfo> weatherForecast = weatherForecastManager
                    .getNewestWeatherForecast();

            databaseManager.UpdateWeatherData(weatherForecast);

            return weatherForecast;
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherInfo> weatherInfos) {
            super.onPostExecute(weatherInfos);

            recomputePropositions(weatherInfos);
        }
    }

    class PropositionsComputer extends AsyncTask<ArrayList<WeatherInfo>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<WeatherInfo>... params) {
            ArrayList<WeatherInfo> weatherForecast = params[0];

            Preference preference = new Preference();
            databaseManager.UpdatePreferences(preference);

            ArrayList<WeatherInfo> dailyPropositions = weatherFilter
                    .GetDailyWeather(weatherForecast, databaseManager.GetPreference());

            ArrayList<WeatherInfo> weeklyPropositions = weatherFilter
                    .GetWeeklyWeather(weatherForecast, preference);

            dailyPropositionsChangedListener.onDailyPropositionsChanged(dailyPropositions);
            weeklyPropositionsChangedListener.onWeeklyPropositionsChanged(weeklyPropositions);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            updatingFinishedListener.onUpdatingFinished();
        }
    }
}
