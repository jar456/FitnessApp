package com.example.jmo.workoutv2.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.activities.ProgramsCreateActivity;
import com.example.jmo.workoutv2.adapters.ManageProgramsListAdapter;
import com.example.jmo.workoutv2.data.DatabaseProgramHelper;
import com.example.jmo.workoutv2.data.ProgramData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ManageProgramsFragment extends Fragment implements ManageProgramsListAdapter.ManageProgramsListListener {

    DatabaseProgramHelper mDatabaseHelper;
    private RecyclerView recyclerView;
    private ManageProgramsListAdapter adapter;
    private List<ProgramData> programDataList;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manageprograms, container, false);

        mDatabaseHelper = new DatabaseProgramHelper(getContext());
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_managePrograms);
        programDataList = new ArrayList<>();
        adapter = new ManageProgramsListAdapter(getActivity(), programDataList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.row_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        populateListView();


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.title_ProgramManager);
    }

    private void populateListView() {
        Gson gson = new Gson();
        Cursor data = mDatabaseHelper.getData();

        while (data.moveToNext()) {
            String program_json = data.getString(1);
            programDataList.add(gson.fromJson(program_json, ProgramData.class));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickExpandItem(final View view, final int position) {
        {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    Gson gson = new Gson();
                    String program_json = gson.toJson(programDataList.get(position));

                    switch (menuItem.getItemId()) {
                        case R.id.programManagerMenu_EditProgram:
                            Snackbar.make(view, "EDIT  ", Snackbar.LENGTH_LONG).show();
                            Intent intent = new Intent(getActivity(), ProgramsCreateActivity.class);
                            intent.putExtra("program_json", program_json);
                            getActivity().startActivity(intent);
                            return true;
                        case R.id.programManagerMenu_DeleteProgram:
                            Snackbar.make(view, "DELETE", Snackbar.LENGTH_LONG).show();
                            Cursor data = mDatabaseHelper.getItemID(program_json);
                            int itemID = -1;
                            while(data.moveToNext()) {
                                itemID = data.getInt(0);
                            }
                            if (itemID != -1) {
                                mDatabaseHelper.deleteName(itemID, program_json);
                                programDataList.remove(position);
                                adapter.notifyDataSetChanged();
                            }

                            return true;
                    }
                    return false;
                }
            });
            popupMenu.inflate(R.menu.program_manager_menu);
            popupMenu.show();
        }
    }

    public String jsonToString(ProgramData programData) {
        String asd = "asd";
        return asd;

    }



}
