package com.example.jmo.workoutv2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.jmo.workoutv2.R;

public class MyProgramsRecentFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.programs_recent_null, container, false);

        RelativeLayout relativeRecentNull = (RelativeLayout) v.findViewById(R.id.relativeRecentNull);
        relativeRecentNull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ProgramSelectorFragment programSelectorFragment = new ProgramSelectorFragment();
                transaction.replace(R.id.fragment_container, programSelectorFragment).addToBackStack(null);
                transaction.commit();

            }
        });

        return v;
    }
}
