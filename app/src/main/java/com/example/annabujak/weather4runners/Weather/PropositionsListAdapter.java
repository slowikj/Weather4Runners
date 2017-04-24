package com.example.annabujak.weather4runners.Weather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.annabujak.weather4runners.PropositionsChangedInterface;
import com.example.annabujak.weather4runners.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by slowik on 24.04.2017.
 */

public class PropositionsListAdapter extends RecyclerView.Adapter<PropositionsListAdapter.PropositionsListViewHolder>
    implements PropositionsChangedInterface {

    private SimpleDateFormat dateFormat;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    private ArrayList<WeatherProposition> propositionsList;

    public PropositionsListAdapter(SimpleDateFormat dateFormat,
                                   View.OnClickListener onClickListener,
                                   View.OnLongClickListener onLongClickListener) {

        this.dateFormat = dateFormat;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public PropositionsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_layout, parent, false);

        return new PropositionsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropositionsListViewHolder holder, int position) {
        holder.mCheckbox.setChecked(propositionsList.get(position).isChecked());
        holder.name.setText(propositionsList.get(position).getDateInfo(dateFormat));
        holder.shortDescription.setText(propositionsList.get(position).getDateInfo(dateFormat));

        holder.SetTags();
    }

    @Override
    public int getItemCount() {
        return this.propositionsList.size();
    }

    @Override
    public void OnPropositionsChanged(List<WeatherProposition> propositions) {
        this.propositionsList = new ArrayList<>(propositions);

        notifyDataSetChanged();
    }

    class PropositionsListViewHolder extends RecyclerView.ViewHolder {

        public CheckBox mCheckbox;
        public TextView name;
        public TextView shortDescription;

        public PropositionsListViewHolder(View itemView) {
            super(itemView);

            setViewReferences(itemView);
            setListeners();
        }

        public void SetTags()
        {
            this.mCheckbox.setTag(this);
            this.name.setTag(this);
            this.shortDescription.setTag(this);
        }

        private void setViewReferences(View itemView) {
            this.mCheckbox = (CheckBox)itemView.findViewById(R.id.item_checked);
            this.name = (TextView)itemView.findViewById(R.id.item_name);
            this.shortDescription = (TextView)itemView.findViewById(R.id.item_short_description);
        }

        private void setListeners() {
            setOnClickListeners();
            setOnLongClickListeners();
        }

        private void setOnClickListeners() {
            this.mCheckbox.setOnClickListener(onClickListener);
            this.name.setOnClickListener(onClickListener);
            this.shortDescription.setOnClickListener(onClickListener);
        }

        private void setOnLongClickListeners() {
            this.mCheckbox.setOnLongClickListener(onLongClickListener);
            this.name.setOnLongClickListener(onLongClickListener);
            this.shortDescription.setOnLongClickListener(onLongClickListener);
        }
    }
}
