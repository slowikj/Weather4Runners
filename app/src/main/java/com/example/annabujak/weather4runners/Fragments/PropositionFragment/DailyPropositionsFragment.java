package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.annabujak.weather4runners.Listeners.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Notifiers.DailyWeatherPropositionsNotifier;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 12.05.2017.
 */

public class DailyPropositionsFragment extends AbstractPropositionsFragment
    implements DailyPropositionsChangedListener {

    private DailyWeatherPropositionsNotifier dailyWeatherPropositionsNotifier;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.itemsListDateFormat = new SimpleDateFormat("E, HH:mm");
        super.onCreate(savedInstanceState);
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
        this.propositionsListAdapter.setPropositionsList(propositions);
    }
}
