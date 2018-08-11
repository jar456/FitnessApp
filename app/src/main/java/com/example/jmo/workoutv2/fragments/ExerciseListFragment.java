package com.example.jmo.workoutv2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jmo.workoutv2.R;

public class ExerciseListFragment extends Fragment {

    private static final String tag = "ExerciseListFragment";
    RecyclerView weekButtonsRecycler;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exerciselist, container, false);

        spinner = v.findViewById(R.id.spinner_exerciseList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.exerciseTypeItems, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        weekButtonsRecycler = getActivity().findViewById(R.id.recyclerView_weekButton);
        weekButtonsRecycler.setVisibility(View.INVISIBLE);

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } catch (NullPointerException e) {
            return v; // Add Error Dialog
        }

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        } catch (NullPointerException e) {
            Log.e(tag, " No Action Bar");
        }

        weekButtonsRecycler.setVisibility(View.VISIBLE);
    }
}
