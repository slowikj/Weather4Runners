package com.example.annabujak.weather4runners.JSONDataUnitTests.ConverstionsHelper;

import com.example.annabujak.weather4runners.Weather.JSONParsers.Extractors.ConversionsHelper;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

/**
 * Created by slowik on 06.05.2017.
 */

@RunWith(Parameterized.class)
public class SpeedConversionTests {

    private double meteresPerSecSpeed, expectedKmPerHourSpeed;

    public SpeedConversionTests(double meteresPerSecSpeed, double expectedKmPerHourSpeed) {
        this.meteresPerSecSpeed = meteresPerSecSpeed;
        this.expectedKmPerHourSpeed = expectedKmPerHourSpeed;
    }

    @Parameterized.Parameters
    public static List getTestArguments() {
        return Arrays.asList(new Double[][] {
                { 1.0, 3.6},
                { 20.0, 72.0 },
                { 40.0, 144.0},
                { 25.5, 91.8}
        });
    }

    @Test
    public void convertMetersPerSecToKmPerHour() {
        Assert.assertEquals(
                this.expectedKmPerHourSpeed,
                ConversionsHelper.convertSpeedToKpH(this.meteresPerSecSpeed)
        );
    }

}
