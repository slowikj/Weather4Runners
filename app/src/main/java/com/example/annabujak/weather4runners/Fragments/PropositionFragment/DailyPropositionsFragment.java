package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.annabujak.weather4runners.Fragments.PropositionFragment.Command.Command;
import com.example.annabujak.weather4runners.Listeners.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Notifiers.DailyWeatherPropositionsNotifier;
import com.example.annabujak.weather4runners.Objects.ChosenProposition;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 12.05.2017.
 */

public class DailyPropositionsFragment extends AbstractPropositionsFragment
    implements DailyPropositionsChangedListener {

    private static final String SHARED_PREF_NUMBER_OF_ITEMS_TAG = "number_of_daily_items_shared_pref_tag";

    private static final int DEFAULT_NUMBER_OF_ITEMS = 4;

    private DailyWeatherPropositionsNotifier dailyWeatherPropositionsNotifier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.itemsListDateFormat = new SimpleDateFormat("E, HH:mm");

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
    protected ChosenProposition getChosenProposition(long date) {
       return new ChosenProposition(date, true);
    }

    @Override
    protected int getNumberOfItemsToShowOrDefault() {
        return sharedPreferencesHelper.getIntSharedPref(
                SHARED_PREF_NUMBER_OF_ITEMS_TAG,
                DEFAULT_NUMBER_OF_ITEMS);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.dailyWeatherPropositionsNotifier = (DailyWeatherPropositionsNotifier) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(
                    "DailyPropositionsFragment has to implement DailyWeatherPropositionsNotifier interface");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.dailyWeatherPropositionsNotifier
                .subscribeForDailyWeatherPropositionsChanged(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.dailyWeatherPropositionsNotifier
                .unsubscribeForDailyWeatherPropositionsChanged(this);
    }

    @Override
    public void onDailyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.propositions = propositions;
        updatePropositionsListView();
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
