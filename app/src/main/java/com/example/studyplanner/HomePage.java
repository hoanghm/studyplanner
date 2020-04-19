package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomePage extends AppCompatActivity {
    Spinner typeSelection;
    EventDBHelper dao;
    Cursor events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializes the spinner (android combobox)
        typeSelection = (Spinner) findViewById(R.id.typeSelection);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.typeSelection_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelection.setAdapter(spinnerAdapter);

        dao = new EventDBHelper(this.getApplicationContext());
        Date currentDay = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/YYYY");
        String dateString = sdf.format(currentDay); //current saved format is
        System.out.println("[DEBUG] "+dateString);
        String[] eventBind = {EventTable.EventEntry._ID,
                EventTable.EventEntry.COLUMN_TITLE,
                EventTable.EventEntry.COLUMN_DEADLINE,
                EventTable.EventEntry.COLUMN_TYPE,
                EventTable.EventEntry.COLUMN_NOTES,
                EventTable.EventEntry.COLUMN_TIME};
        String whereClause = EventTable.EventEntry.COLUMN_DEADLINE+" = ?";
        String[] whereArgs = {dateString};
        events = dao.getReadableDatabase().query(EventTable.EventEntry.TABLE_NAME,
                eventBind,
                whereClause,
                whereArgs,
                null,
                null,
                EventTable.EventEntry.COLUMN_TIME+" ASC");

        String[] projection = {
                EventTable.EventEntry.COLUMN_TITLE,
                EventTable.EventEntry.COLUMN_TYPE,
                EventTable.EventEntry.COLUMN_TIME};
        int[] to = {
                R.id.event_title,
                R.id.event_type,
                R.id.event_time
        };

        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.home_event_layout, events, projection, to, 0);
        final ListView list = (ListView) findViewById(R.id.eventList);
        list.setAdapter(listAdapter);

        TextView emptyView = findViewById(R.id.empty);
        list.setEmptyView(emptyView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);

                EventDBObject selected = new EventDBObject(c.getString(c.getColumnIndex(EventTable.EventEntry._ID)),
                                                            c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TITLE)),
                                                            c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_DEADLINE)),
                                                            c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TYPE)),
                                                            c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_NOTES)),
                                                            c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TIME)));
                Toast.makeText(getApplicationContext(), selected.getNotes(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //TODO: change menu to something more fitting. maybe add a toolbox?
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.eventManager) {
            Intent intent = new Intent(getApplicationContext(), EventsManager.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.addEvent) {
            Intent intent = new Intent(getApplicationContext(), AddEvent.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);

    }
}
