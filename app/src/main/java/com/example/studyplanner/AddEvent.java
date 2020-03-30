package com.example.studyplanner;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView titleView = findViewById(R.id.titleView);
        TextView typeView = findViewById(R.id.typeView);
        TextView deadlineView = findViewById(R.id.deadlineView);
        TextView timeView = findViewById(R.id.timeView);
        TextView notesView = findViewById(R.id.notesView);

        Button addBtn = findViewById(R.id.BtnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create EventDBHelper
                EventDBHelper dbHelper = new EventDBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //get values from the add form
                values.put(EventTable.EventEntry.COLUMN_TITLE, titleView.getText().toString());
                values.put(EventTable.EventEntry.COLUMN_TYPE, titleView.getText().toString());
                values.put(EventTable.EventEntry.COLUMN_TITLE, titleView.getText().toString());
                values.put(EventTable.EventEntry.COLUMN_TITLE, titleView.getText().toString());

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.eventManager:
                Intent intent = new Intent(getApplicationContext(), EventsManager.class);
                startActivity(intent);
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
