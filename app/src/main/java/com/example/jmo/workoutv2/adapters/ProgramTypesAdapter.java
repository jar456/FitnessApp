package com.example.jmo.workoutv2.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jmo.workoutv2.activities.ProgramsCreateActivity;
import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.ProgramTypes;

import java.util.List;

public class ProgramTypesAdapter extends RecyclerView.Adapter<ProgramTypesAdapter.ViewHolder> {

    private Context mContext;
    private List<ProgramTypes> programTypesList;
    // Provides reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public ImageView thumbnail;
        public RelativeLayout parentLayout;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.select_name);
            description = (TextView) view.findViewById(R.id.select_description);
            thumbnail = (ImageView) view.findViewById(R.id.select_thumbnail);
            parentLayout = (RelativeLayout) view.findViewById(R.id.parent_layout);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            });
        }
    };
    // Constructor dependent on the dataset, in this case (ProgramTypesList)
    public ProgramTypesAdapter(Context mContext, List<ProgramTypes> programTypesList) {
        this.programTypesList = programTypesList;
        this.mContext = mContext;
    }
    // Create new views (invoked by layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_card_programtypes, parent, false);
        return new ViewHolder(itemView);
    }
    // Replace contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ProgramTypes programTypes = programTypesList.get(position);
        holder.name.setText(programTypes.getName());
        holder.description.setText(programTypes.getDescription());
        holder.thumbnail.setImageResource(programTypes.getThumbnail());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgramTypes temp = programTypesList.get(position);
                Intent intent = new Intent(mContext, ProgramsCreateActivity.class);
                intent.putExtra("program_id", temp.getUniqueTag());
                intent.putExtra("program_name", temp.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return programTypesList.size();
    }

}
