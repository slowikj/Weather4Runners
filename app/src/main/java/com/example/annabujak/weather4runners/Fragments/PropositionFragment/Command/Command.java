package com.example.annabujak.weather4runners.Fragments.PropositionFragment.Command;

/**
 * Created by slowik on 21.05.2017.
 */

public interface Command<T> {

    void execute(T value);
}
