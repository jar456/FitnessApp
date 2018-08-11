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
import com.example.jmo.workoutv2.data.ExerciseTypes;

import java.util.List;

public class ExerciseSelectAdapter extends RecyclerView.Adapter<ExerciseSelectAdapter.ViewHolder> {

    private Context mContext;
    private List<ExerciseTypes> list;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ExerciseTypes data, View view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;
        public TextView title;
        public RelativeLayout parentLayout;

        public ViewHolder (View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.txt_exerciseSelect);
            thumbnail = (ImageView) view.findViewById(R.id.img_exerciseSelect);
            parentLayout = (RelativeLayout) view.findViewById(R.id.parentLayout_cV_exerciseSelect);
        }
    }

    public ExerciseSelectAdapter(List<ExerciseTypes> typesList, Context mContext, OnItemClickListener listener) {
        this.list = typesList;
        this.mContext = mContext;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_exercise_selection, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ExerciseTypes data = list.get(position);

        holder.title.setText(data.getTitle());
        holder.thumbnail.setImageDrawable(data.getImg());
        holder.thumbnail.setColorFilter(mContext.getResources().getColor(R.color.colorExerciseIcon));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(data, view);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
