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

    private Button createButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myprograms, container, false);

        createButton = (Button) v.findViewById(R.id.btn_myPrograms_Create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() != null) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ProgramSelectorFragment programSelectorFragment = new ProgramSelectorFragment();
                    transaction.replace(R.id.fragment_container, programSelectorFragment).addToBackStack(null);
                    transaction.commit();
                }

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
