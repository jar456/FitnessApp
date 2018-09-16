package com.example.jmo.workoutv2.contracts;

public class ExerciseContract {

    public static final String TABLE_NAME = "exercise_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXERCISETARGET = "exercise_target";
    public static final String COLUMN_EXERCISE = "exercise_name";
    public static final String COLUMN_NUMSETS = "num_sets";
    public static final String COLUMN_NUMREPS = "num_reps";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_WEIGHTTYPE = "weight_type";
    public static final String COLUMN_ADDITIONALINFO = "additional_info";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_EXERCISETARGET + " TEXT, "
                    + COLUMN_EXERCISE + " TEXT, "
                    + COLUMN_NUMSETS + " INTEGER, "
                    + COLUMN_NUMREPS + " INTEGER, "
                    + COLUMN_WEIGHT + " REAL, "
                    + COLUMN_WEIGHTTYPE + " TEXT, "
                    + COLUMN_ADDITIONALINFO + " TEXT"
                    + ")";
}
