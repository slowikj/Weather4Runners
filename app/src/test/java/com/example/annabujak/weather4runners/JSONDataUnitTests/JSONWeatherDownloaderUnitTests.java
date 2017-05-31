package com.example.annabujak.weather4runners.JSONDataUnitTests;

import com.example.annabujak.weather4runners.Weather.JSONDownloaders.JSONWeatherDownloader;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by slowik on 02.05.2017.
 */

public class JSONWeatherDownloaderUnitTests {

    private JSONWeatherDownloader jsonWeatherDownloader;

    @Before
    public void init() {
        jsonWeatherDownloader = new JSONWeatherDownloader(
                "Warsaw,Poland",
                "en");
    }


    //TODO
    @Ignore
    @Test
    public void downloadAndValidate() {

        final int correctNumberOfItems = 40;

        JSONArray jsonArray = null;
        try {
            jsonArray = jsonWeatherDownloader.getData();
            Assert.assertTrue(jsonArray.length() == correctNumberOfItems);
        } catch (Exception e) {
            Assert.fail(e.toString());
        }
    }
}
