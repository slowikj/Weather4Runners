package com.example.annabujak.weather4runners.DatabaseUnitTests;

import com.example.annabujak.weather4runners.Objects.BestHour;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class TBBestHoursUnitTests {
    BestHour bestHour = new BestHour(new Date(), 15);
    @Test
    public void setAndGetBestHourUnitTest() throws Exception{
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
        bestHour.setDayAndHour(dateParser.parse("15/09/2016"),16);
        assertEquals(dateParser.parse("15/09/2016"),bestHour.getDate());
        assertEquals(16,bestHour.getHour());
    }
    @Test
    public void setIdTest() throws Exception{
        bestHour.setId(1);
        assertEquals(1,bestHour.getId());
    }
}
