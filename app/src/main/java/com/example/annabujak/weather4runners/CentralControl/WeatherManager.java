package com.example.annabujak.weather4runners.CentralControl;

import android.content.Context;

import com.example.annabujak.weather4runners.Database.DBManager;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Weather.WeatherFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherManager {

    private DBManager DatabaseManager;

    public WeatherManager(Context context){
        DatabaseManager = new DBManager(context);
    }

    public void UpdateWeather(){
        //Pobiera dane z JSONa
        //Dane są wrzucane do JSON Transformator
        DatabaseManager.UpdateWeatherData(new ArrayList<WeatherInfo>());//Tutaj powinny wejść dane pobrane z JSON Transformator
        //Tutaj powinien lecieć CALL o zaktualizowaniu danych w bazie

    }
}
