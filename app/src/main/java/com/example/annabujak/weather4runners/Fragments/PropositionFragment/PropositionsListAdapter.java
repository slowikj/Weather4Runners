package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.annabujak.weather4runners.Objects.PropositionsList;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

/**
 * Created by slowik on 24.04.2017.
 */

public class PropositionsListAdapter extends RecyclerView.Adapter<PropositionsListAdapter.PropositionsListViewHolder> {

    private SimpleDateFormat dateFormat;

    private PropositionsList propositionsList;

    public PropositionsListAdapter(SimpleDateFormat dateFormat) {

        this.dateFormat = dateFormat;
        this.propositionsList = new PropositionsList();
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
        holder.getCheckbox().setChecked(elem.getIsChecked());

        holder.name.setText(elem.getFormattedDate(this.dateFormat).toString());

        holder.conditionsDescription.setText(elem.getConditionsSummary());

        holder.shortDescription.setText(elem.getDescription());
        holder.date = elem.getDate();

        holder.mImageView.setVisibility(View.VISIBLE);
        holder.mImageView.setImageResource(getIconId("weather_" + elem.getIconName()));
    }

    @Override
    public int getItemCount() {
        return this.propositionsList.size();
    }

    public void setPropositionsList(PropositionsList propositions) {
        this.propositionsList = propositions;
        notifyDataSetChanged();
    }

    public class PropositionsListViewHolder extends RecyclerView.ViewHolder {

        private CheckBox mCheckbox;

        private ImageView mImageView;

        private TextView name;

        private TextView conditionsDescription;

        private TextView shortDescription;

        private long date;

        public PropositionsListViewHolder(View itemView) {
            super(itemView);

            setViewReferences(itemView);
        }

        private void setViewReferences(View itemView) {
            this.mCheckbox = (CheckBox)itemView.findViewById(R.id.item_proposition_checked);
            this.mImageView = (ImageView)itemView.findViewById(R.id.item_proposition_weather_icon);
            this.name = (TextView)itemView.findViewById(R.id.item_proposition_name);
            this.conditionsDescription = (TextView) itemView.findViewById(R.id.item_proposition_conditions);
            this.shortDescription = (TextView)itemView.findViewById(R.id.item_proposition_short_description);
        }

        public CheckBox getCheckbox() {
            return mCheckbox;
        }

        public TextView getName() {
            return name;
        }

        public long getDate() { return this.date; }

        public void setDate(long date) { this.date = date; }
    }

    private int getIconId(String drawableName) {
        try {
            Class res = R.drawable.class;
            Field field = res.getField(drawableName);
            return field.getInt(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
