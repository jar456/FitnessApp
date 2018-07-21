package com.example.jmo.workoutv2.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.ProgramData;
import com.example.jmo.workoutv2.fragments.ProgramDataFragment;
import com.google.gson.Gson;

public class ProgramViewActivity extends AppCompatActivity {

    private ProgramData programData;
    private String program_json;
    private LinearLayout layout_WeekSelection;
    private int focusedWeek = 0;
    private int indexButtons = -1;
    private final static boolean isEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_program_view);
        setSupportActionBar(toolbar);

        layout_WeekSelection = (LinearLayout) findViewById(R.id.layout_view_weekselect);

        getIncomingIntent();
        createWeekButtons();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
        ft.add(R.id.fragment_container_program_view, programDataFragment).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(programData.getProgramName());
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("program_json")) {
            program_json = getIntent().getStringExtra("program_json");

            Gson gson = new Gson();
            programData = gson.fromJson(program_json, ProgramData.class);
        }
    }

    private Button prevWeekButton;
    public void createWeekButtons() {

        for (int i = 0; i < programData.getWeekSize(); i++) {
            final Button weekButton = new Button(this);
            if (i == focusedWeek) {
                prevWeekButton = weekButton;
            }
            indexButtons++;
            onCreateWeekSelect(weekButton, indexButtons);

            weekButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "INDEX: " + weekButton.getId(), Snackbar.LENGTH_LONG).show();
                    prevWeekButton.setBackground(getResources().getDrawable(R.drawable.line_unselected));
                    weekButton.setBackground(getResources().getDrawable(R.drawable.line_selected));
                    prevWeekButton = weekButton;
                    focusedWeek = weekButton.getId();

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
                    ft.replace(R.id.fragment_container_program_view, programDataFragment).commit();

                }
            });

            layout_WeekSelection.addView(weekButton);
        }
    }

    public void onCreateWeekSelect(Button weekButton, int i) {
        if (i == focusedWeek) {
            weekButton.setBackground(getResources().getDrawable(R.drawable.line_selected));
        } else {
            weekButton.setBackground(getResources().getDrawable(R.drawable.line_unselected));
        }
        weekButton.setText(getResources().getString(R.string.week_select) + " " + (i + 1));
        weekButton.setId(i);
        weekButton.setTextColor(getResources().getColor(R.color.colorWhite));
    }
}
