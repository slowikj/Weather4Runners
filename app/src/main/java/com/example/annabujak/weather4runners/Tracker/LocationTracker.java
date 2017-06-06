package com.example.annabujak.weather4runners.Tracker;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.widget.Toast;

import com.example.annabujak.weather4runners.Dialogs.DialogFactory;
import com.example.annabujak.weather4runners.R;

import java.util.Calendar;
import java.util.List;

public class LocationTracker implements LocationListener {

    private final Context context;

    private double latitude;

    private double longitude;

    public LocationTracker(Context context,
                           double longitude,
                           double latitude) {
        this.context = context;
        this.setLocation(longitude,
                latitude);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        showLocationUpdatedMessage();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        DialogFactory.getSettingsAlertDialog(this.context)
                .show();
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void updateLocation(LocationManager locationManager) {
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            onProviderDisabled(null);
        }

        final long TIME_DIFF = 3;

        Location newLocation = getLastKnownLocation(locationManager);
        if(newLocation != null && newLocation.getTime() > Calendar.getInstance().getTimeInMillis() - TIME_DIFF) {
            this.setLocation(newLocation.getLongitude(),
                    newLocation.getLatitude());

            showLocationUpdatedMessage();
        } else {
            //noinspection MissingPermission
            locationManager.requestSingleUpdate(
                    LocationTracker.getDefaultCriteria(),
                    this,
                    null);
        }
    }

    public static Criteria getDefaultCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        return criteria;
    }

    @SuppressWarnings("MissingPermission")
    private Location getLastKnownLocation(LocationManager locationManager) {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private void setLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private void showLocationUpdatedMessage() {
        Toast.makeText(this.context,
                this.context.getResources().getString(R.string.location_updated_message),
                Toast.LENGTH_LONG)
                .show();

        Log.i("LOCATION", "Location set to " + String.format(" %f, %f", longitude, latitude));
    }
}
