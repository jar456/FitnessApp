package com.example.jmo.workoutv2.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ProgramDay implements Parcelable {
    private List<ProgramExercise> exerciseList = new ArrayList<ProgramExercise>();
    private boolean isDayVisible;
    private String title;

    public ProgramDay () {
        isDayVisible = true;
        title = "";

    }

    public void addExercise(String exerciseTarget, String exerciseName, int numSets, int numReps, double weight, String weightType, String additionalInfo) {
        ProgramExercise e = new ProgramExercise(exerciseTarget, exerciseName, numSets, numReps, weight, weightType, additionalInfo);
        exerciseList.add(e);
    }

    public void setDayVisible(boolean isDayVisible) {
        this.isDayVisible = isDayVisible;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void removeExercise(int index) {
        if (!exerciseList.isEmpty())
            exerciseList.remove(index);

    }

    public ProgramExercise getExercise(int indexExercise) {
        return exerciseList.get(indexExercise);
    }

    public List<ProgramExercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<ProgramExercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    protected ProgramDay(Parcel in) {
        if (in.readByte() == 0x01) {
            exerciseList = new ArrayList<ProgramExercise>();
            in.readList(exerciseList, ProgramExercise.class.getClassLoader());
        } else {
            exerciseList = null;
        }
        isDayVisible = in.readByte() != 0x00;
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (exerciseList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(exerciseList);
        }
        dest.writeByte((byte) (isDayVisible ? 0x01 : 0x00));
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProgramDay> CREATOR = new Parcelable.Creator<ProgramDay>() {
        @Override
        public ProgramDay createFromParcel(Parcel in) {
            return new ProgramDay(in);
        }

        @Override
        public ProgramDay[] newArray(int size) {
            return new ProgramDay[size];
        }
    };
}