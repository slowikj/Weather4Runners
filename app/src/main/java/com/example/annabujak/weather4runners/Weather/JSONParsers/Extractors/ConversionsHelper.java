package com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors;

/**
 * Created by slowik on 06.05.2017.
 */

public class ConversionsHelper {

    public static int convertTemperatureToCelsius(int kelvinTemperature) {
        return kelvinTemperature - 273;
    }

    public static double convertSpeedToKpH(double mPerSec) {
        return mPerSec * 3.6;
    }
}
