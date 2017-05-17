package com.example.annabujak.weather4runners.Fragments.WeatherPreferenceFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.R;
import com.example.annabujak.weather4runners.SeekSlider.SeekBarPreference;

/**
 * Created by pawel.bujak on 15.05.2017.
 */

public class WeatherPreferenceFragment extends android.preference.PreferenceFragment  implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SeekBarPreference seekTemperature;
    private SeekBarPreference seekHumidity;
    private SeekBarPreference seekPrecipitation;
    private SeekBarPreference seekWindSpeed;
    private SeekBarPreference seekCloudiness;
    private SeekBarPreference seekStartHour;
    private SeekBarPreference seekEndHour;

    private String SEEK_T;
    private String SEEK_H;
    private String SEEK_P;
    private String SEEK_W;
    private String SEEK_C;
    private String SEEK_S;
    private String SEEK_E;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference);

        SEEK_T = getString(R.string.seek_temperature);
        SEEK_H = getString(R.string.seek_humidity);
        SEEK_P = getString(R.string.seek_precipitation);
        SEEK_W = getString(R.string.seek_windspeed);
        SEEK_C = getString(R.string.seek_cloudiness);
        SEEK_S = getString(R.string.seek_starthour);
        SEEK_E = getString(R.string.seek_endhour);

        seekTemperature =  (SeekBarPreference) findPreference(SEEK_T);
        seekHumidity = (SeekBarPreference) findPreference(SEEK_H);
        seekPrecipitation = (SeekBarPreference) findPreference(SEEK_P);
        seekWindSpeed = (SeekBarPreference) findPreference(SEEK_W);
        seekCloudiness = (SeekBarPreference) findPreference(SEEK_C);
        seekStartHour = (SeekBarPreference) findPreference(SEEK_S);
        seekEndHour = (SeekBarPreference) findPreference(SEEK_E);

    }
    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        // set the summaries from saved values
        onSharedPreferenceChanged(prefs, SEEK_T);
        onSharedPreferenceChanged(prefs, SEEK_H);
        onSharedPreferenceChanged(prefs, SEEK_P);
        onSharedPreferenceChanged(prefs, SEEK_W);
        onSharedPreferenceChanged(prefs, SEEK_C);
        onSharedPreferenceChanged(prefs, SEEK_S);
        onSharedPreferenceChanged(prefs, SEEK_E);
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));

        return view;
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        //TODO metody do prawidłowego przekształcania 0-100 na wartości preferencji

        if (SEEK_T.equals(key)) {
            int i = prefs.getInt(key, 0);
            seekTemperature.setSummary(i+" ");
        } else if (SEEK_H.equals(key)) {
            int i = prefs.getInt(key, 0);
            seekHumidity.setSummary(i+" ");
        }
        else if (SEEK_P.equals(key)) {
            int i = prefs.getInt(key, 0);
            seekPrecipitation.setSummary(i+" ");
        }
        else if (SEEK_W.equals(key)) {
            int i = prefs.getInt(key, 0);
            seekWindSpeed.setSummary(i+" ");
        }
        else if (SEEK_C.equals(key)) {
            int i = prefs.getInt(key, 0);
            seekCloudiness.setSummary(i+" ");
        }
        else if (SEEK_S.equals(key)) {
            int i = prefs.getInt(key, 0);
            seekStartHour.setSummary(i+" ");
        }
        else if (SEEK_E.equals(key)) {
            int i = prefs.getInt(key, 0);
            seekEndHour.setSummary(i+" ");
        }

    }
}
