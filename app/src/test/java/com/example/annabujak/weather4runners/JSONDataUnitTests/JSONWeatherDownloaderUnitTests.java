package com.example.annabujak.weather4runners.JSONDataUnitTests;

import android.net.Uri;
import android.support.test.rule.ActivityTestRule;

import com.example.annabujak.weather4runners.Facebook.FacebookLoginActivity;
import com.example.annabujak.weather4runners.Weather.JSONWeatherDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

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
