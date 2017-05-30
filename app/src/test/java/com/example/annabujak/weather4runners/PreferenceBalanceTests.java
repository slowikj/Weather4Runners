package com.example.annabujak.weather4runners;

import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.PreferenceBalance;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by slowik on 30.05.2017.
 */

public class PreferenceBalanceTests {

    private static final double EPS = 0.00001;

    @Test
    public void convertFromImportantValues() {
        ArrayList<WeatherConditionsNames> importantConditions = new ArrayList<>();
        importantConditions.add(WeatherConditionsNames.Cloudiness);
        importantConditions.add(WeatherConditionsNames.Humidity);

        PreferenceBalance resultPreferenceBalance = new PreferenceBalance(importantConditions);
        PreferenceBalance expectedPreferenceBalance = new PreferenceBalance(
            0, 2.0 / 3.0, 1.0 / 3.0, 0, 0);

        Assert.assertEquals(expectedPreferenceBalance, resultPreferenceBalance);
    }
}
