package com.example.jmo.workoutv2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.ProgramTypes;
import com.example.jmo.workoutv2.adapters.ProgramTypesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProgramSelectorFragment extends Fragment {

    private RecyclerView recyclerView_programSelector;
    private ProgramTypesAdapter adapter;
    private List<ProgramTypes> programTypesList;

    private static final String DEFAULT_TAG = "DEFAULT";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_program_types, container, false);

        recyclerView_programSelector = (RecyclerView) v.findViewById(R.id.recyclerView_programTypes);

        programTypesList = new ArrayList<>();
        adapter = new ProgramTypesAdapter(getActivity(),programTypesList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_programSelector.setLayoutManager(mLayoutManager);
        recyclerView_programSelector.setItemAnimator(new DefaultItemAnimator());
        recyclerView_programSelector.setAdapter(adapter);

        prepareProgramTypes();

        return v;
    }

    private void prepareProgramTypes() {
        ProgramTypes a = new ProgramTypes(getString(R.string.templateProgram_custom), getString(R.string.description_custom), R.drawable.ic_add_box, DEFAULT_TAG);
        programTypesList.add(a);
        a = new ProgramTypes(getString(R.string.templateProgram_bodybuilding), getString(R.string.description_bodybuilding), R.drawable.ic_dumbell, DEFAULT_TAG);
        programTypesList.add(a);
        a = new ProgramTypes(getString(R.string.templateProgram_powerlifting), getString(R.string.description_powerlifting), R.drawable.ic_dumbell, DEFAULT_TAG);
        programTypesList.add(a);
        a = new ProgramTypes(getString(R.string.templateProgram_oly), getString(R.string.description_bodybuilding), R.drawable.ic_dumbell, DEFAULT_TAG);
        programTypesList.add(a);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.menu_programSelector);
    }

}
