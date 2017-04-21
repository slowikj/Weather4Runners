package com.example.annabujak.weather4runners.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.annabujak.weather4runners.Enum.Cloudiness;

import java.util.Date;

/**
 * Created by pawel.bujak on 21.04.2017.
 */

public class DBWeather4Runners extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Weather for runners";

    private static final String TABLE_PREFERENCES_NAME = "preferences";
    private static final String TABLE_BEST_HOURS = "best_hours";
    private static final String TABLE_CHOSEN_HOURS = "chosen_hours";

    private static final String KEY_ID = "id";

    // PREFERENCES Table - column names
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_CLOUDINESS = "cloudiness";
    private static final String KEY_START_HOUR = "start";
    private static final String KEY_END_HOUR = "end";

    // BEST_HOURS, CHOSEN_HOURS Table - column names
    private static final String KEY_DATE = "date";
    private static final String KEY_HOUR = "hour";

    private static final String CREATE_TABLE_PREFERENCES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PREFERENCES_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TEMPERATURE
            + " INTEGER," + KEY_CLOUDINESS + " INTEGER,"+ KEY_START_HOUR + " INTEGER,"
            + KEY_END_HOUR + " INTEGER"+ ")";

    private static final String CREATE_TABLE_BEST_HOURS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_BEST_HOURS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
            + " INTEGER," + KEY_HOUR +" INTEGER"+ ")";
    private static final String CREATE_TABLE_CHOSEN_HOURS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CHOSEN_HOURS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE
            + " TEXT," + KEY_HOUR +" INTEGER"+ ")";

    public DBWeather4Runners(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //    db.execSQL(CREATE_TABLE_PREFERENCES);
        db.execSQL(CREATE_TABLE_BEST_HOURS);
        db.execSQL(CREATE_TABLE_CHOSEN_HOURS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    //    database.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_BEST_HOURS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CHOSEN_HOURS);

        onCreate(database);
    }

    public long addPreference(Preference preference) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPERATURE, preference.getTemperature());
        values.put(KEY_CLOUDINESS, preference.getCloudiness().getValue());
        values.put(KEY_START_HOUR, preference.getStartHour());
        values.put(KEY_END_HOUR, preference.getEndHour());

        return database.insert(TABLE_PREFERENCES_NAME, null, values);
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

        return preference;
    }

    public long addBestHour(BestHour bestHour) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, 1);
        values.put(KEY_HOUR, bestHour.getHour());

        return database.insert(TABLE_BEST_HOURS, null, values);
    }
    public BestHour getBestHour(long bestHourId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BEST_HOURS + " WHERE "
                + KEY_ID + " = " + bestHourId;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        BestHour bestHour = new BestHour(new Date(cursor.getColumnIndex(KEY_DATE)),cursor.getInt(cursor.getColumnIndex(KEY_HOUR)));

        return bestHour;
    }

    public long addChosenHour(ChosenHour chosenHour) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, chosenHour.getDate().toString());
        values.put(KEY_HOUR, chosenHour.getHour());

        return database.insert(TABLE_CHOSEN_HOURS, null, values);
    }
    public ChosenHour getChosenHour(long chosenHourId) {
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CHOSEN_HOURS + " WHERE "
                + KEY_ID + " = " + chosenHourId;
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

        ChosenHour chosenHour = new ChosenHour(new Date(cursor.getColumnIndex(KEY_DATE)),cursor.getInt(cursor.getColumnIndex(KEY_HOUR)));

        return chosenHour;
    }
}
