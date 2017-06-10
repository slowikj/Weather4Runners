package com.example.annabujak.weather4runners.Fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.Listeners.ChosenPropositionListener;
import com.example.annabujak.weather4runners.Listeners.ChosenPropositionsProvider;
import com.example.annabujak.weather4runners.Objects.ChosenProposition;
import com.example.annabujak.weather4runners.R;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
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
    private StaticLabelsFormatter daysLabel;
    private StaticLabelsFormatter hoursLabel;

    public static ChartFragment create(List<ChosenProposition> allChosenPropositions) {
        ChartFragment res = new ChartFragment();
        res.onChosenPropositionChanged(allChosenPropositions);

        return res;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_chart_share:
                //TODO
                FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(getBitmapFromView(graphHours))
                        .build();

                SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                if(ShareDialog.canShow(SharePhotoContent.class)) {
                    ShareDialog shareDialog = new ShareDialog(this);
                    shareDialog.show(sharePhotoContent);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chart_menu, menu);
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

        hoursLabel = new StaticLabelsFormatter(graphHours);
        daysLabel = new StaticLabelsFormatter(graphDays);
        daysLabel.setHorizontalLabels(new String[]{"Mon","Tue","Wed","Thu","Fri","Sat","Sun"});
        graphDays.getGridLabelRenderer().setLabelFormatter(daysLabel);

        String[] hours = new String[13];
        for(int i = 0; i <= 24; i += 2)
            hours[i/2] = Integer.toString(i);
        hoursLabel.setHorizontalLabels(hours);
        graphHours.getGridLabelRenderer().setLabelFormatter(hoursLabel);
    }

    @Override
    public void onChosenPropositionChanged(List<ChosenProposition> allChosenPropositions) {

        List<ChosenProposition> hours = new ArrayList<>();
        List<ChosenProposition> days = new ArrayList<>();

        for (ChosenProposition hour: allChosenPropositions) {
            if(hour.getIsHour())
                hours.add(hour);
            days.add(hour);
        }

        prepareGraphDays(days);
        prepareGraphHours(hours);
    }

    @Override
    public void onResume() {
        super.onResume();

        onChosenPropositionChanged(
                ((ChosenPropositionsProvider)getActivity())
                        .getAllChosenPropositions());

    }

    private void prepareGraphDays(List<ChosenProposition> chosenPropositions){
        final int DAYS_CNT = 7;

        DataPoint[] points = new DataPoint[DAYS_CNT];
        for(int i  = 0; i < DAYS_CNT; i ++)
            points[i] = new DataPoint(i+1,0);
        for(int i = 0; i < chosenPropositions.size(); i ++){
            Date tempDate = new Date(chosenPropositions.get(i).getDate());
            int index = (tempDate.getDay() - 1 + DAYS_CNT) % DAYS_CNT;
            points[index] = new DataPoint(points[index].getX(),points[index].getY()+1);
        }
        seriesDays = new BarGraphSeries<>(points);
        seriesDays.setTitle("Days chosen for run");
        seriesDays.setDrawValuesOnTop(true);
        if(graphDays != null) {
            graphDays.addSeries(seriesDays);
        }
    }
    private void prepareGraphHours(List<ChosenProposition> chosenPropositions){
        DataPoint[] points = new DataPoint[24];
        for(int i  = 0; i < 24; i ++)
            points[i] = new DataPoint(i,0);
        for(int i = 0; i < chosenPropositions.size(); i ++){
            Date tempDate = new Date(chosenPropositions.get(i).getDate());
            points[tempDate.getHours()] = new DataPoint(points[tempDate.getHours()].getX(),points[tempDate.getHours()].getY()+1);
        }
        seriesHours = new BarGraphSeries<>(points);
        seriesHours.setTitle("Hours chosen for run");
        seriesHours.setColor(Color.parseColor("#FF4081"));

        if(graphHours != null) {
            graphHours.addSeries(seriesHours);
        }
    }

    private static Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap) {
            @Override
            public boolean isHardwareAccelerated() {
                return true;
            }
        };
        view.draw(canvas);

        return bitmap;
    }
}
