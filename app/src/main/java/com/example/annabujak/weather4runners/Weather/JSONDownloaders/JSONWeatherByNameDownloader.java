package com.example.annabujak.weather4runners.Weather.JSONDownloaders;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by slowik on 31.05.2017.
 */

public class JSONWeatherByNameDownloader extends JSONWeatherDownloader {

    private final String LOCATION_QUERY = "q";

    private String city, country;

    public JSONWeatherByNameDownloader(String city, String country, String language) {
        super (language);

        this.city = city;
        this.country = country;
    }

    @Override
    protected URL buildURL() throws MalformedURLException {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(LOCATION_QUERY, getLocation())
                .appendQueryParameter(LANGUAGE_QUERY, this.language)
                .appendQueryParameter(APIKEY_QUERY, this.apiKey)
                .build();

        return new URL(builtUri.toString());
    }

    private String getLocation() {
        return city + "," + country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
