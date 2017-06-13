package com.example.annabujak.weather4runners.Fragments.WeatherPreferenceFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.MainActivity;
import com.example.annabujak.weather4runners.Objects.Preference;
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


    SharedPreferences preferences;

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

        SharedPreferences sharedPref = getPreferenceManager().getSharedPreferences();
        int temperature_ind = getTemperatureIndexOrDefault(sharedPref);
        int humidity_ind = getHumidityIndexOrDefault(sharedPref);
        int precipitation_ind = getPrecipitationIndexOrDefault(sharedPref);
        int wind_ind = getWindSpeedIndexOrDefault(sharedPref);
        int cloudiness_ind = getCloudinessIndexOrDefault(sharedPref);
        int startHour_ind = getStartHourIndexOrDefault(sharedPref);
        int endHour_ind = getEndHourIndexOrDefault(sharedPref);

        seekTemperature =  (SeekBarPreference) findPreference(SEEK_T);
        seekTemperature.setValue(temperature_ind);

        seekHumidity = (SeekBarPreference) findPreference(SEEK_H);
        seekHumidity.setValue(humidity_ind);

        seekPrecipitation = (SeekBarPreference) findPreference(SEEK_P);
        seekPrecipitation.setValue(precipitation_ind);

        seekWindSpeed = (SeekBarPreference) findPreference(SEEK_W);
        seekWindSpeed.setValue(wind_ind);

        seekCloudiness = (SeekBarPreference) findPreference(SEEK_C);
        seekCloudiness.setValue(cloudiness_ind);

        seekStartHour = (SeekBarPreference) findPreference(SEEK_S);
        seekStartHour.setValue(startHour_ind);

        seekEndHour = (SeekBarPreference) findPreference(SEEK_E);
        seekEndHour.setValue(endHour_ind);

        setHasOptionsMenu(true);
        setMenuVisibility(false);
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

        updatePreference();
    }

    private void updatePreference() {
        Preference preference = new Preference(
                RenderPreference.getTemperature(getTemperatureIndexOrDefault(preferences)),
                Cloudiness.fromId(RenderPreference.getCloudiness(getCloudinessIndexOrDefault(preferences))),
                RenderPreference.getStartHour(getStartHourIndexOrDefault(preferences)),
                RenderPreference.getEndHours(getEndHourIndexOrDefault(preferences)),
                RenderPreference.getHumidity(getHumidityIndexOrDefault(preferences)),
                (double)RenderPreference.getPrecipitation(getPrecipitationIndexOrDefault(preferences)),
                (double)RenderPreference.getWindSpeed(getWindSpeedIndexOrDefault(preferences)));

        ((MainActivity)getActivity()).UpdatePreference(preference);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.white));

        return view;
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

        preferences = prefs;

        if (SEEK_T.equals(key)) {
            int i = getTemperatureIndexOrDefault(prefs);
            seekTemperature.setSummary(RenderPreference.getTemperature(i)+" ");
        } else if (SEEK_H.equals(key)) {
            int i = getHumidityIndexOrDefault(prefs);
            seekHumidity.setSummary(RenderPreference.getHumidity(i)+" ");
        }
        else if (SEEK_P.equals(key)) {
            int i = getPrecipitationIndexOrDefault(prefs);
            seekPrecipitation.setSummary(RenderPreference.getPrecipitation(i)+" ");
        }
        else if (SEEK_W.equals(key)) {
            int i = getWindSpeedIndexOrDefault(prefs);
            seekWindSpeed.setSummary(RenderPreference.getWindSpeed(i)+" ");
        }
        else if (SEEK_C.equals(key)) {
            int i = getCloudinessIndexOrDefault(prefs);
            seekCloudiness.setSummary(RenderPreference.getCloudiness(i)+" ");
        }
        else if (SEEK_S.equals(key)) {
            int i = getStartHourIndexOrDefault(prefs);
            seekStartHour.setSummary(RenderPreference.getStartHour(i)+" ");
        }
        else if (SEEK_E.equals(key)) {
            int i = getEndHourIndexOrDefault(prefs);
            seekEndHour.setSummary(RenderPreference.getEndHours(i)+" ");
        }
    }

    private int getTemperatureIndexOrDefault(SharedPreferences sharedPref) {
        return sharedPref.getInt(SEEK_T, getDefaultTemperatureIndex());
    }

    private int getHumidityIndexOrDefault(SharedPreferences sharedPref) {
        return sharedPref.getInt(SEEK_H, getDefaultHumidityIndex());
    }

    private int getPrecipitationIndexOrDefault(SharedPreferences sharedPref) {
        return sharedPref.getInt(SEEK_P, getDefaultPrecipitationIndex());
    }

    private int getWindSpeedIndexOrDefault(SharedPreferences sharedPref) {
        return sharedPref.getInt(SEEK_W, getDefaultWindSpeedIndex());
    }

    private int getCloudinessIndexOrDefault(SharedPreferences sharedPref) {
        return sharedPref.getInt(SEEK_C, getDefaultCloudinessIndex());
    }

    private int getStartHourIndexOrDefault(SharedPreferences sharedPref) {
        return sharedPref.getInt(SEEK_S, getDefaultStartHourIndex());
    }

    private int getEndHourIndexOrDefault(SharedPreferences sharedPref) {
        return sharedPref.getInt(SEEK_E, getDefaultEndHourIndex());
    }

    private static int getDefaultTemperatureIndex() {
        return RenderPreference.getIndexFromTemperature(Preference.DEFAULT_TEMPERATURE);
    }

    private static int getDefaultHumidityIndex() {
        return RenderPreference.getIndexFromHumidity(Preference.DEFAULT_HUMIDITY);
    }

    private static int getDefaultPrecipitationIndex() {
        return RenderPreference.getIndexFromPrecipitation((int)Preference.DEFAULT_PRECIPITATION);
    }

    private static int getDefaultWindSpeedIndex() {
        return RenderPreference.getIndexFromWindSpeed((int)Preference.DEFAULT_WIND_SPEED);
    }

    private static int getDefaultCloudinessIndex() {
        return RenderPreference.getIndexFromCloudiness(Preference.DEFAULT_CLOUDINESS.getValue());
    }

    private static int getDefaultStartHourIndex() {
        return RenderPreference.getIndexFromStartHour(Preference.DEFAULT_START_HOUR);
    }

    private static int getDefaultEndHourIndex() {
        return RenderPreference.getIndexFromEndHour(Preference.DEFAULT_END_HOUR);
    }
}
