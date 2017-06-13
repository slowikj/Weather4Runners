package com.example.annabujak.weather4runners;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by slowik on 22.05.2017.
 */

public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    public static SharedPreferencesHelper create(Activity activity) {
        return new SharedPreferencesHelper(
                activity.getPreferences(Context.MODE_PRIVATE));
    }

    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public int getSharedPref(String tag, int defaultValue) {
        return this.sharedPreferences.getInt(tag, defaultValue);
    }

    public void saveSharedPref(String tag, int value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(tag, value);
        editor.commit();
    }

    public float getSharedPref(String tag, float defaultValue) {
        return this.sharedPreferences.getFloat(tag, defaultValue);
    }

    public void saveSharedPref(String tag, float value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putFloat(tag, value);
        editor.commit();
    }

    public String getSharedPref(String tag, String defaultValue) {
        return this.sharedPreferences.getString(tag, defaultValue);
    }

    public void saveSharedPref(String tag, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(tag, value);
        editor.commit();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
