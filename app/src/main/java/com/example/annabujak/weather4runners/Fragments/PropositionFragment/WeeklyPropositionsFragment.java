package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.annabujak.weather4runners.Fragments.PropositionFragment.Command.Command;
import com.example.annabujak.weather4runners.Listeners.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Objects.ChosenHour;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.Notifiers.WeeklyWeatherPropositionsNotifier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 12.05.2017.
 */

public class WeeklyPropositionsFragment extends AbstractPropositionsFragment
    implements WeeklyPropositionsChangedListener {

    private static final String SHARED_PREF_NUMBER_OF_ITEMS_TAG = "shared_pref_number_of_weekly_items_tag";

    private static final int DEFAULT_NUMBER_OF_ITEMS = 4;

    private WeeklyWeatherPropositionsNotifier weeklyWeatherPropositionsNotifier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.itemsListDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Command<Integer> getChangeIntPrefCommand() {
        return new Command<Integer>() {
            @Override
            public void execute(Integer value) {
                sharedPreferencesHelper.saveIntSharedPref(
                        SHARED_PREF_NUMBER_OF_ITEMS_TAG,
                        value);
            }
        };
    }

    @Override
    protected ChosenHour getChosenProposition(long date) {
        return null; // TODO: return new ChosenHour(date, false)
    }

    @Override
    protected int getNumberOfItemsToShowOrDefault() {
        return sharedPreferencesHelper.getIntSharedPref(
                SHARED_PREF_NUMBER_OF_ITEMS_TAG,
                DEFAULT_NUMBER_OF_ITEMS);
    }

    @Override
    public void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.propositions = propositions;
        updatePropositionsListView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.weeklyWeatherPropositionsNotifier = (WeeklyWeatherPropositionsNotifier) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("WeeklyPropositionsFragment has to implement WeeklyWeatherPropositionsNotifier");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.weeklyWeatherPropositionsNotifier
                .subscribeForWeeklyWeatherPropositionsChanged(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.weeklyWeatherPropositionsNotifier
                .unsubscribeForWeeklyWeatherPropositionsChanged(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch(key) {
            case SHARED_PREF_NUMBER_OF_ITEMS_TAG:
                updatePropositionsListView();
                break;
        }
    }
}
