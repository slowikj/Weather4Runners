package com.example.annabujak.weather4runners.Fragments.WeatherPreferenceFragment;

/**
 * Created by android on 2017-05-17.
 */

public class RenderPreference {
    public static int getTemperature(int i){
        int result = (int)(((double)i / 100.0) * 65.0);
        result -= 25;
        return result;
    }
    public static int getHumidity(int i){
        return i;
    }
    public static int getWindSpeed(int i){
        return i;
    }
    public static int getPrecipitation(int i){
        int result = (int)(((double)i / 100.0) * 500.0);
        return result;
    }
    public static int getCloudiness(int i){
        int result = (int)(((double)i / 100.0) * 4.0);
        return result;
    }
    public static int getStartHour(int i){
        int result = (int)(((double)i / 100.0) * 24.0);
        return result;
    }
    public static int getEndHours(int i){
        int result = (int)(((double)i / 100.0) * (24.0));
        return result;
    }
}
