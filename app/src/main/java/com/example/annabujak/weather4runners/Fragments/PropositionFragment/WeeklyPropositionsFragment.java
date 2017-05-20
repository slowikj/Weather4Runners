package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.annabujak.weather4runners.CentralControl.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.WeeklyWeatherPropositionsNotifier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 12.05.2017.
 */

public class WeeklyPropositionsFragment extends AbstractPropositionsFragment
    implements WeeklyPropositionsChangedListener {

    private WeeklyWeatherPropositionsNotifier weeklyWeatherPropositionsNotifier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.itemsListDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.propositionsListAdapter.setPropositionsList(propositions);
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
}
