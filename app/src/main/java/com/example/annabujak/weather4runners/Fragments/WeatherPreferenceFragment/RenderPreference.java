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

    public static int getIndexFromTemperature(int temperature) {
        int result = temperature + 25;
        return (int)(result * 100 / 65.0);
    }

    public static int getHumidity(int i){
        return i;
    }

    public static int getIndexFromHumidity(int humidity) {
        return humidity;
    }

    public static int getWindSpeed(int i){
        return i;
    }

    public static int getIndexFromWindSpeed(int windSpeed) {
        return windSpeed;
    }

    public static int getPrecipitation(int i){
        int result = (int)(((double)i / 100.0) * 500.0);
        return result;
    }

    public static int getIndexFromPrecipitation(int precipitation) {
        return (int)(precipitation * 100.0 / 500.0);
    }
    public static int getCloudiness(int i){
        int result = (int)(((double)i / 100.0) * 3.0);
        return result;
    }

    public static int getIndexFromCloudiness(int cloudiness) {
        return (int)(cloudiness * 100 / 3.0);
    }

    public static int getStartHour(int i){
        int result = (int)(((double)i / 100.0) * 24.0);
        return result;
    }

    public static int getIndexFromStartHour(int startHour) {
        return (int)(startHour * 100 / 24.0);
    }

    public static int getEndHours(int i){
        int result = (int)(((double)i / 100.0) * (24.0));
        return result;
    }

    public static int getIndexFromEndHour(int endHour) {
        return (int)(endHour * 100 / 24.0);
    }
}
