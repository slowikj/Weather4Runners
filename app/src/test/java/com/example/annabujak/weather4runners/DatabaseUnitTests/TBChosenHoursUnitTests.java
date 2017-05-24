package com.example.annabujak.weather4runners.DatabaseUnitTests;


import com.example.annabujak.weather4runners.Objects.ChosenHour;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class TBChosenHoursUnitTests {
    ChosenHour chosenHour = new ChosenHour(new Date(), false);
    @Test
    public void setAndGetChosenHourUnitTest() throws Exception{
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
        chosenHour.setDayAnIsdHour(dateParser.parse("15/09/2016"),false);
        assertEquals(dateParser.parse("15/09/2016"),chosenHour.getDate());
        assertEquals(false,chosenHour.getIsHour());
    }
    @Test
    public void setIdTest() throws Exception{
        chosenHour.setId(1);
        assertEquals(1,chosenHour.getId());
    }
}
