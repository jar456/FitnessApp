package com.example.jmo.workoutv2.dialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.jmo.workoutv2.R;

public class InputProgramTitleDialogFragment extends DialogFragment {

    InputProgramTitleDialogListener mListener;

    public interface InputProgramTitleDialogListener {
        public void onInputProgramTitleDialogPositiveClick(DialogFragment dialog, String newName);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (InputProgramTitleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.popup_entertitle, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Enter title")
                .setView(v)
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editTextTitle = v.findViewById(R.id.editDayTitle_popup);
                        String name = editTextTitle.getText().toString();
                        mListener.onInputProgramTitleDialogPositiveClick(InputProgramTitleDialogFragment.this, name);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}
