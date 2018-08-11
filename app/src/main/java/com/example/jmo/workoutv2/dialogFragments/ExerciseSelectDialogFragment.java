package com.example.jmo.workoutv2.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.adapters.ExerciseSelectAdapter;
import com.example.jmo.workoutv2.data.ExerciseTypes;
import com.example.jmo.workoutv2.data.PrepareExerciseTypes;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSelectDialogFragment extends DialogFragment {

    private RecyclerView mRecyclerView;
    private List<ExerciseTypes> list;
    ExerciseSelectAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.popup_exerciseselect, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        list = new ArrayList<ExerciseTypes>();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rV_exerciseSelect);
        PrepareExerciseTypes.CreateExerciseTypeList(list, getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(new ExerciseSelectAdapter(list, getActivity(), adapterInterface));

        return builder.create();
    }

    ExerciseSelectAdapter.OnItemClickListener adapterInterface = new ExerciseSelectAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(ExerciseTypes data, View view) {

            switch (data.getId()) {
                case 0:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 1:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 2:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 3:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 4:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 5:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 6:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 7:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
                case 8:
                    Snackbar.make(view, Integer.toString(data.getId()), Snackbar.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}

