package com.example.annabujak.weather4runners;

import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;
import com.example.annabujak.weather4runners.Fragments.ChartFragment;
import com.example.annabujak.weather4runners.Fragments.ImportantConditionsFragment.ImportantConditionsFragment;
import com.example.annabujak.weather4runners.Listeners.DailyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Listeners.ImportantConditionsChangedListener;
import com.example.annabujak.weather4runners.Listeners.UpdatingFinishedListener;
import com.example.annabujak.weather4runners.Listeners.WeatherForecastUpdater;
import com.example.annabujak.weather4runners.Listeners.WeeklyPropositionsChangedListener;
import com.example.annabujak.weather4runners.Facebook.ILoginFacebook;
import com.example.annabujak.weather4runners.Fragments.LoginFragment;
import com.example.annabujak.weather4runners.Fragments.PagerFragment;
import com.example.annabujak.weather4runners.Fragments.WeatherPreferenceFragment.IWeatherPreferenceFragment;
import com.example.annabujak.weather4runners.Fragments.WeatherPreferenceFragment.WeatherPreferenceFragment;
import com.example.annabujak.weather4runners.Notifiers.DailyWeatherPropositionsNotifier;
import com.example.annabujak.weather4runners.Notifiers.WeeklyWeatherPropositionsNotifier;
import com.example.annabujak.weather4runners.Objects.Preference;
import com.example.annabujak.weather4runners.Objects.PreferenceBalance;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity
        implements DailyPropositionsChangedListener,
            WeeklyPropositionsChangedListener,
            DailyWeatherPropositionsNotifier,
            WeeklyWeatherPropositionsNotifier,
            UpdatingFinishedListener,
            ILoginFacebook,
            IWeatherPreferenceFragment,
            NavigationView.OnNavigationItemSelectedListener,
            ImportantConditionsChangedListener,
            WeatherForecastUpdater{

    private ProgressBar mLoadingIndicator;

    private NavigationView mNavigationView;

    private DrawerLayout mDrawerLayout;

    private CentralControl centralControl;

    private LinkedList<DailyPropositionsChangedListener> dailyPropositionsChangedListeners;

    private LinkedList<WeeklyPropositionsChangedListener> weeklyPropositionsChangedListeners;

    private ArrayList<WeatherInfo> dailyPropositions, weeklyPropositions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViewReferences();
        restorePreviousStateIfAny(savedInstanceState);

        initListenersLists();
        initEmptyPropositionsList();

        this.centralControl = getCentralControl();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.centralControl.updatePropositionsAsync();
    }

    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean returnResult = false;
        switch(item.getItemId()) {
            case R.id.nav_propositions:
                setFragment(new PagerFragment(), true);
                returnResult = true;
                break;
            case R.id.nav_locations:
                // TODO
                returnResult = true;
                break;
            case R.id.nav_favourite_values:
                setFragment(new WeatherPreferenceFragment(), true);
                returnResult = true;
                break;
            case R.id.id_nav_charts:
                setFragment(new ChartFragment(), true);
                returnResult = true;
                break;
            case R.id.nav_order_of_imporance:
                // TODO: get the date from database
                ArrayList<WeatherConditionsNames> importantConditions = new ArrayList<>(
                        Arrays.asList(WeatherConditionsNames.values())
                );
                setFragment(ImportantConditionsFragment.Create(importantConditions), true);
                returnResult = true;
                break;
        }

        this.mDrawerLayout.closeDrawer(GravityCompat.START);

        return returnResult;
    }

    @Override
    public void UpdatePreference(Preference preference) {
        centralControl.updatePreference(preference);
        centralControl.updatePropositionsAsync();
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
        for(DailyPropositionsChangedListener listener: this.dailyPropositionsChangedListeners) {
            listener.onDailyPropositionsChanged(propositions);
        }
    }

    @Override
    public void onWeeklyPropositionsChanged(ArrayList<WeatherInfo> propositions) {
        this.weeklyPropositions = propositions;
        for(WeeklyPropositionsChangedListener listener: this.weeklyPropositionsChangedListeners) {
            listener.onWeeklyPropositionsChanged(propositions);
        }
    }

    @Override
    public void onUpdatingFinished() {
        this.mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public void StartPagerFragment(){
        setFragment(new PagerFragment(), false);
    }

    @Override
    public void AddUser(String firstName, String secondName) {
        User user = new User(firstName,secondName);
        centralControl.updateUser(user);
    }

    private void initListenersLists() {
        this.dailyPropositionsChangedListeners = new LinkedList<>();
        this.weeklyPropositionsChangedListeners = new LinkedList<>();
    }

    private void initEmptyPropositionsList() {
        this.dailyPropositions = new ArrayList<>();
        this.weeklyPropositions = new ArrayList<>();
    }

    private void restorePreviousStateIfAny(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            FacebookSdk.sdkInitialize(this);
            setFragment(new LoginFragment() ,false);
        }
    }

    private void refreshAll() {
        this.mLoadingIndicator.setVisibility(View.VISIBLE);
        this.centralControl.updateWeatherForecastAsync();
    }

    private void setViewReferences() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        this.mDrawerLayout = getDrawerLayout(toolbar);

        this.mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        this.mNavigationView.setNavigationItemSelectedListener(this);
        this.mNavigationView.setItemIconTintList(null);
    }

    private DrawerLayout getDrawerLayout(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_content);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        return drawer;
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

    @Override
    public void onImportantConditionsChangedListener(ArrayList<WeatherConditionsNames> orderedImportantConditions) {
        // TODO
        // convert the argument into PreferenceBalance and update preferenceBalance in database
    }

    @Override
    public void onWeatherForecastUpdate() {
        this.refreshAll();
    }
}