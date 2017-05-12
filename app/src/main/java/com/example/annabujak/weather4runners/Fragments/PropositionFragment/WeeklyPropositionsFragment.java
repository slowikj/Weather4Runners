package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.os.Bundle;

import java.text.SimpleDateFormat;

/**
 * Created by slowik on 12.05.2017.
 */

public class WeeklyPropositionsFragment extends AbstractPropositionsFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.itemsListDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
        super.onCreate(savedInstanceState);
    }
}
