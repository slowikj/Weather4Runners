package com.example.annabujak.weather4runners.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.Listeners.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Listeners.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Notifiers.DailyWeatherPropositionsNotifier;
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.DailyPropositionsFragment;
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.WeeklyPropositionsFragment;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.R;
import com.example.annabujak.weather4runners.Notifiers.WeeklyWeatherPropositionsNotifier;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by slowik on 07.05.2017.
 */

public class PagerFragment extends Fragment
    implements DailyWeatherPropositionsNotifier,
        WeeklyWeatherPropositionsNotifier,
        DailyPropositionsChangedListener,
        WeeklyPropositionsChangedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private DailyWeatherPropositionsNotifier dailyWeatherPropositionsNotifier;

    private WeeklyWeatherPropositionsNotifier weeklyWeatherPropositionsNotifier;

    private LinkedList<DailyPropositionsChangedListener> dailyPropositionsChangedListeners;

    private LinkedList<WeeklyPropositionsChangedListener> weeklyPropositionsChangedListeners;

    private ArrayList<WeatherInfo> dailyPropositions, weeklyPropositions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initListenersLists();
        initEmptyPropositionsLists();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setChildViews(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        attachDailyWeatherPropositionsNotifier(context);
        attachWeeklyWeatherPropositionsNotifier(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.dailyWeatherPropositionsNotifier
                .subscribeForDailyWeatherPropositionsChanged(this);

        this.weeklyWeatherPropositionsNotifier
                .subscribeForWeeklyWeatherPropositionsChanged(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.dailyWeatherPropositionsNotifier
                .unsubscribeForDailyWeatherPropositionsChanged(this);

        this.weeklyWeatherPropositionsNotifier
                .unsubscribeForWeeklyWeatherPropositionsChanged(this);
    }

    @Override
    public void subscribeForDailyWeatherPropositionsChanged(DailyPropositionsChangedListener listener) {
        this.dailyPropositionsChangedListeners.add(listener);
        listener.onDailyPropositionsChanged(this.dailyPropositions);
    }

    @Override
    public void unsubscribeForDailyWeatherPropositionsChanged(DailyPropositionsChangedListener listener) {
        this.dailyPropositionsChangedListeners.remove(listener);
    }

    @Override
    public void subscribeForWeeklyWeatherPropositionsChanged(WeeklyPropositionsChangedListener listener) {
        this.weeklyPropositionsChangedListeners.add(listener);
        listener.onWeeklyPropositionsChanged(this.weeklyPropositions);
    }

    @Override
    public void unsubscribeForWeeklyWeatherPropositionsChanged(WeeklyPropositionsChangedListener listener) {
        this.weeklyPropositionsChangedListeners.remove(listener);
    }

    @Override
    public void onDailyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.dailyPropositions = propositions;
        notifyDailyPropositionsChanged();
    }

    @Override
    public void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.weeklyPropositions = propositions;
        notifyWeeklyPropositionsChanged();
    }

    private void initListenersLists() {
        dailyPropositionsChangedListeners = new LinkedList<>();
        weeklyPropositionsChangedListeners = new LinkedList<>();
    }

    private void initEmptyPropositionsLists() {
        this.dailyPropositions = new ArrayList<>();
        this.weeklyPropositions = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setChildViews(View parentView) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) parentView.findViewById(R.id.pager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void attachWeeklyWeatherPropositionsNotifier(Context context) {
        try {
            this.weeklyWeatherPropositionsNotifier = (WeeklyWeatherPropositionsNotifier)context;
        } catch(ClassCastException e) {
            throw new ClassCastException(
                    "PagerFragment has to implement WeeklyWeatherPropositionsNotifier interface");
        }
    }

    private void attachDailyWeatherPropositionsNotifier(Context context) {
        try {
            this.dailyWeatherPropositionsNotifier = (DailyWeatherPropositionsNotifier) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(
                    "PagerFragment has to implement DailyWeatherPropositionsNotifier interface");
        }
    }

    private void notifyDailyPropositionsChanged() {
        for(DailyPropositionsChangedListener listener: this.dailyPropositionsChangedListeners) {
            listener.onDailyPropositionsChanged(this.dailyPropositions);
        }
    }

    private void notifyWeeklyPropositionsChanged() {
        for(WeeklyPropositionsChangedListener listener: this.weeklyPropositionsChangedListeners) {
            listener.onWeeklyPropositionsChanged(this.weeklyPropositions);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final int ALL_PAGES_COUNT = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0: return new DailyPropositionsFragment();
                case 1: return new WeeklyPropositionsFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return ALL_PAGES_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Day weather section";
                case 1:
                    return "Week weather section";
            }
            return null;
        }
    }
}
