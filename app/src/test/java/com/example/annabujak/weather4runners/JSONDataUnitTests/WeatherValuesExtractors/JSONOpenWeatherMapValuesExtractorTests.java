package com.example.annabujak.weather4runners.JSONDataUnitTests.WeatherValuesExtractors;

import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.ConversionsHelper;
import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.JSONOpenWeatherMapValuesExtractor;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by slowik on 06.05.2017.
 */

public class JSONOpenWeatherMapValuesExtractorTests {

    private final String sample_json = "{\"dt\":1494007200,\"main\":{\"temp\":291.63,\"temp_min\":289.561,\"temp_max\":291.63,\"pressure\":1018.62,\"sea_level\":1030.72,\"grnd_level\":1018.62,\"humidity\":92,\"temp_kf\":2.07},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"lekki deszcz\",\"icon\":\"10d\"}],\"clouds\":{\"all\":80},\"wind\":{\"speed\":2.96,\"deg\":55.5014},\"rain\":{\"3h\":0.2725},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2017-05-05 18:00:00\"}";
    private JSONOpenWeatherMapValuesExtractor extractor;

    @Before
    public void setUp() throws JSONException {
        JSONObject tmp = new JSONObject(sample_json);
        this.extractor = new JSONOpenWeatherMapValuesExtractor(tmp);
    }


    // TODO
    @Ignore
    @Test
    public void extractTemperatureInCelsius() throws JSONException {
        int celsiusTemperature = extractor.extractTemperature();

        Assert.assertEquals(ConversionsHelper.convertTemperatureToCelsius(288),
                celsiusTemperature);
    }
}
