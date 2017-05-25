package com.example.annabujak.weather4runners.Database;

import android.content.Context;
import android.webkit.ConsoleMessage;

import com.example.annabujak.weather4runners.Objects.ChosenProposition;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.PreferenceBalance;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.io.Console;
import java.util.ArrayList;
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
    public void UpdatePreferenceBalance(PreferenceBalance balance){
        try{
            database.getPreference(1);
        }
        catch (Exception e){
            database.addPreference(new Preference());
        }
        Preference preference = database.getPreference(1);
        database.clearPreferences();
        preference.setPreferenceBalance(balance);
        database.addPreference(preference);
    }
    public Preference GetPreference(){
        try{
            database.getPreference(1);
        }
        catch (Exception e){
            database.addPreference(new Preference());
        }
        return database.getPreference(1);
    }

    public void UpdateUserDatas(User user){
        database.clearUser();
        database.addUser(user);
    }
    public User GetUser(){return database.getUser(1);}
    public void AddChosenHourAndUpdateIsChecked(ChosenProposition hour){
        List<ChosenProposition> chosenPropositions = database.getAllChosenHours();
        for (ChosenProposition fromBase: chosenPropositions) {
            if(fromBase.getDate() == hour.getDate()){
                fromBase.setDayAnIsdHour(fromBase.getDate(), !fromBase.getIsHour());
                database.deleteChosenHour(fromBase.getId());
                updateIsChecked(hour);
                return;
            }
        }
        updateIsChecked(hour);
        database.addChosenHour(hour);
    }
    private void updateIsChecked(ChosenProposition hour){
        List<WeatherInfo> weatherInfos = database.getAllWeatherInfos();
        for (WeatherInfo fromBase : weatherInfos) {
                if(fromBase.getDate() == hour.getDate()){
                    fromBase.setIsChecked(!fromBase.getIsChecked());
                    database.updateWeatherInfo(fromBase);
                    break;
                }
        }
    }
    public List<ChosenProposition> GetChosenHours(){return database.getAllChosenHours();}
}
