package com.example.annabujak.weather4runners.Weather.Filter;

/**
 * Created by pawel.bujak on 05.05.2017.
 */

public class WeatherPreferencePair<L,R> {
    public final L left;
    public final R right;

    public WeatherPreferencePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WeatherPreferencePair)) return false;
        WeatherPreferencePair pairo = (WeatherPreferencePair) o;
        return this.left.equals(pairo.left) &&
                this.right.equals(pairo.right);
    }

}
