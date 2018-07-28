package com.example.jmo.workoutv2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jmo.workoutv2.R;
import com.example.jmo.workoutv2.data.ProgramData;

public class WeekSelectAdapter extends RecyclerView.Adapter<WeekSelectAdapter.ViewHolder>{

    private WeekSelectListener mListener;
    private Context context;
    private ProgramData programData;
    private int focusedWeek;

    public interface WeekSelectListener {
        public void onWeekSelectClick(final int position);
        public void onWeekAddClick(final int position);
    }

    public WeekSelectAdapter(ProgramData programData, Context context, WeekSelectListener mListener, int focusedWeek) {
        this.programData = programData;
        this.context = context;
        this.mListener = mListener;
        this.focusedWeek = focusedWeek;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        if (viewType == 0) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_week_button, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_week_addbutton, parent, false);
        }

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case 0: // Handles Week Button
                String weekText = context.getResources().getString(R.string.week_select) + " " + (position + 1);

                holder.weekButton.setText(weekText);

                if (focusedWeek == holder.getAdapterPosition()) {
                    holder.weekButton.setBackgroundResource(R.drawable.line_selected);
                } else {
                    holder.weekButton.setBackgroundResource(R.drawable.line_unselected);
                }

                holder.weekButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        focusedWeek = holder.getAdapterPosition();
                        notifyDataSetChanged();
                        mListener.onWeekSelectClick(focusedWeek);
                    }
                });
                holder.weekButton.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return false;
                    }
                });
                break;
            case 1: // Handles Add Button
                holder.addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        programData.addWeek();
                        focusedWeek = programData.getWeekSize() - 1;
                        notifyDataSetChanged();
                        mListener.onWeekAddClick(focusedWeek);
                    }
                });

                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < programData.getWeekSize()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return programData.getWeekSize() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public Button weekButton;
        public ImageButton addButton;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            weekButton = (Button) itemView.findViewById(R.id.btn_weekButton);
            addButton = (ImageButton) itemView.findViewById(R.id.btn_addWeekButton);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add(0, R.id.weekSelectHoldMenu_Edit, getAdapterPosition(), R.string.weekSelectLong_menu_edit);
            menu.add(0, R.id.weekSelectHoldMenu_Remove, getAdapterPosition(), R.string.weekSelectLong_menu_remove);
        }
    }
}
