package com.example.studyplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

public class EventExpListAdapter extends BaseExpandableListAdapter {

    Context context;
    List<DailyEvents> listGroups;

    public EventExpListAdapter(Context context, List<DailyEvents> listGroups){
        this.context=context;
        this.listGroups=listGroups;
    }

    @Override
    public int getGroupCount() {
        return listGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listGroups.get(groupPosition).getNbChild();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listGroups.get(groupPosition).getChildren();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listGroups.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group = listGroups.get(groupPosition).getDate();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.events_heading, null);
        }
        TextView textView = convertView.findViewById(R.id.event_date);
        textView.setText(group);
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String child = this.listGroups.get(groupPosition).getChildren().get(childPosition).getTitle();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.event_component, null);
        }
        TextView textView = convertView.findViewById(R.id.event_title);
        textView.setText(child);
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
