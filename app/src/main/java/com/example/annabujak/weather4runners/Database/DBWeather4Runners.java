package com.example.annabujak.weather4runners.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.Objects.BestHour;
import com.example.annabujak.weather4runners.Objects.ChosenHour;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class DBWeather4Runners extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Weather for runners";

    private static final String TABLE_PREFERENCES_NAME = "preferences";
    private static final String TABLE_BEST_HOURS = "best_hours";
    private static final String TABLE_CHOSEN_HOURS = "chosen_hours";
    private static final String TABLE_USER = "user";
    private static final String TABLE_WEATHER_INFO = "weather_info";

    private static final String KEY_ID = "id";

    // PREFERENCES Table - column names (WEATHER_INFO also uses them)
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_CLOUDINESS = "cloudiness";
    private static final String KEY_START_HOUR = "start";
    private static final String KEY_END_HOUR = "end";
    private static final String KEY_HUMIDITY = "humidity";
    private static final String KEY_PRECIPITATION = "precipitation";
    private static final String KEY_IS_CHECKED = "checked";

    // BEST_HOURS, CHOSEN_HOURS Table - column names
    private static final String KEY_DATE = "date";
    private static final String KEY_HOUR = "hour";

    //USER Table - columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";


    private static final String CREATE_TABLE_PREFERENCES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PREFERENCES_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEMPERATURE
            + " INTEGER," + KEY_CLOUDINESS + " INTEGER,"+ KEY_START_HOUR + " INTEGER,"
            + KEY_END_HOUR + " INTEGER, "+ KEY_HUMIDITY + " INTEGER, "+KEY_PRECIPITATION + " DOUBLE PRECISION"+")";

    private static final String CREATE_TABLE_BEST_HOURS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_BEST_HOURS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
            + " INTEGER," + KEY_HOUR +" INTEGER"+ ")";
    private static final String CREATE_TABLE_CHOSEN_HOURS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CHOSEN_HOURS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
            + " TEXT," + KEY_HOUR +" INTEGER"+ ")";

    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_SURNAME +" TEXT"+ ")";

    private static final String CREATE_TABLE_WEATHER_INFO = "CREATE TABLE IF NOT EXISTS "
            + TABLE_WEATHER_INFO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEMPERATURE
            + " INTEGER," + KEY_HUMIDITY + " INTEGER,"+ KEY_CLOUDINESS + " INTEGER,"
            + KEY_PRECIPITATION + " DOUBLE PRECISION, "+ KEY_DATE
            + " TEXT, "+KEY_IS_CHECKED + " BOOLEAN"+")";

    public DBWeather4Runners(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PREFERENCES);
        db.execSQL(CREATE_TABLE_BEST_HOURS);
        db.execSQL(CREATE_TABLE_CHOSEN_HOURS);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_WEATHER_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_BEST_HOURS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOSEN_HOURS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER_INFO);

        onCreate(database);
    }

    public void addPreference(Preference preference) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPERATURE, preference.getTemperature());
        values.put(KEY_CLOUDINESS, preference.getCloudiness().getValue());
        values.put(KEY_START_HOUR, preference.getStartHour());
        values.put(KEY_END_HOUR, preference.getEndHour());
        values.put(KEY_HUMIDITY,preference.getHumidity());
        values.put(KEY_PRECIPITATION,preference.getPrecipitation());

        long id = database.insert(TABLE_PREFERENCES_NAME, null, values);
        preference.setId(id);
    }
    public Preference getPreference(long preferenceId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PREFERENCES_NAME + " WHERE "
                + KEY_ID + " = " + preferenceId;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        Preference preference = new Preference();
        preference.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        preference.setTemperature((cursor.getInt(cursor.getColumnIndex(KEY_TEMPERATURE))));
        preference.setCloudiness(Cloudiness.fromId(cursor.getInt(cursor.getColumnIndex(KEY_CLOUDINESS))));
        preference.setHours(cursor.getInt(cursor.getColumnIndex(KEY_START_HOUR)),cursor.getInt(cursor.getColumnIndex(KEY_END_HOUR)));
        preference.setHumidity(cursor.getInt(cursor.getColumnIndex(KEY_HUMIDITY)));
        preference.setPrecipitation(cursor.getDouble(cursor.getColumnIndex(KEY_PRECIPITATION)));

        return preference;
    }
    public void updatePreference(Preference preference){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPERATURE, preference.getTemperature());
        values.put(KEY_CLOUDINESS, preference.getCloudiness().getValue());
        values.put(KEY_START_HOUR, preference.getStartHour());
        values.put(KEY_END_HOUR, preference.getEndHour());
        values.put(KEY_HUMIDITY,preference.getHumidity());
        values.put(KEY_PRECIPITATION,preference.getPrecipitation());

        // updating row
        database.update(TABLE_PREFERENCES_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(preference.getId()) });
    }
    public void clearPreferences(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_PREFERENCES_NAME);
    }

    public void addBestHour(BestHour bestHour) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, bestHour.getDate().toString());
        values.put(KEY_HOUR, bestHour.getHour());

        long bestHourId =  database.insert(TABLE_BEST_HOURS, null, values);
        bestHour.setId(bestHourId);
    }
    public BestHour getBestHour(long bestHourId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BEST_HOURS + " WHERE "
                + KEY_ID + " = " + bestHourId;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();
        if(cursor.getCount() == 0)
            return null;
        BestHour bestHour = new BestHour(new Date(cursor.getString(cursor.getColumnIndex(KEY_DATE))),cursor.getInt(cursor.getColumnIndex(KEY_HOUR)));
        bestHour.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        return bestHour;
    }
    public List<BestHour> getAllBestHours() {
        List<BestHour> allHours = new ArrayList<BestHour>();
        String selectQuery = "SELECT  * FROM " + TABLE_BEST_HOURS;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                BestHour hour = new BestHour(new Date(c.getString(c.getColumnIndex(KEY_DATE))),c.getInt(c.getColumnIndex(KEY_HOUR)));
                hour.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                allHours.add(hour);
            } while (c.moveToNext());
        }

        return allHours;
    }
    public void updateBestHour(BestHour hour){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, hour.getDate().toString());
        values.put(KEY_HOUR, hour.getHour());

        // updating row
        database.update(TABLE_BEST_HOURS, values, KEY_ID + " = ?", new String[] { String.valueOf(hour.getId()) });
    }
    public void clearBestHours(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_BEST_HOURS);
    }
    public void deleteBestHour(long hourId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_BEST_HOURS, KEY_ID + " = ?",
                new String[] { String.valueOf(hourId) });
    }

    public void addChosenHour(ChosenHour chosenHour) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, chosenHour.getDate().toString());
        values.put(KEY_HOUR, chosenHour.getHour());

        long id =  database.insert(TABLE_CHOSEN_HOURS, null, values);
        chosenHour.setId(id);
    }
    public ChosenHour getChosenHour(long chosenHourId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOSEN_HOURS + " WHERE "
                + KEY_ID + " = " + chosenHourId;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount() == 0)
            return null;
        ChosenHour chosenHour = new ChosenHour(new Date(cursor.getString(cursor.getColumnIndex(KEY_DATE))),cursor.getInt(cursor.getColumnIndex(KEY_HOUR)));
        chosenHour.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));

        return chosenHour;
    }
    public List<ChosenHour> getAllChosenHours() {
        List<ChosenHour> allHours = new ArrayList<ChosenHour>();
        String selectQuery = "SELECT  * FROM " + TABLE_CHOSEN_HOURS;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ChosenHour hour = new ChosenHour(new Date(c.getString(c.getColumnIndex(KEY_DATE))),c.getInt(c.getColumnIndex(KEY_HOUR)));
                hour.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                allHours.add(hour);
            } while (c.moveToNext());
        }

        return allHours;
    }
    public void updateChosenHour(ChosenHour hour){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, hour.getDate().toString());
        values.put(KEY_HOUR, hour.getHour());

        // updating row
        database.update(TABLE_CHOSEN_HOURS, values, KEY_ID + " = ?", new String[] { String.valueOf(hour.getId()) });
    }
    public void clearChosenHours(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_CHOSEN_HOURS);
    }
    public void deleteChosenHour(long hourId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_CHOSEN_HOURS, KEY_ID + " = ?",
                new String[] { String.valueOf(hourId) });
    }

    public long addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        long id = database.insert(TABLE_USER, null, values);
        user.setId(id);
        return id;
    }
    public User getUser(long userId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_ID + " = " + userId;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User();
        user.setNameAndSurname(cursor.getString(cursor.getColumnIndex(KEY_NAME)),cursor.getString(cursor.getColumnIndex(KEY_SURNAME)));
        user.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        return user;
    }
    public void updateUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());

        // updating row
        database.update(TABLE_USER, values, KEY_ID + " = ?", new String[] { String.valueOf(user.getId()) });
    }
    public void clearUser(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_USER);
    }

    public void addWeatherInfo(WeatherInfo weather) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPERATURE, weather.getTemperature());
        values.put(KEY_HUMIDITY,weather.getHumidity());
        values.put(KEY_CLOUDINESS, weather.getCloudiness().getValue());
        values.put(KEY_PRECIPITATION,weather.getPrecipitation());
        values.put(KEY_DATE, weather.getDate().toString());
        values.put(KEY_IS_CHECKED,weather.getIsChecked());

        long id = database.insert(TABLE_WEATHER_INFO, null, values);
        weather.setId(id);
    }
    public WeatherInfo getWeatherInfo(long weatherId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER_INFO + " WHERE "
                + KEY_ID + " = " + weatherId;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount() == 0)
            return null;

        int temperature = cursor.getInt(cursor.getColumnIndex(KEY_TEMPERATURE));
        Cloudiness cloudiness = Cloudiness.fromId(cursor.getInt(cursor.getColumnIndex(KEY_CLOUDINESS)));
        int humidity = cursor.getInt(cursor.getColumnIndex(KEY_HUMIDITY));
        double precipitation =cursor.getDouble(cursor.getColumnIndex(KEY_PRECIPITATION));
        boolean checked = cursor.getInt(cursor.getColumnIndex(KEY_IS_CHECKED)) > 0;
        Date date = new Date(cursor.getString(cursor.getColumnIndex(KEY_DATE)));

        WeatherInfo weather = new WeatherInfo(temperature,humidity,cloudiness,precipitation,date);

        weather.setIsChecked(checked);
        weather.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));

        return weather;
    }
    public void deleteWeatherInfo(long weatherId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_WEATHER_INFO, KEY_ID + " = ?",
                new String[] { String.valueOf(weatherId) });
    }
    public void clearWeatherInfo(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from "+ TABLE_WEATHER_INFO);
    }
    public void updateWeatherInfo(WeatherInfo weather){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPERATURE, weather.getTemperature());
        values.put(KEY_HUMIDITY,weather.getHumidity());
        values.put(KEY_CLOUDINESS, weather.getCloudiness().getValue());
        values.put(KEY_PRECIPITATION,weather.getPrecipitation());
        values.put(KEY_DATE, weather.getDate().toString());
        values.put(KEY_IS_CHECKED,weather.getIsChecked());

        // updating row
        database.update(TABLE_WEATHER_INFO, values, KEY_ID + " = ?", new String[] { String.valueOf(weather.getId()) });
    }
    public List<WeatherInfo> getAllWeatherInfos() {
        List<WeatherInfo> allWeatherInfos = new ArrayList<WeatherInfo>();
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER_INFO;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                int temperature = c.getInt(c.getColumnIndex(KEY_TEMPERATURE));
                Cloudiness cloudiness = Cloudiness.fromId(c.getInt(c.getColumnIndex(KEY_CLOUDINESS)));
                int humidity = c.getInt(c.getColumnIndex(KEY_HUMIDITY));
                double precipitation =c.getDouble(c.getColumnIndex(KEY_PRECIPITATION));
                boolean checked = c.getInt(c.getColumnIndex(KEY_IS_CHECKED)) > 0;
                Date date = new Date(c.getString(c.getColumnIndex(KEY_DATE)));

                WeatherInfo weather = new WeatherInfo(temperature,humidity,cloudiness,precipitation,date);
                weather.setIsChecked(checked);

                weather.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                allWeatherInfos.add(weather);
            } while (c.moveToNext());
        }

        return allWeatherInfos;
    }
}
