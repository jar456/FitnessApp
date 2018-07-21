package com.example.jmo.workoutv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    // Provides reference to the views for each data item
    public interface ManageProgramsListListener {
        public void onClickExpandItem(final View view, final int position);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ConstraintLayout parentLayout;
        public ImageButton expandItemButton;


        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_ProgramTitle);
            subtitle = (TextView) view.findViewById(R.id.txt_ProgramSubtitle);
            parentLayout = (ConstraintLayout) view.findViewById(R.id.parent_layout_programManager);
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_programmanger, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ProgramData programData = programDataList.get(position);
        holder.title.setText(programData.getProgramName());
        holder.subtitle.setText(Integer.toString(programData.getProgramID()));

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
    }

    @Override
    public int getItemCount() {
        return programDataList.size();
    }
}
