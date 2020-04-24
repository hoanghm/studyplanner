package com.example.studyplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
    List<EventDBObject> events;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new EventDBHelper(this);
        events = dao.getTodayEvents();
        HomePageAdapter adapter = new HomePageAdapter(this, events);

        list = findViewById(R.id.eventList);
        list.setAdapter(adapter);

        TextView emptyView = findViewById(R.id.empty);
        list.setEmptyView(emptyView);
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
