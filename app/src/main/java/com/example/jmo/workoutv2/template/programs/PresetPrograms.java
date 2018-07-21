package com.example.jmo.workoutv2.template.programs;

import android.content.Context;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.ProgramData;

public class PresetPrograms {
    public static void CreateTemplateProgram(ProgramData data, String name, Context c) {
        if (name.equals(c.getString(R.string.templateProgram_custom))) {
            DefaultProgramTemplate(data);
        }

    }

    private static void DefaultProgramTemplate(ProgramData data) {
        data.addWeek();

        data.getWeek(0).getDay(0).addExercise("Squat", 3, 10);
        data.getWeek(0).getDay(0).addExercise("Bench", 3, 10);
        data.getWeek(0).getDay(0).addExercise("Deadlift", 3, 10);

        data.getWeek(0).getDay(1).addExercise("Dumbell Bench Press", 3, 10);
        data.getWeek(0).getDay(1).addExercise("Lateral Pull Downs", 3, 10);
        data.getWeek(0).getDay(1).addExercise("Seated Rows", 3, 10);

        data.getWeek(0).getDay(2).addExercise("Squat", 3, 10);
        data.getWeek(0).getDay(2).addExercise("Bench", 3, 10);
        data.getWeek(0).getDay(2).addExercise("Leg Accessories", 3, 10);

        data.addWeek();

        data.getWeek(1).addDay();
        data.getWeek(1).getDay(0).addExercise("Cinnabon Lift", 3, 10);
        data.getWeek(1).getDay(0).addExercise("Tricep Extensions", 3, 10);
        data.getWeek(1).getDay(0).addExercise("Overhead Shoulder Press", 3, 10);
    }
}
