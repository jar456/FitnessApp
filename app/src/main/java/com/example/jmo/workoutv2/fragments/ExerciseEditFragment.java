package com.example.jmo.workoutv2.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.ProgramDay;
import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.dialogFragments.ExerciseEditTitleDialogFragment;

public class ExerciseEditFragment extends Fragment implements ExerciseEditTitleDialogFragment.ExerciseEditTitleDialogListener {

    private static final String tag = "ExerciseEditFragment";
    private static final String PROGRAMDAY_KEY = "programDay_key";
    private static final String EXERCISEPOSITION_KEY = "exercisePosition_key";
    private static final String weightLB = "lb";
    private static final String weightKG = "kg";

    private TextView textView_title, textView_subtitle;
    private ImageButton imageButton_editTitle;
    private EditText editText_numSets, editText_numReps, editText_weight, editText_additionalInfo;
    private Spinner spinner_weightType;
    private FloatingActionButton fab_Save;

    private ProgramDay day;
    private ProgramExercise exercise;
    private int exercisePosition;

    public static ExerciseEditFragment newInstance(ProgramDay day, int exercisePosition) {
        Bundle args = new Bundle();
        args.putParcelable(PROGRAMDAY_KEY, day);
        args.putInt(EXERCISEPOSITION_KEY, exercisePosition);

        ExerciseEditFragment fragment = new ExerciseEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise_edit, container, false);

        try {
            day = getArguments().getParcelable(PROGRAMDAY_KEY);
            exercisePosition = getArguments().getInt(EXERCISEPOSITION_KEY);
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } catch (NullPointerException e) {
            return v; // Add Error Dialog
        }

        exercise = day.getExercise(exercisePosition);
        PrepareViews(v, exercise);

        imageButton_editTitle.setOnClickListener(editTitleListener);
        fab_Save.setOnClickListener(fabSaveListener);
        return v;
    }



    private void PrepareViews(View v, ProgramExercise exercise) {
        String title = exercise.getExerciseName();
        String subtitle = exercise.getExerciseTarget();
        String numReps = exercise.getNumReps() + "";
        String numSets = exercise.getNumSets() + "";
        String weight = exercise.getWeight() + "";
        String additionalInfo = exercise.getAdditionalInfo();

        textView_title = (TextView) v.findViewById(R.id.txt_exerciseInfo_title);
        textView_subtitle = (TextView) v.findViewById(R.id.txt_exerciseInfo_subtitle);
        imageButton_editTitle = (ImageButton) v.findViewById(R.id.imgBtn_exerciseInfo_editTitle);
        editText_numSets = (EditText) v.findViewById(R.id.editText_exerciseInfo_numSets);
        editText_numReps = (EditText) v.findViewById(R.id.editText_exerciseInfo_numReps);
        editText_weight = (EditText) v.findViewById(R.id.editText_exerciseInfo_weight);
        spinner_weightType = (Spinner) v.findViewById(R.id.spinner_exerciseInfo_weightType);
        editText_additionalInfo = (EditText) v.findViewById(R.id.editText_exerciseInfo_additional);
        fab_Save = (FloatingActionButton) v.findViewById(R.id.fab_exerciseInfo);


        textView_title.setText(title);
        textView_subtitle.setText(subtitle);
        editText_numSets.setText(numSets, TextView.BufferType.EDITABLE);
        editText_numReps.setText(numReps, TextView.BufferType.EDITABLE);
        editText_weight.setText(weight, TextView.BufferType.EDITABLE);
        editText_additionalInfo.setText(additionalInfo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.weightType_array, android.R.layout.simple_spinner_dropdown_item);
        spinner_weightType.setAdapter(adapter);

        Resources res = getResources(); // For the sake of readability
        String weightType = exercise.getWeightType();

        if (weightType.equals(res.getString(R.string.exerciseWeightType_lb))) {
            spinner_weightType.setSelection(0);
        } else if (weightType.equals(res.getString(R.string.exerciseWeightType_kg))) {
            spinner_weightType.setSelection(1);
        } else if (weightType.equals(res.getString(R.string.exerciseWeightType_RPE))) {
            spinner_weightType.setSelection(2);
        } else if (weightType.equals(res.getString(R.string.exerciseWeightType_max))) {
            spinner_weightType.setSelection(3);
        }
    }

    View.OnClickListener editTitleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String prevTitle = textView_title.getText().toString();
            String prevSubtitle = textView_subtitle.getText().toString();

            ExerciseEditTitleDialogFragment dialog = ExerciseEditTitleDialogFragment.newInstance(prevTitle, prevSubtitle);
            dialog.show(getFragmentManager(), null);
            dialog.setTargetFragment(ExerciseEditFragment.this, 1);
        }
    };

    View.OnClickListener fabSaveListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String exerciseTarget = textView_subtitle.getText().toString();
            String exerciseTitle = textView_title.getText().toString();
            int numSets = Integer.parseInt(editText_numSets.getText().toString());
            int numReps = Integer.parseInt(editText_numReps.getText().toString());
            double weight = Integer.parseInt(editText_weight.getText().toString());
            String weightType = spinner_weightType.getSelectedItem().toString();
            String additionalInfo = editText_additionalInfo.getText().toString();

            exercise.setAll(exerciseTarget, exerciseTitle, numSets, numReps, weight, weightType, additionalInfo);

            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    @Override
    public void onExerciseEditTitlePositiveClick(DialogFragment dialog, String title, String subtitle) {
        textView_title.setText(title);
        textView_subtitle.setText(subtitle);
        dialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        } catch (NullPointerException e) {
            Log.d(tag, " No Action Bar");
        }
    }
}
