package com.example.annabujak.weather4runners.Objects;

/**
 * Created by pawel.bujak on 14.05.2017.
 */

public class PreferenceBalance {

    private double TemperatureImportance;
    private double CloudinessImportance;
    private double HumidityImportance;
    private double PrecipitationImportance;
    private double WindSpeedImportance;

    public PreferenceBalance(double _temperatureImportance, double _cloudinessImportance, double _humidityImportance,
                             double _precipitationImportance, double _windSpeedImportance)
    {
        TemperatureImportance = _temperatureImportance;
        CloudinessImportance = _cloudinessImportance;
        HumidityImportance = _humidityImportance;
        PrecipitationImportance = _precipitationImportance;
        WindSpeedImportance = _windSpeedImportance;
    }

    public double GetTemperatureImportance(){return TemperatureImportance;}
    public double GetCloudinessImportance(){return CloudinessImportance;}
    public double GetHumidityImportance(){return HumidityImportance;}
    public double GetPrecipitationImportance(){return PrecipitationImportance;}
    public double GetWindSpeedImportance(){return WindSpeedImportance;}

}
