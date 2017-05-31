package com.example.annabujak.weather4runners.CentralControl;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.Listeners.AddChosenHourListener;
import com.example.annabujak.weather4runners.Listeners.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Listeners.UpdatingFinishedListener;
import com.example.annabujak.weather4runners.Listeners.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Notifications.NotificationManager;
import com.example.annabujak.weather4runners.Objects.ChosenProposition;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.PreferenceBalance;
import com.example.annabujak.weather4runners.Objects.PropositionsList;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.Approximators.WeatherInfosLinearApproximatorFactory;
import com.example.annabujak.weather4runners.Weather.Filter.WeatherFilter;
import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONOpenWeatherMapValuesExtractorFactory;
import com.example.annabujak.weather4runners.Weather.JSONTransformator;
import com.example.annabujak.weather4runners.Weather.JSONTransformatorBuilder;
import com.example.annabujak.weather4runners.Weather.JSONWeatherDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slowik on 07.05.2017.
 */

public class CentralControl {

    private static final String DEFAULT_CITY_NAME = "Warsaw,Poland";

    private static final String DEFAULT_LANGUAGE = "en";

    private static final int HOURS_PER_FORECAST = 3;

    private static final int BEST_WEATHER_PROPOSITIONS = Integer.MAX_VALUE;

    private DBManager databaseManager;

    private WeatherForecastManager weatherForecastManager;

    private WeatherFilter weatherFilter;

    private DailyPropositionsChangedListener dailyPropositionsChangedListener;

    private WeeklyPropositionsChangedListener weeklyPropositionsChangedListener;

    private UpdatingFinishedListener updatingFinishedListener;

    private AddChosenHourListener addChosenHourListener;

    private NotificationManager notificationManager;

    public CentralControl(Context context) {
        this.databaseManager = new DBManager(context);
        this.weatherForecastManager = new WeatherForecastManager(
                getDefaultJSONDownloader(DEFAULT_CITY_NAME, DEFAULT_LANGUAGE),
                getDefaultJSONTransformator());

        this.weatherFilter = new WeatherFilter(BEST_WEATHER_PROPOSITIONS,
                getPreferenceBalanceOrDefault());
        notificationManager = new NotificationManager(context);
    }

    public void setDailyPropositionsChangedListener(DailyPropositionsChangedListener listener) {
        this.dailyPropositionsChangedListener = listener;
    }
    public void setAddChosenHourListener(AddChosenHourListener listener){
        this.addChosenHourListener = listener;
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

    public void updateWeatherForecastAsync() {
        (new WeatherForecastUpdaterTask()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void updatePropositionsAsync() {
        recomputePropositionsAsync(this.databaseManager.GetWeatherData());
    }

    public void updateUser(User user){
        databaseManager.UpdateUserDatas(user);
    }
    public void addChosenHour(ChosenProposition hour){
        if(databaseManager.AddChosenHourAndUpdateIsChecked(hour))
        updatePropositionsAsync();
    }
    public List<ChosenProposition> getAllChosenHours(){
        return databaseManager.GetChosenHours();
    }
    public void updatePreference(Preference preference){databaseManager.UpdatePreferences(preference);}
    public void updatePreferenceBalance(PreferenceBalance balance){
        databaseManager.UpdatePreferenceBalance(balance);
        this.weatherFilter = new WeatherFilter(BEST_WEATHER_PROPOSITIONS, balance);
    }

    private void recomputePropositionsAsync(ArrayList<WeatherInfo> weatherForecast) {
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

    public PreferenceBalance getPreferenceBalanceOrDefault() {
        PreferenceBalance res;
        try {
            res = databaseManager.GetPreference().getPreferenceBalance();
        } catch(Exception e) {
            res = new PreferenceBalance();
        }

        return res;
    }

    private Preference getUserWeatherPreferenceOrDefault() {
        try {
            return databaseManager.GetPreference();
        } catch(Exception e) {
            return new Preference();
        }
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

            recomputePropositionsAsync(weatherInfos);
        }
    }

    class PropositionsComputer
            extends AsyncTask<ArrayList<WeatherInfo>, Void, Pair<ArrayList<WeatherInfo>, ArrayList<WeatherInfo>>> {

        @Override
        protected Pair<ArrayList<WeatherInfo>, ArrayList<WeatherInfo>> doInBackground(ArrayList<WeatherInfo>... params) {
            ArrayList<WeatherInfo> weatherForecast = params[0];

            Preference preference = getUserWeatherPreferenceOrDefault();

            ArrayList<WeatherInfo> dailyPropositions = weatherFilter
                    .GetDailyWeather(weatherForecast, preference);

            ArrayList<WeatherInfo> weeklyPropositions = weatherFilter
                    .GetWeeklyWeather(weatherForecast, preference);

            return new Pair<ArrayList<WeatherInfo>, ArrayList<WeatherInfo>>(dailyPropositions, weeklyPropositions);
        }

        @Override
        protected void onPostExecute(Pair<ArrayList<WeatherInfo>, ArrayList<WeatherInfo>> propositionsPair) {
            super.onPostExecute(propositionsPair);

            dailyPropositionsChangedListener.onDailyPropositionsChanged(
                    new PropositionsList(propositionsPair.first));
            weeklyPropositionsChangedListener.onWeeklyPropositionsChanged(
                    new PropositionsList(propositionsPair.second));

            updatingFinishedListener.onUpdatingFinished();
        }
    }
}
