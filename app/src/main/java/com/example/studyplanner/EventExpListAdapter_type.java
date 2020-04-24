package com.example.studyplanner;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EventExpListAdapter_type extends BaseExpandableListAdapter {

    private Context context;
    private List<CategoricalEvents> listGroups;

    public EventExpListAdapter_type(Context context, List<CategoricalEvents> listGroups){
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
        String group = listGroups.get(groupPosition).getType();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.events_heading, null);
        }
        TextView textView = convertView.findViewById(R.id.event_date);
        textView.setText(group);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final EventDBObject child = this.listGroups.get(groupPosition).getChildren().get(childPosition);
        String child_title = child.getTitle();
        String child_time = child.getDeadline();
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.event_component, null);
        }
        TextView titleView = convertView.findViewById(R.id.event_title);
        TextView timeView = convertView.findViewById(R.id.event_time);
        titleView.setText(child_title);
        timeView.setText(context.getString(R.string.event_time_component, child_time));

        // Listener for editBtn and doneBtn
        Button editBtn = convertView.findViewById(R.id.edit_event);
        Button doneBtn = convertView.findViewById(R.id.done_event);
        final Context curContext = this.context;
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(curContext, EditEvent.class);
                editIntent.putExtra("EVENT_ID", child.get_ID());
                curContext.startActivity(editIntent);
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDBHelper dao = new EventDBHelper(curContext);
                SQLiteDatabase db = dao.getWritableDatabase();
                String sql_del = "DELETE FROM " + EventTable.EventEntry.TABLE_NAME + " WHERE " + EventTable.EventEntry._ID + " = " + child.get_ID();
                db.execSQL(sql_del);
                String toast_mes = "Event " + child.getTitle() + " deleted successfully.";
                Toast.makeText(curContext, toast_mes, Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(curContext, EventsManager.class);
                curContext.startActivity(refreshIntent);
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
