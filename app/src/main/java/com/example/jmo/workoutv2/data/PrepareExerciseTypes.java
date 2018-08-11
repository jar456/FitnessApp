package com.example.jmo.workoutv2.data;

import android.content.Context;
import android.content.res.Resources;

import com.example.jmo.workoutv2.R;

import java.util.List;

public class PrepareExerciseTypes {

    public static void CreateExerciseTypeList(List<ExerciseTypes> list, Context context) {
        Resources res = context.getResources();

        ExerciseTypes a = new ExerciseTypes(res.getString(R.string.exerciseSelect_custom) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_chest) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_back) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_shoulders) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_arms) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_legs) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_core) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_cardio) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);
        a = new ExerciseTypes(res.getString(R.string.exerciseSelect_search) ,  res.getDrawable(R.drawable.ic_dumbell), list.size());
        list.add(a);

    }
}
