package com.example.annabujak.weather4runners.Database;


/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class User {

    int Id;
    String Name = "";
    String Surname = "";

    public User(){};
    public User(String _Name, String _Surname){
        Name = _Name;
        Surname = _Surname;
    }

    public void setNameAndSurname(String name, String surname){
        Name = name;
        Surname = surname;
    }
    public String getName(){
        return Name;
    }
    public String getSurname(){
        return Surname;
    }
    public void setId(int id){
        Id = id;
    }
    public int getId(){
        return Id;
    }
}
