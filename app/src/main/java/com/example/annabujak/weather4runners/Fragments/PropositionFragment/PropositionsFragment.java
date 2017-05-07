package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.R;
import com.example.annabujak.weather4runners.DataActivityInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 25.04.2017.
 */

public class PropositionsFragment extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;

    private PropositionsListAdapter propositionsListAdapter;

    private View fullView;

    private DataActivityInterface attachedActivity;

    private SimpleDateFormat itemsListDateFormat;

    public static PropositionsFragment getDailyPropositionsFragment() {
        return getPropositionsFragment(new SimpleDateFormat("h:mm a"));
    }

    public static PropositionsFragment getWeeklyPropositionsFragment() {
        return getPropositionsFragment(new SimpleDateFormat("dd.MM.yy"));
    }

    private static PropositionsFragment getPropositionsFragment(SimpleDateFormat dateFormat) {
        PropositionsFragment res = new PropositionsFragment();
        res.setDateFormat(dateFormat); // TODO : improve

        return res;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.attachedActivity = (DataActivityInterface) getActivity();
        } catch(ClassCastException e) {
            throw new ClassCastException("being attached activity must implement DataActivityInterface");
        }
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.itemsListDateFormat = dateFormat;
    }

    public void setPropositions(ArrayList<WeatherInfo> propositions) {
        this.propositionsListAdapter.setPropositionsList(propositions);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(this.fullView == null) {
            this.fullView = getFullView(inflater, container);
            setChildViews();
        }

        return this.fullView;
    }

    @Override
    public void onResume() {

        //TODO
        // change the title of "activity" to "propositions..."
        super.onResume();
    }

    @Override
    public void onPause() {

        // TODO ?
        super.onPause();
    }

    private View getFullView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.propositions_layout, container, false);
    }

    private void setChildViews() {
        this.recyclerView = getRecyclerView(this.fullView);
        this.propositionsListAdapter = (PropositionsListAdapter)this.recyclerView.getAdapter();
    }

    private RecyclerView getRecyclerView(View parentView) {
        RecyclerView rv = (RecyclerView) parentView.findViewById(R.id.rv_propositions);

        rv.setAdapter(new PropositionsListAdapter(
                this.itemsListDateFormat,
                new ListItemOnClickListener(),
                new ListItemOnLongClickListener()
        ));

        rv.setHasFixedSize(false);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rv;
    }

    private class ListItemOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO
            // OnClick means that we want to go running this time
            // check checkbox and send info to data base about the user's declaration

            PropositionsListAdapter.PropositionsListViewHolder holder =
                    (PropositionsListAdapter.PropositionsListViewHolder)v.getTag();

            holder.mCheckbox.setChecked(!holder.mCheckbox.isChecked());

            Toast.makeText(getContext(), "you clicked on item", Toast.LENGTH_SHORT).show();
        }
    }

    private class ListItemOnLongClickListener implements  View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {

            // TODO
            Toast.makeText(getContext(), "you long clicked on item", Toast.LENGTH_SHORT).show();

            return true;
        }
    }
}
