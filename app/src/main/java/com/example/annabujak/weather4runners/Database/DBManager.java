package com.example.annabujak.weather4runners.Database;

import android.content.Context;

import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pawel.bujak on 28.04.2017.
 */

public class DBManager {
    private DBWeather4Runners database;

    public DBManager(Context context){
        database = new DBWeather4Runners(context);
    }
    public void ClearAndClose(){
        database.clearBestHours();
        database.clearUser();
        database.clearChosenHours();
        database.clearPreferences();
        database.close();
    }
    public void UpdateWeatherData(List<WeatherInfo> newWeather){
        database.clearWeatherInfo();
        for (WeatherInfo w: newWeather) {
            database.addWeatherInfo(w);
        }
    }
    public ArrayList<WeatherInfo> GetWeatherData(){
        return database.getAllWeatherInfos();
    }

    public void UpdatePreferences(Preference preference) {
        database.clearPreferences();
        database.addPreference(preference);
    }

    public Preference GetPreference(){return database.getPreference(1);}

    public void UpdateUserDatas(User user){
        database.clearUser();
        database.addUser(user);
    }
    public User GetUser(){return database.getUser(1);}
}
