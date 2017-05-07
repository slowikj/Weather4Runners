package com.example.annabujak.weather4runners;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.annabujak.weather4runners.CentralControl.CentralControl;
import com.example.annabujak.weather4runners.CentralControl.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.CentralControl.UpdatingFinishedListener;
import com.example.annabujak.weather4runners.CentralControl.WeatherForecastManager;
import com.example.annabujak.weather4runners.CentralControl.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Fragments.PagerFragment;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class MainActivity extends AppCompatActivity
        implements DataActivityInterface,
            DailyPropositionsChangedListener,
            WeeklyPropositionsChangedListener,
            UpdatingFinishedListener {

    private ProgressBar mLoadingIndicator;

    private CentralControl centralControl;

    private PagerFragment pagerFragment;

    // TODO add PreferencesFragment here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewReferences();

        this.centralControl = getCentralControl();

        createFragments();

        if(savedInstanceState == null) {
            setFragment(this.pagerFragment, false);
        }

        refreshAll();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_refresh:
                refreshAll();
                return true;
            case R.id.action_settings:
                // TODO: show settings/preferences
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDailyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.pagerFragment.onDailyPropositionsChanged(propositions);
    }

    @Override
    public void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.pagerFragment.onWeeklyPropositionsChanged(propositions);
    }

    @Override
    public void onUpdatingFinished() {
        this.mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void createFragments() {
        // TODO: add preferences fragment here
        this.pagerFragment = new PagerFragment();
    }

    private void refreshAll() {
        this.mLoadingIndicator.setVisibility(View.VISIBLE);
        this.centralControl.updateWeatherForecast();
    }

    private void setViewReferences() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    private CentralControl getCentralControl() {
        CentralControl res = new CentralControl(getApplicationContext());
        res.setDailyPropositionsChangedListener(this);
        res.setWeeklyPropositionsChangedListener(this);
        res.setUpdatingFinishedListener(this);

        return res;
    }

    private void setFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.main_fragment, fragment);

        fragmentTransaction.commit();
    }
}