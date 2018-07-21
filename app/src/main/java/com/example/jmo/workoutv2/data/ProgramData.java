package com.example.jmo.workoutv2.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ProgramData implements Parcelable {
    private String programName;
    private String programTag;
    private int programID;
    // private String lastFocusDay; // private String lastFocusWeek; add possibly?
    private List<ProgramWeek> weekList = new ArrayList<ProgramWeek>();

    public ProgramData(String programName, String programTag) {
        this.programName = programName;
        this.programTag = programTag;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public int getProgramID() {
        return this.programID;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    public String getProgramName() {
        return programName;
    }

    public String getProgramTag() {
        return programTag;
    }
    public void setProgramTag (String tag) {
        this.programTag = tag;
    }

    // Week Assessors

    public void addWeek() {
        ProgramWeek pw =  new ProgramWeek();
        weekList.add(pw);
    }

    public void removeWeek(int index) {
        if (!weekList.isEmpty() && (index < weekList.size()))
            weekList.remove(index);
    }

    public ProgramWeek getWeek(int indexWeek) {
        return weekList.get(indexWeek);
    }

    public int getWeekSize() {
        return weekList.size();
    }

    protected ProgramData(Parcel in) {
        programName = in.readString();
        programTag = in.readString();
        if (in.readByte() == 0x01) {
            weekList = new ArrayList<ProgramWeek>();
            in.readList(weekList, ProgramWeek.class.getClassLoader());
        } else {
            weekList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(programName);
        dest.writeString(programTag);
        if (weekList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(weekList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProgramData> CREATOR = new Parcelable.Creator<ProgramData>() {
        @Override
        public ProgramData createFromParcel(Parcel in) {
            return new ProgramData(in);
        }

        @Override
        public ProgramData[] newArray(int size) {
            return new ProgramData[size];
        }
    };
}