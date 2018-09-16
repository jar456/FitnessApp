package com.example.jmo.workoutv2.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jmo.workoutv2.contracts.ExerciseContract;
import com.example.jmo.workoutv2.data.ProgramExercise;

import java.util.ArrayList;
import java.util.List;

public class DatabaseExerciseListHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "ExerciseListActivity.db";

    public DatabaseExerciseListHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExerciseContract.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExerciseContract.TABLE_NAME);

        onCreate(db);

    }

    public long insertData(ProgramExercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExerciseContract.COLUMN_EXERCISETARGET, exercise.getExerciseTarget());
        values.put(ExerciseContract.COLUMN_EXERCISE, exercise.getExerciseName());
        values.put(ExerciseContract.COLUMN_NUMSETS, exercise.getNumSets());
        values.put(ExerciseContract.COLUMN_NUMREPS, exercise.getNumReps());
        values.put(ExerciseContract.COLUMN_WEIGHT, exercise.getWeight());
        values.put(ExerciseContract.COLUMN_WEIGHTTYPE, exercise.getWeightType());
        values.put(ExerciseContract.COLUMN_ADDITIONALINFO, exercise.getAdditionalInfo());

        long id = db.insert(ExerciseContract.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public ProgramExercise getExercise(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ExerciseContract.COLUMN_ID,
                ExerciseContract.COLUMN_EXERCISETARGET,
                ExerciseContract.COLUMN_EXERCISE,
                ExerciseContract.COLUMN_NUMSETS,
                ExerciseContract.COLUMN_NUMREPS,
                ExerciseContract.COLUMN_WEIGHT,
                ExerciseContract.COLUMN_WEIGHTTYPE,
                ExerciseContract.COLUMN_ADDITIONALINFO };

        Cursor cursor = db.query(ExerciseContract.TABLE_NAME, projection,
                ExerciseContract.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        ProgramExercise programExercise = new ProgramExercise(
                cursor.getInt(cursor.getColumnIndex(ExerciseContract.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_EXERCISETARGET)),
                cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_EXERCISE)),
                cursor.getInt(cursor.getColumnIndex(ExerciseContract.COLUMN_NUMSETS)),
                cursor.getInt(cursor.getColumnIndex(ExerciseContract.COLUMN_NUMREPS)),
                cursor.getDouble(cursor.getColumnIndex(ExerciseContract.COLUMN_WEIGHT)),
                cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_WEIGHTTYPE)),
                cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_ADDITIONALINFO)));

        cursor.close();

        return programExercise;
    }

    public List<ProgramExercise> getAllExercises() {
        List<ProgramExercise> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + ExerciseContract.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProgramExercise programExercise = new ProgramExercise(
                        cursor.getInt(cursor.getColumnIndex(ExerciseContract.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_EXERCISETARGET)),
                        cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_EXERCISE)),
                        cursor.getInt(cursor.getColumnIndex(ExerciseContract.COLUMN_NUMSETS)),
                        cursor.getInt(cursor.getColumnIndex(ExerciseContract.COLUMN_NUMREPS)),
                        cursor.getDouble(cursor.getColumnIndex(ExerciseContract.COLUMN_WEIGHT)),
                        cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_WEIGHTTYPE)),
                        cursor.getString(cursor.getColumnIndex(ExerciseContract.COLUMN_ADDITIONALINFO)));

                list.add(programExercise);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();

        return list;
    }

    public int getExerciseCount() {
        String countQuery = "SELECT  * FROM " + ExerciseContract.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int updateExercise(ProgramExercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExerciseContract.COLUMN_EXERCISETARGET, exercise.getExerciseTarget());
        values.put(ExerciseContract.COLUMN_EXERCISE, exercise.getExerciseName());
        values.put(ExerciseContract.COLUMN_NUMSETS, exercise.getNumSets());
        values.put(ExerciseContract.COLUMN_NUMREPS, exercise.getNumReps());
        values.put(ExerciseContract.COLUMN_WEIGHT, exercise.getWeight());
        values.put(ExerciseContract.COLUMN_WEIGHTTYPE, exercise.getWeightType());
        values.put(ExerciseContract.COLUMN_ADDITIONALINFO, exercise.getAdditionalInfo());

        // updating row
        return db.update(ExerciseContract.TABLE_NAME, values, ExerciseContract.COLUMN_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});
    }

    public void deleteExercise(ProgramExercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ExerciseContract.TABLE_NAME, ExerciseContract.COLUMN_ID + " = ?",
                new String[]{String.valueOf(exercise.getId())});
        db.close();
    }
}
