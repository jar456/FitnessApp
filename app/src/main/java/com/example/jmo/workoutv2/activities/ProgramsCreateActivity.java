package com.example.jmo.workoutv2.activities;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.DatabaseProgramHelper;
import com.example.jmo.workoutv2.data.ProgramData;
import com.example.jmo.workoutv2.dialogFragments.InputProgramTitleDialogFragment;
import com.example.jmo.workoutv2.dialogFragments.PromptExitDialogFragment;
import com.example.jmo.workoutv2.fragments.ProgramDataFragment;
import com.example.jmo.workoutv2.template.programs.PresetPrograms;
import com.google.gson.Gson;

public class ProgramsCreateActivity extends AppCompatActivity implements InputProgramTitleDialogFragment.InputProgramTitleDialogListener,
        PromptExitDialogFragment.PromptExitDialogListener {
    DatabaseProgramHelper mDatabaseHelper;

    private String programName; // Primarily used if loaded from a new program template
    private String programTag; // Primarily used if loaded from a new program template

    private int focusedWeek; // Track the week user is focused on
    private Button prevWeekButton;
    private int indexButtons = -1;

    private ProgramData programData;

    private String selectedProgram; // Used for updating
    private int selectedID; // Used for updating

    private final static boolean isEditable = true;
    private boolean isExitable = false;
    private final static String SHARED_PREF_KEY = "num_programs"; // Used for unique program modifier

    private LinearLayout layout_WeekSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_edit);

        mDatabaseHelper = new DatabaseProgramHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_programscreate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save);

        // Check database for imformation... if not create DEFAULT
        getIncomingIntent();

        // If 1st time creation
        if (programTag.equals("DEFAULT")) {
            programData = new ProgramData("", programTag);
            PresetPrograms.CreateTemplateProgram(programData, programName, getApplicationContext()); // Note null program name
        }

        if (programName != null) {
            setTitle(programName);
        }

        // Pass the data to fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
        ft.add(R.id.fragment_container_program_edit, programDataFragment).commit();

        layout_WeekSelection = (LinearLayout) findViewById(R.id.layout_weekselect);

        createWeekButtons();
        createAddWeekButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.program_creator_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case android.R.id.home: // Save Button
                if (programData.getProgramTag().equals("DEFAULT")) {

                    if (programData.getProgramName().equals("")) {
                        InputProgramTitleDialogFragment dialog = new InputProgramTitleDialogFragment();
                        dialog.show(getSupportFragmentManager(), "InputProgramTitleDialogFragment");
                    } else {
                        AddData(); // This means the user already enter a program name
                    }

                } else if (programData.getProgramTag().equals("CUSTOM")) {
                    UpdateData();
                    Snackbar.make(findViewById(R.id.CoordinatorLayout_program_create), "Successfully Saved", Snackbar.LENGTH_LONG).show();
                }

                return true;

            case R.id.programCreatorMenu_setTitle:
                InputProgramTitleDialogFragment dialog = new InputProgramTitleDialogFragment();
                dialog.show(getSupportFragmentManager(), "InputProgramTitleDialogFragment");
                return true;

            case R.id.programCreatorMenu_help:
                return true;

            case R.id.programCreatorMenu_exit:
                PromptExitDialogFragment promptExitDialogFragment = new PromptExitDialogFragment();
                promptExitDialogFragment.show(getSupportFragmentManager(), "PromptExitDialogFragment");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("program_name") && getIntent().hasExtra("program_id")) {
            programName = getIntent().getStringExtra("program_name");
            programTag = getIntent().getStringExtra("program_id");
        } else if (getIntent().hasExtra("program_json") && getIntent().hasExtra("selected_id")) {
            String program_json = getIntent().getStringExtra("program_json");
            Gson gson = new Gson();
            programData = gson.fromJson(program_json, ProgramData.class);
            programTag = programData.getProgramTag();
            programName = programData.getProgramName();

            selectedProgram = program_json; // SET ORIGINAL DATA STATE
            selectedID = getIntent().getIntExtra("selected_id", -1);
        }
    }

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
                    if (prevWeekButton.getId() == weekButton.getId()) {
                        return;
                    }
                    Snackbar.make(view, "INDEX: " + weekButton.getId(), Snackbar.LENGTH_LONG).show();
                    prevWeekButton.setBackground(getResources().getDrawable(R.drawable.line_unselected));
                    weekButton.setBackground(getResources().getDrawable(R.drawable.line_selected));
                    prevWeekButton = weekButton;
                    focusedWeek = weekButton.getId();

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
                    ft.replace(R.id.fragment_container_program_edit, programDataFragment).commit();

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

    public void createAddWeekButton() {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final ImageButton addWeekButton = new ImageButton(this);
        // final because will be used in override

        onCreateAddWeekButton(addWeekButton, params);
        layout_WeekSelection.addView((addWeekButton));

        addWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_WeekSelection.removeView(addWeekButton);

                final Button weekButton = new Button(getApplicationContext());
                indexButtons++;
                onCreateWeekSelect(weekButton, indexButtons);
                layout_WeekSelection.addView(weekButton);

                prevWeekButton.setBackground(getResources().getDrawable(R.drawable.line_unselected));
                weekButton.setBackground(getResources().getDrawable(R.drawable.line_selected));
                prevWeekButton = weekButton;
                focusedWeek = weekButton.getId();

                onCreateAddWeekButton(addWeekButton, params);
                layout_WeekSelection.addView(addWeekButton);

                programData.addWeek();

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
                ft.replace(R.id.fragment_container_program_edit, programDataFragment).commit();

                weekButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (prevWeekButton.getId() == weekButton.getId()) {
                            return;
                        }
                        Snackbar.make(view, "INDEX: " + weekButton.getId(), Snackbar.LENGTH_LONG).show();
                        prevWeekButton.setBackground(getResources().getDrawable(R.drawable.line_unselected));
                        weekButton.setBackground(getResources().getDrawable(R.drawable.line_selected));
                        prevWeekButton = weekButton;
                        focusedWeek = weekButton.getId();

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
                        ft.replace(R.id.fragment_container_program_edit, programDataFragment).commit();
                    }
                });

            }
        });
    }

    private void onCreateAddWeekButton(ImageButton imageButton, LinearLayout.LayoutParams params) {
        imageButton.setImageResource(R.drawable.ic_add_circle);
        imageButton.setBackgroundColor(Color.TRANSPARENT);
        params.gravity = Gravity.CENTER_VERTICAL;
        imageButton.setLayoutParams(params);
    }

    public void AddData() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int numPrograms = sharedPreferences.getInt(SHARED_PREF_KEY, 0);
        programData.setProgramID(numPrograms);
        numPrograms++;
        editor.putInt(SHARED_PREF_KEY, numPrograms);
        editor.apply();

        Gson gson = new Gson();
        String newEntry = gson.toJson(programData);
        selectedProgram = newEntry;

        boolean insertData = mDatabaseHelper.addData(newEntry);

        View parentLayout = findViewById(android.R.id.content);
