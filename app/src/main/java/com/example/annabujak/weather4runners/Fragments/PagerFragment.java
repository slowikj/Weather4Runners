package com.example.annabujak.weather4runners.Fragments;

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

import com.example.annabujak.weather4runners.CentralControl.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.CentralControl.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.AbstractPropositionsFragment;
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.DailyPropositionsFragment;
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.WeeklyPropositionsFragment;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.R;

import java.util.ArrayList;

/**
 * Created by slowik on 07.05.2017.
 */

public class PagerFragment extends Fragment
    implements DailyPropositionsChangedListener,
        WeeklyPropositionsChangedListener {

    private static final String TAG_DAILY_PROPOSITIONS_FRAGMENT = "daily_propositions_frag";

    private static final String TAG_WEEKLY_PROPOSITIONS_FRAGMENT = "weekly_propositions_frag";

    private static final String TAG_STATS_FRAGMENT = "stats_frag";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private AbstractPropositionsFragment dailyPropositions;

    private AbstractPropositionsFragment weeklyPropositions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getChildFragmentManager();
        if(savedInstanceState == null) {
            instantiatePagerFragments(fragmentManager);
        } else {
            fetchPagerFragments(savedInstanceState, fragmentManager);
        }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        savePagerFragments(outState);
    }

    @Override
    public void onDailyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.dailyPropositions.setPropositions(propositions);
    }

    @Override
    public void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.weeklyPropositions.setPropositions(propositions);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setChildViews(View parentView) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) parentView.findViewById(R.id.pager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void instantiatePagerFragments(FragmentManager fragmentManager) {
        this.dailyPropositions = new DailyPropositionsFragment();
        this.weeklyPropositions = new WeeklyPropositionsFragment();
        // TODO: add the statistics fragment
    }

    private void fetchPagerFragments(Bundle savedInstanceState,
                                     FragmentManager fragmentManager) {
        this.dailyPropositions = (AbstractPropositionsFragment) fragmentManager
                .getFragment(savedInstanceState, TAG_DAILY_PROPOSITIONS_FRAGMENT);

        this.weeklyPropositions = (AbstractPropositionsFragment) fragmentManager
                .getFragment(savedInstanceState, TAG_WEEKLY_PROPOSITIONS_FRAGMENT);

        // TODO: add the statistics fragment
    }

    private void savePagerFragments(Bundle outState) {
        FragmentManager fragmentManager = getChildFragmentManager();
        saveFragment(outState, fragmentManager, this.dailyPropositions, TAG_DAILY_PROPOSITIONS_FRAGMENT);
        saveFragment(outState, fragmentManager, this.weeklyPropositions, TAG_WEEKLY_PROPOSITIONS_FRAGMENT);
        // TODO: add statistics fragment here
    }

    private void saveFragment(Bundle outState,
                              FragmentManager fragmentManager,
                              Fragment fragment,
                              String fragmentKey) {
        fragmentManager.putFragment(outState,
                fragmentKey,
                fragment);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final int ALL_PAGES_COUNT = 3;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch(position) {
                case 0: return dailyPropositions;
                case 1: return weeklyPropositions;
                case 2: return new Fragment(); // TODO: change to stats
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return ALL_PAGES_COUNT;
        }

        // TODO: extract string consts
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Day weather section";
                case 1:
                    return "Week weather section";
                case 2:
                    return "Diagram section";
            }
            return null;
        }
    }
}
