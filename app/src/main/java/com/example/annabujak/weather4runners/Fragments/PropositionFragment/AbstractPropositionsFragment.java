package com.example.annabujak.weather4runners.Fragments.PropositionFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.annabujak.weather4runners.Fragments.PropositionFragment.Command.Command;
import com.example.annabujak.weather4runners.Listeners.CustomRecyclerViewOnTouchListener;
import com.example.annabujak.weather4runners.Listeners.PropositionClickedListener;
import com.example.annabujak.weather4runners.Objects.ChosenHour;
import com.example.annabujak.weather4runners.Objects.WeatherInfo;
import com.example.annabujak.weather4runners.R;
import com.example.annabujak.weather4runners.Listeners.RecyclerViewItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by slowik on 25.04.2017.
 */

public abstract class AbstractPropositionsFragment extends android.support.v4.app.Fragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected PropositionsListAdapter propositionsListAdapter;

    protected ArrayList<WeatherInfo> propositions;

    protected SimpleDateFormat itemsListDateFormat;

    protected SharedPreferencesHelper sharedPreferencesHelper;

    private RecyclerView recyclerView;

    private PropositionClickedListener propositionClickedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.propositionsListAdapter = getPropositionsListAdapter(this.itemsListDateFormat);
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_propositions, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.sharedPreferencesHelper = SharedPreferencesHelper.create(getActivity());

        attachPropositionsClickedListener((PropositionClickedListener) context);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.sharedPreferencesHelper
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        this.sharedPreferencesHelper
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_propositions_number:
                Context context = getContext();
                EditText editText = new EditText(context);
                editText.setText(((Integer)getNumberOfItemsToShowOrDefault()).toString());
                getChangingNumberDialog(context, editText, getChangeIntPrefCommand()).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    private PropositionsListAdapter getPropositionsListAdapter(SimpleDateFormat dateFormat) {
        return new PropositionsListAdapter(dateFormat);
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

        rv.addOnItemTouchListener(
                new CustomRecyclerViewOnTouchListener(
                        getContext(),
                        rv,
                        new PropositionItemClickedListener()));

        return rv;
    }

    private AlertDialog.Builder getChangingNumberDialog(
            Context context, final EditText edit, final Command action) {
        return new AlertDialog.Builder(context)
                .setMessage("Set the number of items to show")
                .setView(edit)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Integer value = Integer.parseInt(edit.getText().toString());

                                    action.execute(value);
                                } catch(Exception e) {
                                    Toast.makeText(getContext(),
                                            getResources().getString(R.string.parsing_input_error_message),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }

    protected ArrayList<WeatherInfo> getFirstNPropositions(int n, ArrayList<WeatherInfo> propositions) {
        ArrayList<WeatherInfo> res = new ArrayList<>();
        int cnt = Math.min(n, propositions.size());
        for(int i = 0; i < cnt; ++i) {
            res.add(propositions.get(i));
        }

        return res;
    }

    protected void updatePropositionsListView() {
        this.propositionsListAdapter.setPropositionsList(
                getFirstNPropositions(getNumberOfItemsToShowOrDefault(),
                        this.propositions)
        );
    }

    protected abstract int getNumberOfItemsToShowOrDefault();

    protected abstract Command<Integer> getChangeIntPrefCommand();

    protected abstract ChosenHour getChosenProposition(long date);

    private void attachPropositionsClickedListener(PropositionClickedListener context) {
        try {
            this.propositionClickedListener = context;
        } catch(ClassCastException e) {
            throw new ClassCastException("attached element must implement PropositionsClickedListener");
        }
    }

    // Listeners
    private class PropositionItemClickedListener implements RecyclerViewItemClickListener{


        @Override
        public void onClick(View view, int position) {
            PropositionsListAdapter.PropositionsListViewHolder holder = getHolder(position);
            holder.getCheckbox().setChecked(!holder.getCheckbox().isChecked());
            Integer pos = recyclerView.getChildAdapterPosition(view);

            // TODO: propositionClickedListener.on...(getChosenProposition(holder.getDate())

            Toast.makeText(getContext(), "you clicked " + pos.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLongClick(View view, int position) {
            Toast.makeText(getContext(), "you LONG clicked", Toast.LENGTH_SHORT).show();
            // TODO: handle FB notification
        }

        protected PropositionsListAdapter.PropositionsListViewHolder getHolder(int position) {
            return (PropositionsListAdapter.PropositionsListViewHolder)recyclerView
                    .findViewHolderForAdapterPosition(position);
        }
    }
}
