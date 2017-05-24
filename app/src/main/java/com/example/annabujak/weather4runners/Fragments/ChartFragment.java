package com.example.annabujak.weather4runners.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by pawel.bujak on 15.05.2017.
 */

public class ChartFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chart_layout, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GraphView graphDays = (GraphView) view.findViewById(R.id.graphDays);
        GraphView graphHours = (GraphView) view.findViewById(R.id.graphHours);
        BarGraphSeries<DataPoint> seriesDays = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 7),
                new DataPoint(2, 10),
                new DataPoint(3, 11),
                new DataPoint(4, 40),
                new DataPoint(5, 6),
                new DataPoint(6, 20),
                new DataPoint(7, 6)

        });
        BarGraphSeries<DataPoint> seriesHours = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 7),
                new DataPoint(2, 10),
                new DataPoint(3, 11),
                new DataPoint(4, 40),
                new DataPoint(5, 6),
                new DataPoint(6, 20),
                new DataPoint(7, 6)

        });
        seriesHours.setTitle("Hours chosen for run");
        seriesDays.setTitle("Days chosen for run");
        graphDays.getLegendRenderer().setVisible(true);
        graphDays.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphHours.getLegendRenderer().setVisible(true);
        graphHours.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        seriesHours.setColor(Color.parseColor("#FF4081"));

        seriesDays.setDrawValuesOnTop(true);
        graphDays.addSeries(seriesDays);
        graphHours.addSeries(seriesHours);
    }
}
