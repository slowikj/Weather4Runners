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
import com.example.annabujak.weather4runners.Fragments.PropositionFragment.PropositionsFragment;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.R;

import java.util.ArrayList;

/**
 * Created by slowik on 07.05.2017.
 */

public class PagerFragment extends Fragment
    implements DailyPropositionsChangedListener,
        WeeklyPropositionsChangedListener {

    private View fullView;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private PropositionsFragment dailyPropositions;

    private PropositionsFragment weeklyPropositions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: attach activity interface

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(this.fullView == null) {
            this.fullView = createFullView(inflater, container);
            setChildViews(this.fullView);
            createPagerFragments();
        }

        return this.fullView;
    }

    @Override
    public void onDailyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.dailyPropositions.setPropositions(propositions);
    }

    @Override
    public void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.weeklyPropositions.setPropositions(propositions);
    }

    private View createFullView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.pager_fragment_layout, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setChildViews(View parentView) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager) parentView.findViewById(R.id.pager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private void createPagerFragments() {
        this.dailyPropositions = PropositionsFragment.getDailyPropositionsFragment();
        this.weeklyPropositions = PropositionsFragment.getWeeklyPropositionsFragment();
        //TODO: add the third one, related to statistics
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
                case 2: return new android.support.v4.app.Fragment(); // TODO: add statistics
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
