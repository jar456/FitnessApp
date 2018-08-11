package com.example.jmo.workoutv2.databases;

        import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.helpers.ExerciseDBHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseExerciseList extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ExerciseList.db";

    public DatabaseExerciseList(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExerciseDBHelper.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExerciseDBHelper.TABLE_NAME);

        onCreate(db);

    }

    public long insertData(String exerciseTarget, String exerciseName, int numSets, int numReps, double weight, String weightType, String additionalInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ExerciseDBHelper.COLUMN_EXERCISETARGET, exerciseTarget);
        values.put(ExerciseDBHelper.COLUMN_EXERCISE, exerciseName);
        values.put(ExerciseDBHelper.COLUMN_NUMSETS, numSets);
        values.put(ExerciseDBHelper.COLUMN_NUMREPS, numReps);
        values.put(ExerciseDBHelper.COLUMN_WEIGHT, weight);
        values.put(ExerciseDBHelper.COLUMN_WEIGHTTYPE, weightType);
        values.put(ExerciseDBHelper.COLUMN_ADDITIONALINFO, additionalInfo);

        long id = db.insert(ExerciseDBHelper.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public ProgramExercise getExercise(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ExerciseDBHelper.COLUMN_EXERCISETARGET,
                ExerciseDBHelper.COLUMN_EXERCISE,
                ExerciseDBHelper.COLUMN_NUMSETS,
                ExerciseDBHelper.COLUMN_NUMREPS,
                ExerciseDBHelper.COLUMN_WEIGHT,
                ExerciseDBHelper.COLUMN_WEIGHTTYPE,
                ExerciseDBHelper.COLUMN_ADDITIONALINFO };

        Cursor cursor = db.query(ExerciseDBHelper.TABLE_NAME, null,
                String.valueOf(id), null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ProgramExercise programExercise = new ProgramExercise(
                cursor.getInt(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_EXERCISE)),
                cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_EXERCISETARGET)),
                cursor.getInt(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_NUMSETS)),
                cursor.getInt(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_NUMREPS)),
                cursor.getDouble(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_WEIGHT)),
                cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_WEIGHTTYPE)),
                cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_ADDITIONALINFO)));

        cursor.close();

        return programExercise;
    }

    public List<ProgramExercise> getAllExercises() {
        List<ProgramExercise> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + ExerciseDBHelper.TABLE_NAME + " ORDER BY " +
                ExerciseDBHelper.COLUMN_EXERCISETARGET + " DESC, " +
                ExerciseDBHelper.COLUMN_EXERCISE + " DESC, " +
                ExerciseDBHelper.COLUMN_NUMSETS + " DESC, " +
                ExerciseDBHelper.COLUMN_NUMREPS+ " DESC, " +
                ExerciseDBHelper.COLUMN_WEIGHT+ " DESC, " +
                ExerciseDBHelper.COLUMN_WEIGHTTYPE+ " DESC, " +
                ExerciseDBHelper.COLUMN_ADDITIONALINFO + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while(cursor.moveToNext()) {
            ProgramExercise programExercise = new ProgramExercise(
                    cursor.getInt(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_EXERCISE)),
                    cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_EXERCISETARGET)),
                    cursor.getInt(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_NUMSETS)),
                    cursor.getInt(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_NUMREPS)),
                    cursor.getDouble(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_WEIGHT)),
                    cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_WEIGHTTYPE)),
                    cursor.getString(cursor.getColumnIndex(ExerciseDBHelper.COLUMN_ADDITIONALINFO)));

            list.add(programExercise);
        }

        cursor.close();
        db.close();

        return list;
    }
}
