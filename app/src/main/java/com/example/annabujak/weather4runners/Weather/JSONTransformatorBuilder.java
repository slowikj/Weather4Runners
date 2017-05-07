package com.example.annabujak.weather4runners.Weather;

import com.example.annabujak.weather4runners.Weather.Approximators.WeatherInfosApproximatorFactory;
import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONWeatherValuesExtractorFactory;
import com.example.annabujak.weather4runners.Weather.JSONParsers.JSONWeatherParser;

/**
 * Created by slowik on 06.05.2017.
 */

public class JSONTransformatorBuilder {

    private JSONWeatherValuesExtractorFactory extractorFactory;

    private WeatherInfosApproximatorFactory approximatorFactory;

    private int hoursPerForecast;

    public JSONTransformatorBuilder() {}

    public JSONTransformatorBuilder setValuesExtractorFactory(JSONWeatherValuesExtractorFactory extractorFactory) {
        this.extractorFactory = extractorFactory;
        return this;
    }

    public JSONTransformatorBuilder setApproximatorFactory(WeatherInfosApproximatorFactory approximatorFactory) {
        this.approximatorFactory = approximatorFactory;
        return this;
    }


    public JSONTransformatorBuilder setHoursPerForecast(int hoursPerForecast) {
        this.hoursPerForecast = hoursPerForecast;
        return this;
    }

    public JSONTransformator build() {
        return new JSONTransformator(
                new JSONWeatherParser(this.extractorFactory),
                this.approximatorFactory,
                this.hoursPerForecast
        );
    }
}
