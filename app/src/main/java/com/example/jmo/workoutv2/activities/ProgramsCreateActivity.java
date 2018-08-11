package com.example.jmo.workoutv2.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.adapters.WeekSelectAdapter;
import com.example.jmo.workoutv2.databases.DatabaseProgramHelper;
import com.example.jmo.workoutv2.data.ProgramData;
import com.example.jmo.workoutv2.dialogFragments.InputProgramTitleDialogFragment;
import com.example.jmo.workoutv2.dialogFragments.PromptExitDialogFragment;
import com.example.jmo.workoutv2.fragments.ProgramDataFragment;
import com.example.jmo.workoutv2.template.programs.PresetPrograms;
import com.google.gson.Gson;

public class ProgramsCreateActivity extends AppCompatActivity implements InputProgramTitleDialogFragment.InputProgramTitleDialogListener,
        PromptExitDialogFragment.PromptExitDialogListener, WeekSelectAdapter.WeekSelectListener {
    DatabaseProgramHelper mDatabaseHelper;

    private String programName; // Primarily used if loaded from a new program template
    private String programTag; // Primarily used if loaded from a new program template

    private int focusedWeek = 0; // Track the week user is focused on

    private ProgramData programData;

    private String selectedProgram; // Used for updating
    private int selectedID; // Used for updating

    private final static boolean isEditable = true;
    private final static String SHARED_PREF_KEY = "num_programs"; // Used for unique program modifier
    private final static String DEFAULT_TAG = "DEFAULT"; // Used for unique program modifier
    private final static String CUSTOM_TAG = "CUSTOM"; // Used for unique program modifier

    private RecyclerView recyclerView;
    private WeekSelectAdapter adapter;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_edit);

        mDatabaseHelper = new DatabaseProgramHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_programEdit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_save);

        // Check database for imformation... if not create DEFAULT
        getIncomingIntent();

        parentLayout = findViewById(android.R.id.content);

        // If 1st time creation
        if (programTag.equals(DEFAULT_TAG)) {
            programData = new ProgramData("", programTag);
            PresetPrograms.CreateTemplateProgram(programData, programName, getApplicationContext()); // Note null program name
        }

        if (programName != null) {
            setTitle(programName);
        }

        CreateRecyclerView();

        // Pass the data to fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
        ft.add(R.id.fragment_container_programEdit_week, programDataFragment).commit();

    }

    private void CreateRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recyclerView_weekButton);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WeekSelectAdapter(programData,this, this, focusedWeek);
        recyclerView.setAdapter(adapter);
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
                if (programData.getProgramTag().equals(DEFAULT_TAG)) {

                    if (programData.getProgramName().equals("")) {
                        // Pass isExitable and isSaveable
                        final boolean isExitable = false;
                        final boolean isSaveable = true;

                        InputProgramTitleDialogFragment dialog = InputProgramTitleDialogFragment.newInstance(isExitable, isSaveable);
                        dialog.show(getSupportFragmentManager(), "InputProgramTitleDialogFragment");
                    } else {
                        AddData(); // This means the user already enter a program name
                    }

                } else if (programData.getProgramTag().equals(CUSTOM_TAG)) {
                    UpdateData();
                    Snackbar.make(findViewById(R.id.CoordinatorLayout_programEdit), "Successfully Saved", Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.programCreatorMenu_setTitle:
                final boolean isExitable = false;
                final boolean isSaveable = false;

                InputProgramTitleDialogFragment dialog = InputProgramTitleDialogFragment.newInstance(isExitable, isSaveable);
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

    @Override // GET PAREMETERS isExitable and isSaveable
    public void onInputProgramTitleDialogPositiveClick(DialogFragment dialog, String newName, boolean isExitable, boolean isSaveable) { // For title popup
        if (programTag.equals(DEFAULT_TAG) && programData.getProgramName().equals("")) { // Handle Condition if saved a new program
            programData.setProgramName(newName);

            if (isSaveable) {
                programData.setProgramTag(CUSTOM_TAG);
                AddData();
            }

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
        final boolean isExitable = true;
        final boolean isSaveable = true;

        if (programTag.equals(DEFAULT_TAG) && programData.getProgramName().equals("")) { // Handle Condition if saved a new program and if has no name
            // pass isExitable + isSaveable
            InputProgramTitleDialogFragment inputProgramTitleDialogFragment = InputProgramTitleDialogFragment.newInstance(isExitable, isSaveable);
            inputProgramTitleDialogFragment.show(getSupportFragmentManager(), "inputProgramTitleDialogFragment");
        } else {
            UpdateData();
            finish();
        }

        dialog.dismiss();
    }

    @Override
    public void onWeekSelectClick(final int position) {
        if (focusedWeek != position) {
            focusedWeek = position;

            ReplaceWeekFragment();
        }
    }

    @Override
    public void onWeekAddClick(final int position) {
        focusedWeek = position;

        ReplaceWeekFragment();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.weekSelectHoldMenu_Edit:
                return true;
            case R.id.weekSelectHoldMenu_Remove:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.popupMessage_removeWeek)
                        .setPositiveButton(getResources().getString(R.string.choice_yes), new RemoveExerciseOnClickListener(item.getOrder()))
                        .setNegativeButton(getResources().getString(R.string.choice_no), null);
                builder.create().show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public class RemoveExerciseOnClickListener implements DialogInterface.OnClickListener {
        private int position;

        private RemoveExerciseOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            Snackbar.make(parentLayout, position + "", Snackbar.LENGTH_SHORT).show();
            if (programData.getWeekSize() > 1) {
                programData.removeWeek(position);
                if (position == focusedWeek && focusedWeek == 0) {
                    ReplaceWeekFragment();
                } else if (position == focusedWeek && focusedWeek == programData.getWeekSize() - 1) {
                    focusedWeek--;
                    ReplaceWeekFragment();
                }  else if (position == focusedWeek) {
                    focusedWeek--;
                    ReplaceWeekFragment();
                } else if (position != focusedWeek && position == 0) {
                    focusedWeek--;
                } else {
                    ReplaceWeekFragment();
                }
                CreateRecyclerView();
            }
        }
    }

    public void ReplaceWeekFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment programDataFragment = ProgramDataFragment.newInstance(programData, focusedWeek, isEditable);
        ft.replace(R.id.fragment_container_programEdit_week, programDataFragment).commit();
    }
}
