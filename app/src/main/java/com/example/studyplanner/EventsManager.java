package com.example.studyplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;


public class EventsManager extends AppCompatActivity {
    EventDBHelper dao;
    List<DailyEvents> events;
    EventExpListAdapter adapter;
    ExpandableListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new EventDBHelper(this.getApplicationContext());
        events = dao.getDailyEvents();


        adapter = new EventExpListAdapter(this, events);
        list = findViewById(R.id.eventList);
        list.setAdapter(adapter);

        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                EventDBObject selected = events.get(groupPosition).getChildren().get(childPosition);
                Toast toast = Toast.makeText(getApplicationContext(), selected.getNotes(), Toast.LENGTH_SHORT);
                toast.show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.addEvent:
                Intent intent = new Intent(getApplicationContext(), AddEvent.class);
                startActivity(intent);
                finish();
                return true;

            case(R.id.clearDatabase):
                EventDBHelper myDbHelper = new EventDBHelper(getApplicationContext());
                SQLiteDatabase db = myDbHelper.getWritableDatabase();
                db.delete(EventTable.EventEntry.TABLE_NAME,"1",null);
                Toast.makeText(this, "Database cleared", Toast.LENGTH_SHORT).show();
                Intent refresh_intent = new Intent(getApplicationContext(), EventsManager.class);
                startActivity(refresh_intent);
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
