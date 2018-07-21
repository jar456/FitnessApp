package com.example.jmo.workoutv2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jmo.workoutv2.R;

public class MyProgramsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v  = inflater.inflate(R.layout.fragment_myprograms, container, false);

        Button createButton = (Button) v.findViewById(R.id.btn_myPrograms_create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ProgramSelectorFragment programSelectorFragment = new ProgramSelectorFragment();
                transaction.replace(R.id.fragment_container, programSelectorFragment).addToBackStack(null).commit();
            }
        });

        Button manageButton = (Button) v.findViewById(R.id.btn_myPrograms_manage);
        manageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ManageProgramsFragment manageProgramsFragment = new ManageProgramsFragment();
                transaction.replace(R.id.fragment_container, manageProgramsFragment).addToBackStack(null).commit();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.menu_MyPrograms);
    }

}
