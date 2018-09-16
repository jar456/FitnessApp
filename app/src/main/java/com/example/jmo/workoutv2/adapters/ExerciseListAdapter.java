package com.example.jmo.workoutv2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.ProgramExercise;

import java.util.List;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ViewHolder> {

    private Context context;
    private List<ProgramExercise> list;
    private ExerciseListListener mListener;

    public ExerciseListAdapter(Context context, List<ProgramExercise> list, ExerciseListListener mListener) {
        this.context = context;
        this.list = list;
        this.mListener = mListener;
    }

    public interface ExerciseListListener {
        public void onExerciseClick(View view, final int position, ProgramExercise exercise);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_exercise, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String title = list.get(position).getExerciseName();
        String subtitle = list.get(position).getExerciseTarget();

        holder.title.setText(title);
        holder.subtitle.setText(subtitle);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onExerciseClick(view, position, list.get(position));
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, subtitle;
        public ImageView icon;
        public RelativeLayout parentLayout;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.txt_exerciseList_name);
            subtitle = view.findViewById(R.id.txt_exerciseList_subtitle);
            icon = view.findViewById(R.id.img_exerciseList_icon);
            parentLayout = view.findViewById(R.id.parentLayout_item_exerciseList);
        }
    }

    public void setList(List<ProgramExercise> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
