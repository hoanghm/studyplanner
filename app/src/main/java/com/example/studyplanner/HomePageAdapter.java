package com.example.studyplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomePageAdapter extends ArrayAdapter<EventDBObject> {

    public HomePageAdapter(Context context, List<EventDBObject> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final EventDBObject event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.home_event_layout, parent, false);
        }
        // Lookup view for data population
        TextView titleView = convertView.findViewById(R.id.event_title);
        TextView typeView = convertView.findViewById(R.id.event_type);
        TextView timeView = convertView.findViewById(R.id.event_time);
        Button notesButton = convertView.findViewById(R.id.event_notes_button);
        // Populate the data into the template view using the data object
        titleView.setText(event.getTitle());
        typeView.setText(event.getType());
        timeView.setText(event.getTime());

        final Context cur_context = this.getContext();
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = event.getNotes();
                if(mes.equals(""))
                    mes = "No notes";
                Toast.makeText(cur_context, mes, Toast.LENGTH_SHORT).show();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}
