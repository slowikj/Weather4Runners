package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 25.04.2017.
 */

public class AbstractPropositionsFragment extends android.support.v4.app.Fragment {

    protected PropositionsListAdapter propositionsListAdapter;

    protected SimpleDateFormat itemsListDateFormat;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.propositionsListAdapter = getPropositionsListAdapter(this.itemsListDateFormat);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.propositions_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setChildViews(view, this.propositionsListAdapter);
    }

    @NonNull
    private PropositionsListAdapter getPropositionsListAdapter(SimpleDateFormat dateFormat) {
        return new PropositionsListAdapter(
                dateFormat,
                new ListItemOnClickListener(),
                new ListItemOnLongClickListener()
        );
    }

    private void setChildViews(View view, RecyclerView.Adapter adapter) {
        this.recyclerView = getRecyclerView(view, adapter);
    }

    private RecyclerView getRecyclerView(View parentView, RecyclerView.Adapter adapter) {
        RecyclerView rv = (RecyclerView) parentView.findViewById(R.id.rv_propositions);

        rv.setAdapter(adapter);

        rv.setHasFixedSize(false);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rv;
    }

    // Listeners
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
