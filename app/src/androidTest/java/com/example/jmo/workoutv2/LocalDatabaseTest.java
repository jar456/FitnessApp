package com.example.jmo.workoutv2;

import android.content.Context;
import android.database.SQLException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.databases.DatabaseExerciseListHelper;
import com.example.jmo.workoutv2.databases.DatabaseHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocalDatabaseTest {

    private DatabaseExerciseListHelper database;
    private static final String TAG = "TAG_LOCALDATABASETEST";

    @Before
    public void setUp() throws Exception {
        InstrumentationRegistry.getTargetContext().deleteDatabase(DatabaseExerciseListHelper.DB_NAME);
        database = new DatabaseExerciseListHelper(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void getExerciseList() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();

        DatabaseHelper db = new DatabaseHelper(context);
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

        List<ProgramExercise> list = db.getAllExercises();
        for (int i = 0; i < list.size(); i++) {
            Log.d(TAG, "getExerciseList: " + list.get(i).getId());
        }
    }
}