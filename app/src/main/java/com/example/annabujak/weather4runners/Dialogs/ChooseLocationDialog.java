package com.example.annabujak.weather4runners.Dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.annabujak.weather4runners.Listeners.CityCountryLocationSetListener;
import com.example.annabujak.weather4runners.R;

/**
 * Created by slowik on 12.06.2017.
 */

public class ChooseLocationDialog extends DialogFragment {

    private CityCountryLocationSetListener cityCountryLocationSetListener;

    public static ChooseLocationDialog create(CityCountryLocationSetListener listener) {
        ChooseLocationDialog chooseLocationDialog = new ChooseLocationDialog();
        chooseLocationDialog.cityCountryLocationSetListener = listener;

        return chooseLocationDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.choose_location_dialog_layout, null);

        builder.setTitle(getResources().getString(R.string.choose_city_country_message))
                .setView(view)
                .setPositiveButton("OK", getOnClickPositiveButtonListener(view));

        return builder.create();
    }

    private DialogInterface.OnClickListener getOnClickPositiveButtonListener(final View view) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String city = ((EditText)view.findViewById(R.id.city_dialog_input)).getText().toString();
                String country = ((EditText)view.findViewById(R.id.country_dialog_input)).getText().toString();

                if(!isValid(city) || !isValid(country)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.incorrect_input_message),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    cityCountryLocationSetListener.setCityCountryLocation(city, country);
                }
            }
        };
    }

    private boolean isValid(String name) {
        return name.matches("[a-zA-Z]+");
    }
}
