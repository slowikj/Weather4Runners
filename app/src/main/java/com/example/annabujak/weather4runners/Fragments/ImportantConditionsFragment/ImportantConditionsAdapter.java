package com.example.annabujak.weather4runners.Fragments.ImportantConditionsFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.annabujak.weather4runners.Enum.WeatherConditionsNames;
import com.example.annabujak.weather4runners.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by slowik on 21.05.2017.
 */

public class ImportantConditionsAdapter extends RecyclerView.Adapter<ImportantConditionsAdapter.ItemViewHolder>
    implements ItemTouchHelperAdapter {

    private ArrayList<WeatherConditionsNames> weatherConditionsNames;

    public ImportantConditionsAdapter(ArrayList<WeatherConditionsNames> weatherConditionsNames) {
        this.weatherConditionsNames = weatherConditionsNames;
    }

    public void setData(ArrayList<WeatherConditionsNames> weatherConditionsNames) {
        this.weatherConditionsNames = weatherConditionsNames;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.important_condition_item_layout, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mTextView.setText(this.weatherConditionsNames.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return this.weatherConditionsNames.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if(fromPosition < toPosition) {
            for(int i = fromPosition; i < toPosition; ++i) {
                Collections.swap(this.weatherConditionsNames, i, i + 1);
            }
        } else {
            for(int i = fromPosition; i > toPosition; --i) {
                Collections.swap(this.weatherConditionsNames, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        this.weatherConditionsNames.remove(position);
        notifyItemRemoved(position);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);

            this.mTextView = (TextView)itemView.findViewById(R.id.item_condition_name);
        }

        public TextView getmTextView() {
            return mTextView;
        }
    }
}
