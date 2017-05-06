package com.example.annabujak.weather4runners.Enum;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class EnumUnitTests {
    @Test
    public void ConvertToInTest() throws Exception{
        Cloudiness cloudiness = Cloudiness.Sunny;
        assertEquals(3,cloudiness.getValue());
    }

    @Test
    public void getBigCloudinessFromID() {
        Assert.assertEquals(Cloudiness.Big, Cloudiness.fromId(0));
    }

    @Test
    public void getMediumCloudinessFromID() {
        Assert.assertEquals(Cloudiness.Medium, Cloudiness.fromId(1));
    }
}
