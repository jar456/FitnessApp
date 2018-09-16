package com.example.jmo.workoutv2.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.adapters.ProgramDataAdapter;
import com.example.jmo.workoutv2.data.ProgramData;
import com.example.jmo.workoutv2.data.ProgramDay;
import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.data.ProgramWeek;
import com.example.jmo.workoutv2.dialogFragments.AddExerciseDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgramDataFragment extends Fragment implements AddExerciseDialogFragment.AddExerciseDialogListener,
        ProgramDataAdapter.ProgramDataListener {

    private static final String PROGRAMDATA_KEY = "programdata_key";
    private static final String FOCUSEDWEEK_KEY = "focusedweek_key";
    private static final String ISEDITABLE_KEY = "iseditable_key";

    private ProgramData programData;
    private List<String> dayTitleList = new ArrayList<>();
    private List<Integer> listDataHeader;
    private HashMap<Integer, List<ProgramExercise>> listDataChild;

    private ProgramDataAdapter listAdapter;
    private ExpandableListView expandableListView;

    private int focusedWeek;
    private int focusedDay;
    private boolean isEditable;

    private RecyclerView weekButtonsRecycler;

    public static ProgramDataFragment newInstance(ProgramData programData, int focusedWeek, boolean isEditable) {
        ProgramDataFragment fragment = new ProgramDataFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PROGRAMDATA_KEY, programData);
        bundle.putInt(FOCUSEDWEEK_KEY, focusedWeek);
        bundle.putBoolean(ISEDITABLE_KEY, isEditable);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.explist_program_data, container, false);

        programData = (ProgramData) getArguments().getParcelable(PROGRAMDATA_KEY);
        focusedWeek = getArguments().getInt(FOCUSEDWEEK_KEY);
        isEditable = getArguments().getBoolean(ISEDITABLE_KEY);

        prepareListData();

        expandableListView = (ExpandableListView) v.findViewById(R.id.ExpList_programData);

        listAdapter = new ProgramDataAdapter(getActivity(), listDataHeader, listDataChild, isEditable, dayTitleList, this);
        expandableListView.setAdapter(listAdapter);

        expandableListView.setDividerHeight(0);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                ProgramDay day = programData.getWeek(focusedWeek).getDay(groupPosition);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                if (isEditable) {
                    for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                        ft.remove(fragment);
                    }

                    ExerciseEditFragment exerciseEditFragment = ExerciseEditFragment.newInstance(day, childPosition, false);
                    ft.add(R.id.CoordinatorLayout_programEdit, exerciseEditFragment).addToBackStack(null).commit();
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            //weekButtonsRecycler = getActivity().findViewById(R.id.recyclerView_weekButton);
            //weekButtonsRecycler.setVisibility(View.INVISIBLE);
        } catch (NullPointerException e) { }
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            weekButtonsRecycler.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
        }

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<Integer>();
        listDataChild = new HashMap<Integer, List<ProgramExercise>>();

        ProgramWeek getFocusedWeek = programData.getWeek(focusedWeek);


        for (int i = 0; i < getFocusedWeek.getDaySize(); i++){
            listDataHeader.add(i+1);
            dayTitleList.add(getFocusedWeek.getDay(i).getTitle());

            for (int j = 0; j < getFocusedWeek.getDay(i).getExerciseList().size(); j++) {
                listDataChild.put(listDataHeader.get(i), getFocusedWeek.getDay(i).getExerciseList());
            }
        }
    }

    @Override
    public void onExpandDayClick(final View v, final int groupPosition) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    // Day Bar Expand Items
                    case R.id.dayExpandMenu_removeAll: {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Remove All Exercises?")
                                .setPositiveButton(R.string.choice_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Snackbar.make(v, "Successfully removed day contents.", Snackbar.LENGTH_LONG).show();

                                        programData.getWeek(focusedWeek).getDay(groupPosition).getExerciseList().clear();
                                        prepareListData();
                                        listAdapter = new ProgramDataAdapter(getActivity(), listDataHeader, listDataChild, isEditable, dayTitleList, ProgramDataFragment.this);
                                        expandableListView.setAdapter(listAdapter);
                                    }
                                })
                                .setNegativeButton(R.string.choice_cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create().show();

                        return true;
                    }

                    case R.id.dayExpandMenu_setTitle: {
                        Snackbar.make(v, "Successfully set title.", Snackbar.LENGTH_LONG).show();
                        programData.getWeek(focusedWeek).getDay(groupPosition).setTitle("Setted");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Enter title")
                                .setView(R.layout.popup_entertitle)
                                .setNegativeButton(R.string.choice_no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setPositiveButton(R.string.choice_yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        EditText editDayTitle = (EditText) ((AlertDialog) dialogInterface).findViewById(R.id.editDayTitle_popup);
                                        String newDayTitle = editDayTitle.getText().toString();

                                        programData.getWeek(focusedWeek).getDay(groupPosition).setTitle(newDayTitle); // UPDATED PROGRAMDATA
                                        dayTitleList.set(groupPosition, newDayTitle);

                                        listAdapter.notifyDataSetChanged();
                                    }
                                });
                        builder.create().show();

                        return true;
                        }
                    default:
                        return false;
                }
            }
        });
        popupMenu.inflate(R.menu.dayexpand_menu);
        popupMenu.show();
    }

    @Override
    public void onAddExerciseClick(View v, int groupPosition) {
        ProgramDay day = programData.getWeek(focusedWeek).getDay(groupPosition);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
            ft.remove(fragment);
        }

        ExerciseListFragment exerciseListFragment = ExerciseListFragment.newInstance(day);
        ft.add(R.id.CoordinatorLayout_programEdit, exerciseListFragment).addToBackStack(null).commit();

        focusedDay = groupPosition;
    }

    @Override
    public void onRemoveExerciseClick(View v, final int groupPosition, final int childPosition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.removeExercise_popup_title).setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                programData.getWeek(focusedWeek).getDay(groupPosition).removeExercise(childPosition);
                listDataChild.put(listDataHeader.get(groupPosition), programData.getWeek(focusedWeek).getDay(groupPosition).getExerciseList());
                listAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, View v) {
        // User touched the dialog's positive button

        EditText eText_addExerciseName = (EditText) v.findViewById(R.id.editText_exerciseName);
        EditText eText_addExerciseReps = (EditText) v.findViewById(R.id.editText_reps);
        EditText eText_addExerciseSets = (EditText) v.findViewById(R.id.editText_sets);

        final String exerciseName = eText_addExerciseName.getText().toString();
        final int numSets = Integer.parseInt(eText_addExerciseSets.getText().toString());
        final int numReps = Integer.parseInt(eText_addExerciseReps.getText().toString());

        ProgramWeek getFocusedExercise = programData.getWeek(focusedWeek);

        //getFocusedExercise.getDay(focusedDay).getExerciseList().add(new ProgramExercise(exerciseName, numSets, numReps));

        listDataChild.put(listDataHeader.get(focusedDay), getFocusedExercise.getDay(focusedDay).getExerciseList());
        listAdapter.notifyDataSetChanged();

        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        dialog.dismiss();
    }
}
