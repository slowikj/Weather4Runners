package com.example.annabujak.weather4runners.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.annabujak.weather4runners.R;

/**
 * Created by slowik on 06.06.2017.
 */

public class CreditsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.credits_fragment_layout, container, false);
    }
}
