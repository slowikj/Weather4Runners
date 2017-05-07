package com.example.annabujak.weather4runners.Weather;

import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by slowik on 02.05.2017.
 */

public class JSONWeatherDownloader {

    private final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private final String CURRENT_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    private final String LOCATION_QUERY = "q";
    private final String LANGUAGE_QUERY = "lang";
    private final String APIKEY_QUERY = "APPID";

    private String location;
    private String language;
    private String apiKey;

    public JSONWeatherDownloader(String location, String language) {
        this.location = location;
        this.apiKey = "f1154294b86bd13039f99eb045c81e12";
        this.language = language;
    }

    public JSONArray getData() throws IOException, MalformedURLException, JSONException {
        String response = getResponseFrom(buildURL());

        return (new JSONObject(response)).getJSONArray("list");
    }

    public void setLocation(String cityName) {
        this.location = cityName;
    }

    private URL buildURL() throws MalformedURLException {
        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(LOCATION_QUERY, this.location)
                .appendQueryParameter(LANGUAGE_QUERY, this.language)
                .appendQueryParameter(APIKEY_QUERY, this.apiKey)
                .build();

        return new URL(builtUri.toString());
    }

    private String getResponseFrom(URL url) throws IOException {
        HttpURLConnection connection = getHttpURLConnection(url);
        connection.connect();

        try {
            return getReadContentFrom(connection.getInputStream());
        } finally {
            connection.disconnect();
        }
    }

    @NonNull
    private HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        return connection;
    }

    private String getReadContentFrom(InputStream inputStream) throws IOException {
        StringBuilder res = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;

            while ((line = reader.readLine()) != null) {
                res.append(line);
            }

            return res.toString();
        } finally {
            reader.close();
        }
    }
}
