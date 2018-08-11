package com.example.jmo.workoutv2.dialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.jmo.workoutv2.R;

public class ExerciseEditTitleDialogFragment extends DialogFragment {

    private static final String PREVTITLE_KEY = "prevtitle_key";
    private static final String PREVSUBTITLE_KEY = "prevsubtitle_key";


    String prevTitle, prevSubtitle;
    EditText editText_title, editText_subtitle;
    ExerciseEditTitleDialogListener mListener;

    public interface ExerciseEditTitleDialogListener {
        public void onExerciseEditTitlePositiveClick(DialogFragment dialog, String title, String subtitle);
    }

    public static ExerciseEditTitleDialogFragment newInstance(String prevTitle, String prevSubtitle) {

        Bundle args = new Bundle();
        args.putString(PREVTITLE_KEY, prevTitle);
        args.putString(PREVSUBTITLE_KEY, prevSubtitle);

        ExerciseEditTitleDialogFragment fragment = new ExerciseEditTitleDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (ExerciseEditTitleDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = inflater.inflate(R.layout.popup_editexercisetitle, null);

        try {
            prevTitle = getArguments().getString(PREVTITLE_KEY);
            prevSubtitle = getArguments().getString(PREVSUBTITLE_KEY);
        } catch (NullPointerException e) {
            dismiss();
        }

        editText_title = v.findViewById(R.id.editText_editExercisePopup_title);
        editText_subtitle = v.findViewById(R.id.editText_editExercisePopup_subtitle);
        editText_title.setText(prevTitle);
        editText_subtitle.setText(prevSubtitle);

        builder.setMessage(R.string.exerciseInfoPopup_dialogTitle);
        builder.setView(v);
        builder.setPositiveButton(R.string.choice_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String title = editText_title.getText().toString();
                String subtitle = editText_subtitle.getText().toString();
                mListener.onExerciseEditTitlePositiveClick(ExerciseEditTitleDialogFragment.this, title, subtitle);
            }
        }).setNegativeButton(R.string.choice_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        return builder.create();

    }
}
