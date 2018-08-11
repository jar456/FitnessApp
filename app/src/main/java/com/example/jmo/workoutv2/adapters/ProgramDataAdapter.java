package com.example.jmo.workoutv2.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jmo.workoutv2.data.ProgramExercise;
import com.example.jmo.workoutv2.R;

import java.util.HashMap;
import java.util.List;

public class ProgramDataAdapter extends BaseExpandableListAdapter{

    private List<Integer> headerData;
    private HashMap<Integer, List<ProgramExercise>> childData;
    private Context context;
    private LayoutInflater mInflater;
    private Boolean isEditable;
    private List<String> dayTitleList;

    ProgramDataListener mListener;

    public interface ProgramDataListener {
        public void onAddExerciseClick(View v, int groupPosition);
        public void onExpandDayClick(View v, int groupPosition);
        public void onRemoveExerciseClick(View v, int groupPosition, int childPosition);
    }

    public ProgramDataAdapter(Context context, List<Integer> headerData, HashMap<Integer, List<ProgramExercise>> childItem, boolean isEditable, List<String> dayTitleList, ProgramDataListener mListener) {
        this.context = context;
        this.headerData = headerData;
        this.childData = childItem;
        this.isEditable = isEditable;
        this.dayTitleList = dayTitleList;
        this.mListener = mListener;
    }

    @Override
    public ProgramExercise getChild(int groupPosition, int childPosition) {
        return this.childData.get(this.headerData.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.childData.get(this.headerData.get(groupPosition)).size();
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (getChildrenCount(groupPosition) < 1) {
            return convertView;
        }

        final String childHeaderText = (String) getChild(groupPosition, childPosition).getExerciseName();
        final String childRepsText = (String) Integer.toString(getChild(groupPosition, childPosition).getNumReps()) + (" Reps");
        final String childSetsText = (String) Integer.toString(getChild(groupPosition, childPosition).getNumSets()) + (" Sets");

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_item_programdata, null);
        }

        TextView textViewHeader = (TextView) convertView.findViewById(R.id.txt_exerciseHeader);
        TextView textViewExtra = (TextView) convertView.findViewById(R.id.txt_exerciseExtra);

        textViewHeader.setText(childHeaderText);
        textViewExtra.setText(childSetsText + " by " + childRepsText);

        ImageButton removeExerciseButton = (ImageButton) convertView.findViewById(R.id.btn_removeExercise);
        removeExerciseButton.setFocusable(false);

        if (isEditable) {
            removeExerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onRemoveExerciseClick(view, groupPosition, childPosition);
                }
            });
        } else {
            removeExerciseButton.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public Object getGroup (int groupPosition) {
        return this.headerData.get(groupPosition);
    }

    @Override
    public long getGroupId (int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return this.headerData.size();
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableListView mExpandableListView = (ExpandableListView) parent;

        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;    // Disable expansion onClick
            }
        });

        if (childData.get(getGroup(groupPosition)) != null) {   // Only expand if group has items
            mExpandableListView.expandGroup(groupPosition);     // else expand everything
        }

        String headerTitle = (String) "Day " + getGroup(groupPosition) + " " + dayTitleList.get(groupPosition);
        if (childData.get(getGroup(groupPosition)) == null) {
            headerTitle = headerTitle + " - Rest";
        }

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_header_daybar, null);
        }

        TextView textDayBar = (TextView) convertView.findViewById(R.id.txt_dayBar);
        textDayBar.setTypeface(null, Typeface.BOLD);
        textDayBar.setText(headerTitle);

        Button addExerciseButton = (Button) convertView.findViewById(R.id.btn_addExercise);
        ImageButton dayExpandButton = (ImageButton) convertView.findViewById(R.id.btn_expandDay);

        if (isEditable) {
            addExerciseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onAddExerciseClick(view, groupPosition);
                }
            });

            dayExpandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onExpandDayClick(view, groupPosition);
                }
            });

        } else {
            addExerciseButton.setVisibility(View.INVISIBLE);
            dayExpandButton.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
