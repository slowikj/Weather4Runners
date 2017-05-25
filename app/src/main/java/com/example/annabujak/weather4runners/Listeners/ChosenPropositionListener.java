package com.example.annabujak.weather4runners.Listeners;

import com.example.annabujak.weather4runners.Objects.ChosenProposition;

import java.util.List;

/**
 * Created by pawel.bujak on 25.05.2017.
 */

public interface ChosenPropositionListener {
    void onChosenPropositionChanged(List<ChosenProposition> allChosenPropositions);
}
