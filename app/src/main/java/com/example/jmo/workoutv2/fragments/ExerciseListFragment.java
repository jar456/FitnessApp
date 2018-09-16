package com.example.jmo.workoutv2.fragments;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.adapters.ExerciseListAdapter;
import com.example.jmo.workoutv2.data.ProgramDay;
import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.databases.DatabaseHelper;

import java.io.IOException;

public class ExerciseListFragment extends Fragment implements ExerciseListAdapter.ExerciseListListener{

    private static final String tag = "ExerciseListFragment";
    private static final String PROGRAMDAY_KEY = "programDay_key";

    private RecyclerView weekButtonsRecycler, exerciseListRecycler;
    private Spinner spinner;
    private DatabaseHelper db;
    private ExerciseListAdapter adapter;
    private ProgramDay programDay;
    private android.support.v7.widget.SearchView searchView;


    public static ExerciseListFragment newInstance(ProgramDay programDay) {

        Bundle args = new Bundle();
        args.putParcelable(PROGRAMDAY_KEY,programDay);

        ExerciseListFragment fragment = new ExerciseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_exerciselist, container, false);

        try {
            programDay = getArguments().getParcelable(PROGRAMDAY_KEY);
        } catch (NullPointerException e) {
            Log.e(tag, "NO PARCELABLE FOUND");
        }

        exerciseListRecycler = v.findViewById(R.id.rV_exerciseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        exerciseListRecycler.setLayoutManager(mLayoutManager);
        exerciseListRecycler.setItemAnimator(new DefaultItemAnimator());

        exerciseListRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        db = new DatabaseHelper(getActivity());
        initiateDatabase();

        searchView = v.findViewById(R.id.searchView_exerciseList);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new ExerciseListAdapter(getActivity(), db.getAllExercises(), this);
        exerciseListRecycler.setAdapter(adapter);
    }

    private void initiateDatabase() {
        try {
            db.createDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            db.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    @Override
    public void onExerciseClick(View view, int position, ProgramExercise exercise) {
        Snackbar.make(view, "pos " + position, Snackbar.LENGTH_SHORT).show();

        ExerciseEditFragment exerciseEditFragment = ExerciseEditFragment.newInstance(programDay, true, exercise);
        getFragmentManager().beginTransaction().replace(R.id.CoordinatorLayout_programEdit, exerciseEditFragment).addToBackStack(null).commit();
    }
}
