package com.example.annabujak.weather4runners.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.Listeners.ChosenPropositionListener;
import com.example.annabujak.weather4runners.Objects.ChosenProposition;
import com.example.annabujak.weather4runners.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pawel.bujak on 15.05.2017.
 */

public class ChartFragment extends Fragment implements ChosenPropositionListener {

    private GraphView graphDays;
    private GraphView graphHours;
    private BarGraphSeries<DataPoint> seriesDays;
    private BarGraphSeries<DataPoint> seriesHours;

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
        graphDays = (GraphView) view.findViewById(R.id.graphDays);
        graphHours = (GraphView) view.findViewById(R.id.graphHours);
        graphDays.getLegendRenderer().setVisible(true);
        graphDays.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphHours.getLegendRenderer().setVisible(true);
        graphHours.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graphDays.addSeries(seriesDays);
        graphHours.addSeries(seriesHours);

    }

    @Override
    public void onChosenPropositionChanged(List<ChosenProposition> allChosenPropositions) {

        List<ChosenProposition> hours = new ArrayList<>();
        List<ChosenProposition> days = new ArrayList<>();

        for (ChosenProposition hour: allChosenPropositions) {
            if(hour.getIsHour())
                hours.add(hour);
            else
                days.add(hour);
        }

        prepareGraphDays(days);
        prepareGraphHours(hours);
    }

    private void prepareGraphDays(List<ChosenProposition> chosenPropositions){
        DataPoint[] points = new DataPoint[7];
        for(int i  = 0; i < 7; i ++)
            points[i] = new DataPoint(i+1,0);
        for(int i = 0; i < chosenPropositions.size(); i ++){
            Date tempDate = new Date(chosenPropositions.get(i).getDate());
            points[tempDate.getDay()] = new DataPoint(points[tempDate.getDay()].getX(),points[tempDate.getDay()].getY()+1);
        }
        seriesDays = new BarGraphSeries<>(points);
        seriesDays.setTitle("Days chosen for run");
        seriesDays.setDrawValuesOnTop(true);
        if(graphDays != null)
            graphDays.addSeries(seriesDays);
    }
    private void prepareGraphHours(List<ChosenProposition> chosenPropositions){
        DataPoint[] points = new DataPoint[24];
        for(int i  = 0; i < 24; i ++)
            points[i] = new DataPoint(i+1,0);
        for(int i = 0; i < chosenPropositions.size(); i ++){
            Date tempDate = new Date(chosenPropositions.get(i).getDate());
            points[tempDate.getHours()-1] = new DataPoint(points[tempDate.getHours()-1].getX(),points[tempDate.getHours()-1].getY()+1);
        }
        seriesHours = new BarGraphSeries<>(points);
        seriesHours.setTitle("Hours chosen for run");
        seriesHours.setColor(Color.parseColor("#FF4081"));
        if(graphHours != null)
            graphHours.addSeries(seriesHours);
    }
}
