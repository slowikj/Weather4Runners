package com.example.annabujak.weather4runners.Weather.JSONDownloaders;

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

public abstract class JSONWeatherDownloader {

    protected final String FORECAST_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";

    protected final String LANGUAGE_QUERY = "lang";
    protected final String APIKEY_QUERY = "APPID";

    protected String language;
    protected String apiKey;

    public JSONWeatherDownloader(String language) {
        this.apiKey = "f1154294b86bd13039f99eb045c81e12";
        this.language = language;
    }

    public JSONArray getData() throws IOException, MalformedURLException, JSONException {
        String response = getResponseFrom(buildURL());

        return (new JSONObject(response)).getJSONArray("list");
    }

    protected abstract URL buildURL() throws MalformedURLException ;

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
