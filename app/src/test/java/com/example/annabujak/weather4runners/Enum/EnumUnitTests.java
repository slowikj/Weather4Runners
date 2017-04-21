package com.example.annabujak.weather4runners.Enum;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class EnumUnitTests {
    @Test
    public void ConvertToInTest() throws Exception{
        Cloudiness cloudiness = Cloudiness.Sunny;
        assertEquals(3,cloudiness.getValue());
    }
}
