package com.example.jmo.workoutv2.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ProgramExercise implements Parcelable {
    private String exerciseName;
    private int numReps;
    private int numSets;

    public ProgramExercise() {

    }

    public ProgramExercise(String exerciseName, int numSets, int numReps) {
        this.exerciseName = exerciseName;
        this.numReps = numReps;
        this.numSets = numSets;
    }

    public void setExerciseAll(String exerciseName, int numSets, int numReps) {
        this.exerciseName = exerciseName;
        this.numSets = numSets;
        this.numReps = numReps;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getNumReps() {
        return numReps;
    }

    public void setNumReps(int numReps) {
        this.numReps = numReps;
    }

    public int getNumSets() {
        return numSets;
    }

    public void setNumSets(int numSets) {
        this.numSets = numSets;
    }

    protected ProgramExercise(Parcel in) {
        exerciseName = in.readString();
        numReps = in.readInt();
        numSets = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(exerciseName);
        dest.writeInt(numReps);
        dest.writeInt(numSets);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProgramExercise> CREATOR = new Parcelable.Creator<ProgramExercise>() {
        @Override
        public ProgramExercise createFromParcel(Parcel in) {
            return new ProgramExercise(in);
        }

        @Override
        public ProgramExercise[] newArray(int size) {
            return new ProgramExercise[size];
        }
    };
}