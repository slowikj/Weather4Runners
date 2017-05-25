package com.example.annabujak.weather4runners.DatabaseUnitTests;


import com.example.annabujak.weather4runners.Objects.ChosenProposition;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class TBChosenHoursUnitTests {
    ChosenProposition chosenProposition = new ChosenProposition(new Date().getSeconds(), false);
    @Test
    public void setAndGetChosenHourUnitTest() throws Exception{
        SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
        chosenProposition.setDayAnIsdHour(dateParser.parse("15/09/2016").getSeconds(),false);
        assertEquals(dateParser.parse("15/09/2016").getSeconds(), chosenProposition.getDate());
        assertEquals(false, chosenProposition.getIsHour());
    }
    @Test
    public void setIdTest() throws Exception{
        chosenProposition.setId(1);
        assertEquals(1, chosenProposition.getId());
    }
}
