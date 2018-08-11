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

        data.getWeek(0).getDay(0).addExercise("Legs","Squat", 3, 10, 0, "lb", "Just test");
        data.getWeek(0).getDay(0).addExercise("Chest","Bench", 3, 10, 255, "kg", "Squatting!" );
        data.getWeek(0).getDay(0).addExercise("Back","Deadlift", 3, 10, 255, "RPE", null);

        data.getWeek(0).getDay(1).addExercise("Chest","Dumbell Bench Press", 3, 10, 0, "% of Max", null);
        data.getWeek(0).getDay(1).addExercise("Back","Lateral Pull Downs", 3, 10, 0, "lb", null);
        data.getWeek(0).getDay(2).addExercise("Back","Seated Rows", 3, 10, 0, "% of Max", null);

        data.getWeek(0).getDay(3).addExercise("Legs","Squat", 3, 5, 315, "% of Max", "A LOTTA WEIGHT");
        data.getWeek(0).getDay(5).addExercise("Chest","Bench", 3, 10, 135, "% of Max", "");
        data.getWeek(0).getDay(6).addExercise("Shoulders", "Shoulder Press", 3, 25, 65, "lb", null);

        data.addWeek();

        data.getWeek(1).addDay();
        data.getWeek(1).getDay(0).addExercise(null,"Cinnabon Lift", 3, 10, 0, "RPE", "What");
        data.getWeek(1).getDay(0).addExercise("Chest","Dumbell Bench Press", 3, 10, 0, "kg", null);
        data.getWeek(1).getDay(0).addExercise("Chest","Dumbell Incline Bench Press", 3, 10, 0, "kg", null);
    }
}
