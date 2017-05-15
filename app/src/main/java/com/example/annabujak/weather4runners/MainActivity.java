package com.example.annabujak.weather4runners;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;

import com.example.annabujak.weather4runners.CentralControl.CentralControl;
import com.example.annabujak.weather4runners.CentralControl.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.CentralControl.UpdatingFinishedListener;
import com.example.annabujak.weather4runners.CentralControl.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Facebook.ILoginFacebook;
import com.example.annabujak.weather4runners.Fragments.ChartFragment;
import com.example.annabujak.weather4runners.Fragments.LoginFragment;
import com.example.annabujak.weather4runners.Fragments.PagerFragment;
import com.example.annabujak.weather4runners.Fragments.PreferenceFragment;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.facebook.FacebookSdk;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements DailyPropositionsChangedListener,
            WeeklyPropositionsChangedListener,
            UpdatingFinishedListener,
            ILoginFacebook{

    private static final String TAG_PAGER_FRAGMENT = "pager_fragment_tag";

    private ProgressBar mLoadingIndicator;

    private CentralControl centralControl;

    private PagerFragment pagerFragment;

    private LoginFragment loginFragment;

    private PreferenceFragment preferenceFragment;

    private ChartFragment chartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewReferences();
        restorePreviousStateOrInstantiate(savedInstanceState);

        this.centralControl = getCentralControl();
        //this.centralControl.updatePropositions();
    }

    private void restorePreviousStateOrInstantiate(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            createFragments(fragmentManager);
            setFragment(loginFragment,false);
        } else {
            fetchFragments(fragmentManager, savedInstanceState);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        saveFragmentInstances(outState);
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
                setFragment(preferenceFragment,true);
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

    private void createFragments(FragmentManager fragmentManager) {

        FacebookSdk.sdkInitialize(this);
        this.pagerFragment = new PagerFragment();
        loginFragment = new LoginFragment();
        preferenceFragment = new PreferenceFragment();
        chartFragment = new ChartFragment();
    }
    @Override
    public void StartPagerFragment(){
        setFragment(this.pagerFragment, false);
    }

    @Override
    public void AddUser(String firstName, String secondName) {
        User user = new User(firstName,secondName);
        centralControl.updateUser(user);
    }

    private void fetchFragments(FragmentManager fragmentManager,
                                Bundle savedInstanceState) {
        // TODO: add the other fragments here
        this.pagerFragment = (PagerFragment) fragmentManager.getFragment(
                savedInstanceState,
                TAG_PAGER_FRAGMENT);
    }

    private void saveFragmentInstances(Bundle outState) {
        if(getFragmentManager().findFragmentById(R.id.main_fragment) == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        saveFragment(outState, fragmentManager, this.pagerFragment, TAG_PAGER_FRAGMENT);
        // TODO: add the other fragments here
    }

    private void saveFragment(Bundle outState,
                              FragmentManager fragmentManager,
                              Fragment fragment,
                              String fragmentTag) {
        fragmentManager.putFragment(outState,
                fragmentTag,
                fragment);
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
    //Overload for Preference Fragment
    private void setFragment(android.preference.PreferenceFragment fragment, boolean addToBackStack) {
        android.app.FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager
                .beginTransaction();
        if (addToBackStack) {
            mFragmentTransaction.addToBackStack(null);
        }
        mFragmentTransaction.replace(android.R.id.content, fragment);
        mFragmentTransaction.commit();
    }
}