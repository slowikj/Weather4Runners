package com.example.annabujak.weather4runners.Weather.Approximators;

import com.example.annabujak.weather4runners.Enum.Cloudiness;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by slowik on 03.05.2017.
 */

public class WeatherInfosLinearApproximator extends WeatherInfosApproximator {

    public WeatherInfosLinearApproximator(WeatherInfo begin, WeatherInfo end, int allItemsCnt) {
        super(begin, end, allItemsCnt);
    }

    @Override
    public ArrayList<WeatherInfo> getApproximated() {
        ArrayList<WeatherInfo> res = new ArrayList<>();

        for (int i = 0; i < allItemsCnt - 1; ++i) {
            res.add(getApproximatedWeatherInfo(i));
        }

        return res;
    }

    private WeatherInfo getApproximatedWeatherInfo(int index) {
        return new WeatherInfo(
                getApproximatedTemparature(index),
                getApproximatedHumidity(index),
                getApproximatedCloudiness(index),
                getApproximatedPrecipitation(index),
                getApproximatedDateLong(index),
                getApproximatedWindSpeed(index),
                getApproximatedIconName(index),
                getApproximatedDescription(index)
        );
    }

    private int getApproximatedTemparature(int index) {
        return (int) getApproximatedNumericValue(
                this.begin.getTemperature(),
                this.end.getTemperature(),
                index,
                this.allItemsCnt);
    }

    private int getApproximatedHumidity(int index) {
        return (int) getApproximatedNumericValue(
                this.begin.getHumidity(),
                this.end.getHumidity(),
                index,
                this.allItemsCnt
        );
    }

    private Cloudiness getApproximatedCloudiness(int index) {
        return Cloudiness.fromId(
                (int) Math.round(getApproximatedNumericValue(
                        this.begin.getCloudiness().getValue(),
                        this.end.getCloudiness().getValue(),
                        index,
                        this.allItemsCnt
                )));
    }

    private double getApproximatedPrecipitation(int index) {
        return this.begin.getPrecipitation() / (allItemsCnt - 1);
    }

    private Date getApproximatedDate(int index) {
        return new Date(
                (long) getApproximatedNumericValue(
                        this.begin.getDate().getTime(),
                        this.end.getDate().getTime(),
                        index,
                        this.allItemsCnt
                ));
    }

    private long getApproximatedDateLong(int index) {
        return (long)getApproximatedNumericValue(
                this.begin.getDateLong(),
                this.end.getDateLong(),
                index,
                this.allItemsCnt
        );
    }

    private double getApproximatedWindSpeed(int index) {
        return getApproximatedNumericValue(
                this.begin.getWindSpeed(),
                this.end.getWindSpeed(),
                index,
                this.allItemsCnt
        );
    }

    private String getApproximatedIconName(int index) {
        return getApproximatedStringValue(
                this.begin.getIconName(),
                this.end.getIconName(),
                index,
                this.allItemsCnt
        );
    }

    private String getApproximatedDescription(int index) {
        return getApproximatedStringValue(
                this.begin.getDescription(),
                this.end.getDescription(),
                index,
                this.allItemsCnt
        );
    }

    private static double getApproximatedNumericValue(
            double firstValue, double lastValue, double index, double allItemsCnt) {
        double inc = (lastValue - firstValue) / (allItemsCnt - 1);

        return firstValue + inc * index;
    }

    private static String getApproximatedStringValue(
            String firstValue, String lastValue, double index, double allItemsCnt) {
        int middle = (int) (allItemsCnt / 2);
        return (index < middle) ? firstValue : lastValue;
    }

}
