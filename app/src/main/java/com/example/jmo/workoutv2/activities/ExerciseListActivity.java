package com.example.jmo.workoutv2.activities;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.adapters.ExerciseListAdapter;
import com.example.jmo.workoutv2.adapters.TargetSelectAdapter;
import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.databases.DatabaseHelper;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseListActivity extends AppCompatActivity implements ExerciseListAdapter.ExerciseListListener{

    MaterialSearchView searchView;
    RecyclerView targetButtonsRecycler;
    TargetSelectAdapter adapterTargetButtons;
    List<String> targetList;
    List<ProgramExercise> exerciseList;

    private DatabaseHelper db;
    RecyclerView exerciseListRecycler;
    ExerciseListAdapter adapterExerciseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exerciselist);

        Toolbar toolbar = findViewById(R.id.toolbar_exerciseList);
        setSupportActionBar(toolbar);

        targetList = new ArrayList<>();
        CreateRecyclerView();

        db = new DatabaseHelper(getApplicationContext());
        initiateDatabase();
        exerciseList = db.getAllExercises();
        adapterExerciseList = new ExerciseListAdapter(getApplicationContext(), exerciseList, this);
        exerciseListRecycler.setAdapter(adapterExerciseList);

        setupSearch();

    }

    private void setupSearch() {
        searchView = findViewById(R.id.searchView_exerciseList);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processQuery(newText);
                return true;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() { }

            @Override
            public void onSearchViewClosed() {
                adapterExerciseList.setList(exerciseList);
            }
        });
    }

    private void processQuery(String query) {
        List<ProgramExercise> result = new ArrayList<>();

        for (int i = 0; i < exerciseList.size(); i++) {
            String exerciseName = exerciseList.get(i).getExerciseName();

            if (exerciseName.toLowerCase().contains(query.toLowerCase())) {
                result.add(exerciseList.get(i));
            }
        }
        adapterExerciseList.setList(result);
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

    private void CreateRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        targetButtonsRecycler = findViewById(R.id.recyclerView_exerciseList_targertSelect);
        targetButtonsRecycler.setLayoutManager(layoutManager);
        adapterTargetButtons = new TargetSelectAdapter(targetList);
        targetButtonsRecycler.setAdapter(adapterTargetButtons);
        PrepareRecyclerView();

        exerciseListRecycler = findViewById(R.id.recyclerView_exerciseList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        exerciseListRecycler.setLayoutManager(mLayoutManager);
        exerciseListRecycler.setItemAnimator(new DefaultItemAnimator());
        exerciseListRecycler.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    private void PrepareRecyclerView() {
        targetList.add(getResources().getString(R.string.exerciseSelect_all));
        targetList.add(getResources().getString(R.string.exerciseSelect_chest));
        targetList.add(getResources().getString(R.string.exerciseSelect_back));
        targetList.add(getResources().getString(R.string.exerciseSelect_legs));
        targetList.add(getResources().getString(R.string.exerciseSelect_shoulders));
        targetList.add(getResources().getString(R.string.exerciseSelect_arms));
        targetList.add(getResources().getString(R.string.exerciseSelect_core));
        targetList.add(getResources().getString(R.string.exerciseSelect_cardio));

        adapterTargetButtons.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setTitle("Exercise List");
    }

    @Override
    public void onExerciseClick(View view, int position, ProgramExercise exercise) {
        Snackbar.make(view, position + "", Snackbar.LENGTH_SHORT).show();
    }
}
