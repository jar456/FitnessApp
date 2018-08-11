package com.example.jmo.workoutv2.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ProgramExercise implements Parcelable {
    private int id;
    private String exerciseName, exerciseTarget, weightType, additionalInfo;
    private int numReps, numSets;
    private double weight;

    public ProgramExercise(int id, String exerciseTarget, String exerciseName, int numSets, int numReps, double weight, String weightType, String additionalInfo) {
        this.exerciseTarget = exerciseTarget;
        this.exerciseName = exerciseName;
        this.numReps = numReps;
        this.numSets = numSets;
        this.weight = weight;
        this.weightType = weightType;
        this.additionalInfo = additionalInfo;
    }

    public ProgramExercise(String exerciseTarget, String exerciseName, int numSets, int numReps, double weight, String weightType, String additionalInfo) {
        this.exerciseTarget = exerciseTarget;
        this.exerciseName = exerciseName;
        this.numReps = numReps;
        this.numSets = numSets;
        this.weight = weight;
        this.weightType = weightType;
        this.additionalInfo = additionalInfo;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setExerciseTarget(String exerciseTarget) {
        this.exerciseTarget = exerciseTarget;
    }

    public String getExerciseTarget() {
        return exerciseTarget;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setNumReps(int numReps) {
        this.numReps = numReps;
    }

    public int getNumReps() {
        return numReps;
    }

    public void setNumSets(int numSets) {
        this.numSets = numSets;
    }

    public int getNumSets() {
        return numSets;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeightType(String weightType) {
        this.weightType = weightType;
    }

    public String getWeightType() {
        return weightType;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    protected ProgramExercise(Parcel in) {
        id = in.readInt();
        exerciseTarget = in.readString();
        exerciseName = in.readString();
        numReps = in.readInt();
        numSets = in.readInt();
        weight = in.readDouble();
        weightType = in.readString();
        additionalInfo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(exerciseTarget);
        dest.writeString(exerciseName);
        dest.writeInt(numReps);
        dest.writeInt(numSets);
        dest.writeDouble(weight);
        dest.writeString(weightType);
        dest.writeString(additionalInfo);
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