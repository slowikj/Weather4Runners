package com.example.annabujak.weather4runners.Objects;

import java.util.ArrayList;

/**
 * Created by slowik on 30.05.2017.
 */

public class PropositionsList {

    private ArrayList<WeatherInfo> list;

    public PropositionsList() {
        this.list = new ArrayList<WeatherInfo>();
    }

    public PropositionsList(ArrayList<WeatherInfo> list) {
        this.list = list;
    }

    public int size() {
        return this.list.size();
    }

    public WeatherInfo get(int position) {
        return this.list.get(position);
    }

    public void set(int position, WeatherInfo weatherInfo) {
        this.list.set(position, weatherInfo);
    }

    public PropositionsList getDeepCopy() {
        ArrayList<WeatherInfo> res = new ArrayList<>();
        for(WeatherInfo weatherInfo: this.list) {
            res.add(weatherInfo.getDeepCopy());
        }

        return new PropositionsList(res);
    }
}
