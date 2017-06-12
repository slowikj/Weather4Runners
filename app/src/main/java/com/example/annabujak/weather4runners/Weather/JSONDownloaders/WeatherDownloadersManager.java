package com.example.annabujak.weather4runners.Weather.JSONDownloaders;

import java.util.HashMap;

/**
 * Created by slowik on 12.06.2017.
 */

public class WeatherDownloadersManager {

    public static final String BY_NAME_DOWNLOADER = "ByName";

    public static final String BY_COORDINATES_DOWNLOADER = "ByCoordinates";

    private String language;

    private HashMap<String, JSONWeatherDownloader> downloaders;

    private JSONWeatherDownloader currentWeatherDownloader;

    public WeatherDownloadersManager(String defaultCity, String defaultCountry,
                                     double defaultLongitude, double defaultLatitude,
                                     String defaultLanguage) {
        this.downloaders = getDownloaders(defaultCity, defaultCountry,
                defaultLongitude, defaultLatitude,
                defaultLanguage);

        this.language = defaultLanguage;

        this.currentWeatherDownloader = this.downloaders.get(BY_COORDINATES_DOWNLOADER);
    }

    public void setLocation(double longitude, double latitude) {
        JSONWeatherByCoordinatesDownloader downloader = (JSONWeatherByCoordinatesDownloader)
                this.downloaders.get(BY_COORDINATES_DOWNLOADER);

        downloader.setLongitude(longitude);
        downloader.setLatitude(latitude);
    }

    public void setLocation(String city, String country) {
        JSONWeatherByNameDownloader downloader = (JSONWeatherByNameDownloader)
                this.downloaders.get(BY_NAME_DOWNLOADER);

        downloader.setCity(city);
        downloader.setCountry(country);
    }

    public void setDownloader(String downloaderKey) {
        this.currentWeatherDownloader = this.downloaders.get(downloaderKey);
    }

    public JSONWeatherDownloader getCurrentWeatherDownloader() {
        return this.currentWeatherDownloader;
    }

    private HashMap<String, JSONWeatherDownloader> getDownloaders(String defaultCity, String defaultCountry,
                                                                  double defaultLongitude, double defaultLatitude,
                                                                  String defaultLanguage) {
        HashMap<String, JSONWeatherDownloader> res = new HashMap<>();

        res.put(BY_NAME_DOWNLOADER,
                new JSONWeatherByNameDownloader(defaultCity, defaultCountry,
                        defaultLanguage));

        res.put(BY_COORDINATES_DOWNLOADER,
                new JSONWeatherByCoordinatesDownloader(defaultLongitude, defaultLatitude,
                        defaultLanguage));

        return res;
    }
}
