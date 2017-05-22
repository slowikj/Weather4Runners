package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

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

    public int getIntSharedPref(String tag, Integer defaultValue) {
        return this.sharedPreferences.getInt(tag, defaultValue);
    }

    public void saveIntSharedPref(String tag, int value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(tag, value);
        editor.commit();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
