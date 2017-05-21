package com.example.annabujak.weather4runners.Fragments.ImportantConditionsFragment;

/**
 * Created by slowik on 21.05.2017.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
