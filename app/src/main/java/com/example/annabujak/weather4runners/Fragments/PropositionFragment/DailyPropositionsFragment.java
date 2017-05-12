package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.os.Bundle;

import java.text.SimpleDateFormat;

/**
 * Created by slowik on 12.05.2017.
 */

public class DailyPropositionsFragment extends AbstractPropositionsFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.itemsListDateFormat = new SimpleDateFormat("E, HH:mm");
        super.onCreate(savedInstanceState);
    }
}
