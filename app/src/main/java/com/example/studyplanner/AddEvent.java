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
import android.widget.Toast;

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
        final TextView typeView = findViewById(R.id.typeView);
        final TextView deadlineView = findViewById(R.id.deadlineView);
        final TextView timeView = findViewById(R.id.timeView);
        final TextView notesView = findViewById(R.id.notesView);

        Button addBtn = findViewById(R.id.BtnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initiate EventDBHelper
                EventDBHelper dbHelper = new EventDBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //get values from the add form
                values.put(EventTable.EventEntry.COLUMN_TITLE, titleView.getText().toString());
                values.put(EventTable.EventEntry.COLUMN_TYPE, typeView.getText().toString());
                values.put(EventTable.EventEntry.COLUMN_DEADLINE, deadlineView.getText().toString());
                values.put(EventTable.EventEntry.COLUMN_TIME, timeView.getText().toString());
                values.put(EventTable.EventEntry.COLUMN_NOTES, notesView.getText().toString());

                //insert values into the database
                long newRowId = db.insert(
                        EventTable.EventEntry.TABLE_NAME,
                        null,   //all columns
                        values);

                //check insertion and toast
                String toast_mes;
                if (newRowId == -1)
                    toast_mes = "ERROR";
                else
                    toast_mes = "New event added";
                Toast.makeText(AddEvent.this, toast_mes, Toast.LENGTH_SHORT).show();

                //clear input fields
                titleView.setText("");
                typeView.setText("");
                deadlineView.setText("");
                timeView.setText("");
                notesView.setText("");

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
        int id = item.getItemId();

        if (id == R.id.eventManager) {
            Intent intent = new Intent(getApplicationContext(), EventsManager.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


}
