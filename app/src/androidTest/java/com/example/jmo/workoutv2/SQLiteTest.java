package com.example.jmo.workoutv2;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.databases.DatabaseExerciseListHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class SQLiteTest {

    private DatabaseExerciseListHelper database;
    private static final String tag = "TAG_SQLITETEST";

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
    public void shouldAddExercise() throws Exception {
        ProgramExercise exercise = new ProgramExercise("Chest","Bench", 3, 10, 255.5, "kg", "Squatting!" );
        ProgramExercise exercise1 = new ProgramExercise("Best","Bench", 3, 10, 255.5, "kg", "Squatting!" );
        database.insertData(exercise);
        database.insertData(exercise1);

        List<ProgramExercise> list = database.getAllExercises();
        assertTrue(list.size() == 2);
        Log.d("Test","ListSize = " + Integer.toString(list.size()));
        for (int i = 0; i < list.size(); i++) {
            Log.d("Test", i + " ID = " + list.get(i).getId());
        }

        assertTrue(list.get(0).getExerciseTarget().equals("Chest"));
        assertTrue(list.get(0).getExerciseName().equals("Bench"));
        assertTrue(list.get(1).getExerciseTarget().equals("Best"));
        assertTrue(list.get(0).getId() == 1);
        assertTrue(list.get(1).getId() == 2);



        ProgramExercise test = database.getExercise(2);
        Log.d("Test", "WHAT = " + test.getExerciseTarget());
        assertTrue(test.getExerciseTarget().equals("Best"));
    }

    @Test
    public void deleteExercise() throws Exception {
        ProgramExercise exercise = new ProgramExercise("Chest","Bench", 3, 10, 255.5, "kg", "Squatting!" );
        ProgramExercise exercise1 = new ProgramExercise("Best","Bench", 3, 10, 255.5, "kg", "Squatting!" );

        database.insertData(exercise);
        database.insertData(exercise1);

        database.getExercise(1);

        database.deleteExercise(database.getExercise(1));

        Log.d(tag, database.getExerciseCount() + " - Count");

        ProgramExercise test = database.getExercise(2);
        assertTrue(test.getExerciseTarget().equals("Best"));

        List<ProgramExercise> list = database.getAllExercises();
        Log.d(tag, "Size" + list   .size());

        for (int i = 0; i < list.size(); i++) {
            Log.d(tag, i + " ID = " + list.get(i).getId());
        }
    }

    @Test
    public void updateExercise() throws Exception {
        ProgramExercise exercise = new ProgramExercise("Chest","Bench", 3, 10, 255.5, "kg", "Squatting!" );
        ProgramExercise exercise1 = new ProgramExercise("FFFF","ASd", 3, 10, 255.5, "kg", "WWWW!" );

        database.insertData(exercise);
        database.insertData(exercise1);

        ProgramExercise ll = database.getExercise(2);
        ll.setAll("Best","Bench", 3, 10, 255.5, "kg", "Squatting!" );
        database.updateExercise(ll);

        assertTrue(database.getExercise(2).getExerciseTarget().equals("Best"));

    }
}