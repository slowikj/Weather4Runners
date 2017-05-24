package com.example.annabujak.weather4runners.Fragments.ImportantConditionsFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;
import com.example.annabujak.weather4runners.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by slowik on 21.05.2017.
 */

public class ImportantConditionsFragment extends Fragment{

    private RecyclerView mRecyclerView;

    private ImportantConditionsAdapter importantConditionsAdapter;

    private ArrayList<WeatherConditionsNames> importantWeatherConditionsNames;

    public static ImportantConditionsFragment Create(
            ArrayList<WeatherConditionsNames> importantWeatherConditionsNames) {
        ImportantConditionsFragment res = new ImportantConditionsFragment();
        res.importantWeatherConditionsNames = importantWeatherConditionsNames;
        return res;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String str = WeatherConditionsNames.Cloudiness.toString();

        this.importantConditionsAdapter = createImportantConditionsAdapter(
                this.importantWeatherConditionsNames
        );

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.important_conditions_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setChildViews(view);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_important_conditions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_important_conditions_refresh:
                this.importantWeatherConditionsNames = new ArrayList<WeatherConditionsNames>(
                        Arrays.asList(WeatherConditionsNames.values()));
                this.importantConditionsAdapter.setData(
                        this.importantWeatherConditionsNames);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setChildViews(View view) {
        this.mRecyclerView = createRecyclerView(view, this.importantConditionsAdapter);
    }

    private RecyclerView createRecyclerView(View parentView, ImportantConditionsAdapter adapter) {
        RecyclerView recyclerView = (RecyclerView)parentView.findViewById(R.id.rv_important_conditions);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        return recyclerView;
    }

    @NonNull
    private ImportantConditionsAdapter createImportantConditionsAdapter(
            ArrayList<WeatherConditionsNames> importantWeatherConditionsNames) {
        return new ImportantConditionsAdapter(importantWeatherConditionsNames);
    }
}
