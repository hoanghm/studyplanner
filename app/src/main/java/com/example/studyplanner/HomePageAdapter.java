package com.example.studyplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HomePageAdapter extends ArrayAdapter<EventDBObject> {
    public HomePageAdapter(Context context, List<EventDBObject> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        EventDBObject event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_event_layout, parent, false);
        }
        // Lookup view for data population
        TextView titleView = convertView.findViewById(R.id.event_title);
        TextView typeView = convertView.findViewById(R.id.event_type);
        TextView timeView = convertView.findViewById(R.id.event_time);
        // Populate the data into the template view using the data object
        titleView.setText(event.getTitle());
        typeView.setText(event.getType());
        timeView.setText(event.getTime());
        // Return the completed view to render on screen
        return convertView;
    }
}
