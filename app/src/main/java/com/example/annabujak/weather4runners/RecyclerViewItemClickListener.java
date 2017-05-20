package com.example.annabujak.weather4runners;

import android.view.View;

/**
 * Created by slowik on 20.05.2017.
 */

public interface RecyclerViewItemClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
