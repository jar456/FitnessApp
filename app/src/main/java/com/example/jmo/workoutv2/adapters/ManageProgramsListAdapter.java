package com.example.jmo.workoutv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.activities.ProgramViewActivity;
import com.example.jmo.workoutv2.data.ProgramData;
import com.google.gson.Gson;

import java.util.List;

public class ManageProgramsListAdapter extends RecyclerView.Adapter<ManageProgramsListAdapter.ViewHolder> {
    private Context mContext;
    private List<ProgramData> programDataList;
    private ManageProgramsListListener mListener;

    private static final int VIEW_TYPE_EMPTY_LIST = 0;
    private static final int VIEW_TYPE_OBJECT_VIEW = 1;
    // Provides reference to the views for each data item
    public interface ManageProgramsListListener {
        public void onClickExpandItem(final View view, final int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle, week;
        public RelativeLayout parentLayout;
        public ImageButton expandItemButton;


        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_cV_myPrograms_title);
            subtitle = (TextView) view.findViewById(R.id.txt_cV_myPrograms_type);
            week = (TextView) view.findViewById(R.id.txt_cV_myPrograms_week);
            parentLayout = (RelativeLayout) view.findViewById(R.id.parent_layout_cV_myPrograms);
            expandItemButton = (ImageButton) view.findViewById(R.id.btn_expandProgramItem);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            });
        }
    };

    public ManageProgramsListAdapter(Context mContext, List<ProgramData> programDataList, ManageProgramsListListener mListener) {
        this.mContext = mContext;
        this.programDataList = programDataList;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        if (viewType == VIEW_TYPE_EMPTY_LIST) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_myprograms_create, parent, false);
            return new ViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card_myprograms, parent, false);
            return new ViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_EMPTY_LIST:
                break;
            case VIEW_TYPE_OBJECT_VIEW:
                final ProgramData programData = programDataList.get(position);
                String txt_title = programData.getProgramName();
                String txt_subtitle = Integer.toString(programData.getProgramID()) + " " + programData.getProgramTag();
                String txt_week = Integer.toString(programData.getWeekSize()) + " Week Program";

                holder.title.setText(txt_title);
                holder.subtitle.setText(txt_subtitle);
                holder.week.setText(txt_week);

                holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Click Work", Snackbar.LENGTH_LONG).show();

                        Gson gson = new Gson();
                        String program_json = gson.toJson(programData);
                        Intent intent = new Intent(mContext, ProgramViewActivity.class);
                        intent.putExtra("program_json", program_json);
                        mContext.startActivity(intent);

                    }
                });

                holder.expandItemButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        mListener.onClickExpandItem(view, position);
                    }
                });

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (programDataList.isEmpty()) {
            return VIEW_TYPE_EMPTY_LIST;
        } else {
            return VIEW_TYPE_OBJECT_VIEW;
        }
    }

    @Override
    public int getItemCount() {
        if (programDataList.isEmpty()) {
            return 1;
        } else {
            return programDataList.size();
        }
    }
}
