package com.example.annabujak.weather4runners.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.example.annabujak.weather4runners.Listeners.CityCountryLocationSetListener;
import com.example.annabujak.weather4runners.Listeners.CurrentLocationSetListener;
import com.example.annabujak.weather4runners.R;

/**
 * Created by slowik on 06.06.2017.
 */

public class DialogFactory {

    public static AlertDialog getSettingsAlertDialog(final Context context){
        return new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.gps_dialog_title))
                .setMessage(context.getResources().getString(R.string.gps_dialog_message))
                .setPositiveButton(context.getResources().getString(R.string.gps_dialog_positive),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }).setNegativeButton(context.getResources().getString(R.string.gps_dialog_negative),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
    }

    public static AlertDialog getWeatherUpdatingAlertDialog(Context context,
                                                     DialogInterface.OnClickListener onPositiveClickListener) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.update_dialog_title)
                .setMessage(R.string.update_dialog_message)
                .setPositiveButton(R.string.update_dialog_positive, onPositiveClickListener)
                .setNegativeButton(R.string.update_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
    }

    public static LocationTypeDialog getLocationTypeDialog(CurrentLocationSetListener currentLocationSetListener,
                                               CityCountryLocationSetListener cityCountryLocationSetListener) {
        return LocationTypeDialog.create(currentLocationSetListener,
                cityCountryLocationSetListener);
    }
}
