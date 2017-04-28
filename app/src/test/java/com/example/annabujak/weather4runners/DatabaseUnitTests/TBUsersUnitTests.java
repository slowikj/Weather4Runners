package com.example.annabujak.weather4runners.DatabaseUnitTests;

import com.example.annabujak.weather4runners.Objects.User;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class TBUsersUnitTests {
    User user = new User();
    @Test
    public void setUserNameAndSurnameTest(){
        user.setNameAndSurname("Ania","Bujak");
        String name = user.getName();
        String surname = user.getSurname();
        assertEquals("Ania",name);
        assertEquals("Bujak",surname);
    }
    @Test
    public void setIdTest() throws Exception{
        user.setId(1);
        assertEquals(1,user.getId());
    }
}
