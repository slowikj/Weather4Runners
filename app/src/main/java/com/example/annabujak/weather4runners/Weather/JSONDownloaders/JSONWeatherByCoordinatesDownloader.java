package com.example.annabujak.weather4runners.Weather.JSONDownloaders;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by slowik on 31.05.2017.
 */

public class JSONWeatherByCoordinatesDownloader extends JSONWeatherDownloader {

    private static final String LONGITUTE_QUERY = "lon";

    private static final String LATITUDE_QUERY = "lat";

    private Double longitude, latitude;

    public JSONWeatherByCoordinatesDownloader(double longitude, double latitude, String language) {
        super(language);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    protected URL buildURL() throws MalformedURLException {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(LONGITUTE_QUERY, this.longitude.toString())
                .appendQueryParameter(LATITUDE_QUERY, this.latitude.toString())
                .appendQueryParameter(APIKEY_QUERY, this.apiKey)
                .appendQueryParameter(LANGUAGE_QUERY, this.language)
                .build();

        return new URL(builtUri.toString());
    }
}
