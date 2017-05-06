package com.example.annabujak.weather4runners.JSONDataUnitTests.ConverstionsHelper;

import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.ConversionsHelper;
import com.facebook.internal.CollectionMapper;
import com.facebook.internal.CollectionMapper.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by slowik on 06.05.2017.
 */

@RunWith(Parameterized.class)
public class TemperatureConversionTests {

    private static final int APPROX_ABSOLUTE_ZERO_CELSIUS = -273;

    private int kelvinTemp, expectedCelsiusTemp;

    public TemperatureConversionTests(int kelvinTemp, int expectedCelsiusTemp) {
        this.kelvinTemp = kelvinTemp;
        this.expectedCelsiusTemp = expectedCelsiusTemp;
    }

    @Parameterized.Parameters
    public static List getTestArguments() {
        return Arrays.asList(new Integer[][] {
                {300, 300 + APPROX_ABSOLUTE_ZERO_CELSIUS},
                {200, 200 + APPROX_ABSOLUTE_ZERO_CELSIUS},
                {100, 100 + APPROX_ABSOLUTE_ZERO_CELSIUS},
                {123, 123 + APPROX_ABSOLUTE_ZERO_CELSIUS},
                {433, 433 + APPROX_ABSOLUTE_ZERO_CELSIUS}
        });
    }

    @Test
    public void convertTemperatureKelvinToCelsius() {
        Assert.assertEquals(
                this.expectedCelsiusTemp,
                ConversionsHelper.convertTemperatureToCelsius(this.kelvinTemp));
    }
}
