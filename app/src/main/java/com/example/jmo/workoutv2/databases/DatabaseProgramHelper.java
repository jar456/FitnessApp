package com.example.jmo.workoutv2.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseProgramHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ProgramHelper.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "program_table";
    private static final String COLUMN_NAME_TITLE = "id";
    private static final String COLUMN_NAME_SUBTITLE = "data";

    public DatabaseProgramHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_NAME_SUBTITLE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_SUBTITLE, item);

        Log.d(DATABASE_NAME, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) { // if data is inserted incorrectly will return -1
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public Cursor getItemID(String subtitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_NAME_TITLE + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NAME_SUBTITLE + " = '" + subtitle + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public void updateSubtitle(String newSubtitle, int id, String oldSubtitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME_SUBTITLE +
                " = '" + newSubtitle + "' WHERE " + COLUMN_NAME_TITLE + " = '" + id + "'" +
                " AND " + COLUMN_NAME_SUBTITLE + " = '" + oldSubtitle + "'";
        Log.d(DATABASE_NAME, "updateSubtitle: query: " + query);
        Log.d(DATABASE_NAME, "updateSubtitle: Setting subtitle to " + newSubtitle);
        db.execSQL(query);
    }

    public void deleteName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_NAME_TITLE + " = '" + id + "'" +
                " AND " + COLUMN_NAME_SUBTITLE + " = '" + name + "'";
        db.execSQL(query);
    }
}
