package com.example.jmo.workoutv2.data;

import android.graphics.drawable.Drawable;

public class ExerciseTypes {
    private int id;
    private String title;
    private Drawable img;

    public ExerciseTypes(String title, Drawable img, int id) {
        this.title = title;
        this.img = img;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Drawable getImg() {
        return img;
    }

    public int getId() {
        return id;
    }
}
