package com.example.jmo.workoutv2.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ProgramWeek implements Parcelable {
    private List<ProgramDay> dayList = new ArrayList<ProgramDay>();

    public ProgramWeek() {
        for (int i = 0; i < 7; i++) {
            dayList.add(new ProgramDay());
        }

    }

    public void addDay() {
        if (dayList.size() < 7)
        {
            ProgramDay d = new ProgramDay();
            dayList.add(d);
        }
    }

    public void removeDay(int index) {
        if (!dayList.isEmpty() && (index < dayList.size()))
            dayList.remove(index);
    }

    public ProgramDay getDay(int indexDay) {
        return dayList.get(indexDay);
    }

    public List<ProgramDay> getDayList() {
        return dayList;
    }

    public int getDaySize() {
        return dayList.size();
    }

    protected ProgramWeek(Parcel in) {
        if (in.readByte() == 0x01) {
            dayList = new ArrayList<ProgramDay>();
            in.readList(dayList, ProgramDay.class.getClassLoader());
        } else {
            dayList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(dayList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProgramWeek> CREATOR = new Parcelable.Creator<ProgramWeek>() {
        @Override
        public ProgramWeek createFromParcel(Parcel in) {
            return new ProgramWeek(in);
        }

        @Override
        public ProgramWeek[] newArray(int size) {
            return new ProgramWeek[size];
        }
    };
}