package com.example.annabujak.weather4runners.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.example.annabujak.weather4runners.Listeners.CityCountryLocationSetListener;
import com.example.annabujak.weather4runners.Listeners.CurrentLocationSetListener;
import com.example.annabujak.weather4runners.R;

/**
 * Created by slowik on 12.06.2017.
 */

public class LocationTypeDialog extends DialogFragment {

    private CurrentLocationSetListener currentLocationSetListener;

    private CityCountryLocationSetListener cityCountryLocationSetListener;

    public static LocationTypeDialog create(CurrentLocationSetListener currentLocationSetListener,
                                             CityCountryLocationSetListener cityCountryLocationSetListener) {
        LocationTypeDialog dialog = new LocationTypeDialog();
        dialog.currentLocationSetListener = currentLocationSetListener;
        dialog.cityCountryLocationSetListener = cityCountryLocationSetListener;

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.choose_location_type_message))
                .setItems(getResources().getStringArray(R.array.location_types),
                        getOnLocationTypeClickListener());

        return builder.create();
    }

    private DialogInterface.OnClickListener getOnLocationTypeClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0: // set my current location
                        currentLocationSetListener.setCurrentLocation();
                        break;
                    case 1: // choose location
                        ChooseLocationDialog chooseLocationDialog = ChooseLocationDialog.create(
                                cityCountryLocationSetListener
                        );

                        chooseLocationDialog.show(getFragmentManager(), "chooseLocationDialog");
                }
            }
        };
    }
}
