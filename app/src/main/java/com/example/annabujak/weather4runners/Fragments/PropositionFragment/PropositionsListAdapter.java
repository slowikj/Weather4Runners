package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 24.04.2017.
 */

public class PropositionsListAdapter extends RecyclerView.Adapter<PropositionsListAdapter.PropositionsListViewHolder> {

    private SimpleDateFormat dateFormat;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    private ArrayList<WeatherInfo> propositionsList;

    public PropositionsListAdapter(SimpleDateFormat dateFormat,
                                   View.OnClickListener onClickListener,
                                   View.OnLongClickListener onLongClickListener) {

        this.dateFormat = dateFormat;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;
        this.propositionsList = new ArrayList<>();
    }

    @Override
    public PropositionsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.proposition_item_list_layout, parent, false);

        return new PropositionsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PropositionsListViewHolder holder, int position) {
        WeatherInfo elem = propositionsList.get(position);
        holder.mCheckbox.setChecked(elem.getIsChecked());

        holder.name.setText(
                String.format("%s; %s C, %s; %s mm; %s km/h",
                        elem.getFormattedDate(this.dateFormat).toString(),
                        (new Integer(elem.getTemperature())).toString(),
                        (new Integer(elem.getHumidity())).toString(),
                        (new Double(elem.getPrecipitation())).toString(),
                        (new Double(elem.getWindSpeed())).toString()));

        holder.shortDescription.setText(elem.getDescription());

        holder.SetTags();
    }

    @Override
    public int getItemCount() {
        return this.propositionsList.size();
    }

    public void setPropositionsList(ArrayList<WeatherInfo> propositions) {
        this.propositionsList = propositions;
        notifyDataSetChanged();
    }

    public class PropositionsListViewHolder extends RecyclerView.ViewHolder {

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
