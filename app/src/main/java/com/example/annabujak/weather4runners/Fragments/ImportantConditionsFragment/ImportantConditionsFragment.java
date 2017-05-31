package com.example.annabujak.weather4runners.Fragments.ImportantConditionsFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;
import com.example.annabujak.weather4runners.Listeners.ImportantConditionsChangedListener;
import com.example.annabujak.weather4runners.Listeners.WeatherConditionsImportanceOrderProvider;
import com.example.annabujak.weather4runners.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by slowik on 21.05.2017.
 */

public class ImportantConditionsFragment extends Fragment{

    private RecyclerView mRecyclerView;

    private ImportantConditionsAdapter importantConditionsAdapter;

    private ArrayList<WeatherConditionsNames> importantWeatherConditionsNames;

    private ImportantConditionsChangedListener importantConditionsChangedListener;

    private FloatingActionButton mFloatingPointButton;

    public static ImportantConditionsFragment Create(
            ArrayList<WeatherConditionsNames> importantWeatherConditionsNames) {
        ImportantConditionsFragment res = new ImportantConditionsFragment();
        res.importantWeatherConditionsNames = importantWeatherConditionsNames;
        return res;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.importantConditionsAdapter = createImportantConditionsAdapter(
                    this.importantWeatherConditionsNames
        );

        setHasOptionsMenu(true);
        setMenuVisibility(false);
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
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.importantConditionsChangedListener = (ImportantConditionsChangedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("the attached fragment should implement ImportantConditionsChangedListener ");
        }
    }

    @Override
    public void onPause() {
        this.importantConditionsChangedListener
                .onImportantConditionsChangedListener(this.importantWeatherConditionsNames);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        this.importantWeatherConditionsNames = ((WeatherConditionsImportanceOrderProvider)getActivity())
                .getWeatherConditionsImportanceOrder();
        this.importantConditionsAdapter.setData(this.importantWeatherConditionsNames);
    }

    private void setChildViews(View view) {
        this.mRecyclerView = createRecyclerView(view, this.importantConditionsAdapter);
        this.mFloatingPointButton = getFloatingPointButton(view);
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

    private FloatingActionButton getFloatingPointButton(View parentView) {
        FloatingActionButton res = (FloatingActionButton) parentView
                .findViewById(R.id.important_conditions_reset_button);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importantWeatherConditionsNames = new ArrayList<WeatherConditionsNames>(
                        Arrays.asList(WeatherConditionsNames.values()));
                importantConditionsAdapter.setData(
                        importantWeatherConditionsNames);
            }
        });

        return res;
    }
}