// Now we must get the id in case the user saves the program again on first time launch
        if (insertData) {
            Snackbar.make(parentLayout, "Successfully saved program.", Snackbar.LENGTH_LONG).show();
            Cursor data = mDatabaseHelper.getItemID(newEntry);
            while (data.moveToNext()) {
                selectedID = data.getInt(0);
            }

        } else {
            Snackbar.make(parentLayout, "Error saving program", Snackbar.LENGTH_LONG).show();
        }
    }

    public void UpdateData() {
        Gson gson = new Gson();
        String newEntry = gson.toJson(programData);

        if (selectedID != -1) {
            mDatabaseHelper.updateSubtitle(newEntry, selectedID, selectedProgram);
        }

        selectedProgram = newEntry; // Set old entry to new entry
    }

    @Override
    public void onInputProgramTitleDialogPositiveClick(DialogFragment dialog, String newName) { // For title popup
        if (programTag.equals("DEFAULT") && programData.getProgramName().equals("")) { // Handle Condition if saved a new program
            programData.setProgramTag("CUSTOM");
            programData.setProgramName(newName);
            AddData();
            if (isExitable) {
                finish();
            }
        } else {
            programData.setProgramName(newName);
        }

        dialog.dismiss();
        setTitle(newName);
    }

    @Override
    public void onPromptExitDialogPositiveClick(DialogFragment dialog) {
        if (programTag.equals("DEFAULT") && programData.getProgramName().equals("")) { // Handle Condition if saved a new program and if has no name
            isExitable = true;
            InputProgramTitleDialogFragment inputProgramTitleDialogFragment = new InputProgramTitleDialogFragment();
            inputProgramTitleDialogFragment.show(getSupportFragmentManager(), "inputProgramTitleDialogFragment");

        } else {
            UpdateData();
            finish();
        }

        dialog.dismiss();
    }
}
