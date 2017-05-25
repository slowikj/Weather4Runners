package com.example.annabujak.weather4runners.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.Listeners.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Listeners.PropositionClickedListener;
import com.example.annabujak.weather4runners.Listeners.WeatherForecastUpdater;
import com.example.annabujak.weather4runners.Listeners.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Notifiers.DailyWeatherPropositionsNotifier;
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.DailyPropositionsFragment;
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.WeeklyPropositionsFragment;
import com.example.annabujak.weather4runners.Objects.ChosenHour;
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
        WeeklyPropositionsChangedListener,
        PropositionClickedListener {

    private static final int ALL_PAGES_COUNT = 2;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private FloatingActionButton mRefreshButton;

    private TabLayout mTabLayout;

    private DailyWeatherPropositionsNotifier dailyWeatherPropositionsNotifier;

    private WeeklyWeatherPropositionsNotifier weeklyWeatherPropositionsNotifier;

    private LinkedList<DailyPropositionsChangedListener> dailyPropositionsChangedListeners;

    private LinkedList<WeeklyPropositionsChangedListener> weeklyPropositionsChangedListeners;

    private PropositionClickedListener propositionClickedListener;

    private ArrayList<WeatherInfo> dailyPropositions, weeklyPropositions;

    private WeatherForecastUpdater weatherForecastUpdater;

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
        attachWeatherForecastUpdater((WeatherForecastUpdater) context);
        attachPropositionClickedListener((PropositionClickedListener) context);
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

    @Override
    public void onPropositionClickedListener(ChosenHour clickedHour) {
        this.propositionClickedListener.onPropositionClickedListener(clickedHour);
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
        mTabLayout = createTabLayout(
                new String[] {"Next 24 hours", "Next few days"},
                parentView,
                R.id.tab_layout);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) parentView.findViewById(R.id.pager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(this.mTabLayout));

        this.mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        this.mRefreshButton = getRefreshButton(parentView);
    }

    private FloatingActionButton getRefreshButton(View parentView) {
        FloatingActionButton res = (FloatingActionButton) parentView.findViewById(
                R.id.forecast_refresh_button);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherForecastUpdater.onWeatherForecastUpdate();
            }
        });

        return res;
    }

    private TabLayout createTabLayout(String[] tabNames, View parentView, int layoutId) {
        TabLayout tabLayout = (TabLayout) parentView.findViewById(layoutId);
        for(String tabName: tabNames) {
            tabLayout.addTab(tabLayout.newTab().setText(tabName));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        return tabLayout;
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

    private void attachPropositionClickedListener(PropositionClickedListener context) {
        try {
            this.propositionClickedListener = context;
        } catch(ClassCastException e) {
            throw new ClassCastException("attached element must implement PropositionsClickedListener");
        }
    }

    private void attachWeatherForecastUpdater(WeatherForecastUpdater context) {
        try {
            this.weatherForecastUpdater = context;
        } catch(ClassCastException e) {
            throw new ClassCastException("attached element must implement WeatherForecastUpdater");
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
