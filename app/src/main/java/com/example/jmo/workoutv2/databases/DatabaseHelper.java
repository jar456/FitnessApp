package com.example.jmo.workoutv2.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jmo.workoutv2.contracts.ExerciseContract;
import com.example.jmo.workoutv2.data.ProgramExercise;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH;
    private static String DB_NAME = "ExerciseListDB.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = context.getFilesDir().getPath();
    }

    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (!dbExist) {
            this.getReadableDatabase();

            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;

        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {

        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {

        if(db != null)
            db.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getCursor() {
        String query = "SELECT * FROM " + ExerciseContract.TABLE_NAME;

        return db.rawQuery(query, null);
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
}
