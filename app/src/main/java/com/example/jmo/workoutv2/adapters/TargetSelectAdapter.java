package com.example.jmo.workoutv2.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jmo.workoutv2.R;

import java.util.List;

public class TargetSelectAdapter extends RecyclerView.Adapter<TargetSelectAdapter.ViewHolder> {

    private List<String> list;
    private int focusedTarget = 0;

    public TargetSelectAdapter(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_target_button, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String targetText = list.get(position);
        holder.targetButton.setText(targetText);

        if (focusedTarget == holder.getAdapterPosition()) {
            holder.targetButton.setBackgroundResource(R.drawable.line_selected);
        } else {
            holder.targetButton.setBackgroundResource(R.drawable.line_unselected);
        }

        holder.targetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusedTarget = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Button targetButton;

        public ViewHolder(View itemView) {
            super(itemView);
            targetButton = itemView.findViewById(R.id.btn_targetButton);
        }


    }
}
