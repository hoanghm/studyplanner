package com.example.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;


public class EventsManager extends AppCompatActivity {
    EventDBHelper dao;
    List<DailyEvents> daily_events;
    EventExpListAdapter_date daily_adapter;
    ExpandableListView list;
    List<CategoricalEvents> categorical_events;
    EventExpListAdapter_type categorical_adapter;
    Boolean sortByDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new EventDBHelper(this.getApplicationContext());

        daily_events = dao.getDailyEvents();
        categorical_events = dao.getCategoricalEvents();
        daily_adapter = new EventExpListAdapter_date(this, daily_events);
        categorical_adapter = new EventExpListAdapter_type(this, categorical_events);

        sortByDate = true;

        list = findViewById(R.id.eventList);
        list.setAdapter(daily_adapter);

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
                break;

            case(R.id.switch_sort):
                TextView switch_button = findViewById(R.id.switch_sort);
                if (sortByDate) {
                    list.setAdapter(categorical_adapter);
                    switch_button.setText("Sort by date");
                    sortByDate = false;
                }
                else {
                    list.setAdapter(daily_adapter);
                    switch_button.setText("Sort by type");
                    sortByDate = true;
                }
                break;

            case(R.id.homePage):
                Intent homeIntent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(homeIntent);
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

}
