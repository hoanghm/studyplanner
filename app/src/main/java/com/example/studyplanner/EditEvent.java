package com.example.studyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Hashtable;

public class EditEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView titleView, typeView, deadlineView, timeView, notesView;
    RadioGroup typeRadio;
    String event_id, event_title, event_deadline, event_notes, event_time, event_type;
    EventDBHelper dao;
    SQLiteDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get Id of editing event
        event_id = getIntent().getStringExtra("EVENT_ID");

        //fill existing data into the textViews
        dao = new EventDBHelper(this.getApplicationContext());
        db = dao.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + EventTable.EventEntry.TABLE_NAME + " WHERE " + EventTable.EventEntry._ID + " = " + event_id, null);
        c.moveToFirst();
        event_title = c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TITLE));
        event_deadline = c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_DEADLINE));
        event_time = c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TIME));
        event_type = c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TYPE));
        event_notes = c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_NOTES));
        c.close();

        Hashtable<String,Integer> type_dict = new Hashtable<String, Integer>();
        type_dict.put("Homework", R.id.radioHomework);
        type_dict.put("Exam", R.id.radioExam);

        titleView = findViewById(R.id.titleView);
        typeRadio = findViewById(R.id.typeRadio);
        deadlineView = findViewById(R.id.deadlineView);
        timeView = findViewById(R.id.timeView);
        notesView = findViewById(R.id.notesView);

        titleView.setText(event_title);
        typeRadio.check(type_dict.getOrDefault(event_type,R.id.radioOthers));
        deadlineView.setText(event_deadline);
        timeView.setText(event_time);
        notesView.setText(event_notes);

        //get date and time of editing event to set default for datepicker, time picker
        String[] date = event_deadline.split("/");
        String[] time = event_time.split(":");
        final int event_month = Integer.parseInt(date[0]);
        final int event_day = Integer.parseInt(date[1]);
        final int event_year = Integer.parseInt(date[2]);
        final int event_hour = Integer.parseInt(time[0]);
        final int event_minute = Integer.parseInt(time[1]);

                // Date Picker dialog for deadline view
        deadlineView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showDatePickerDialog(event_month-1, event_day, event_year);
            }
        });
        deadlineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] cur_deadline = deadlineView.getText().toString().split("/");
                int year = Integer.parseInt(cur_deadline[2]);
                int month = Integer.parseInt(cur_deadline[0]);
                int day = Integer.parseInt(cur_deadline[1]);
                showDatePickerDialog(month-1, day, year);
            }
        });

        // Time picker dialog for time view
        timeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showTimePickerDialog(event_hour, event_minute);
            }
        });
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] cur_time = timeView.getText().toString().split(":");
                int hour = Integer.parseInt(cur_time[0]);
                int minute = Integer.parseInt(cur_time[1]);
                showTimePickerDialog(hour, minute);
            }
        });

        // when submitting a new event
        Button addBtn = findViewById(R.id.BtnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initiate EventDBHelper
                EventDBHelper dbHelper = new EventDBHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                //delete the old event before adding the updated version
                String sql_del = "DELETE FROM " + EventTable.EventEntry.TABLE_NAME + " WHERE " + EventTable.EventEntry._ID + " = " + event_id;
                db.execSQL(sql_del);

                typeView  = findViewById(typeRadio.getCheckedRadioButtonId());
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
                    toast_mes = "Event edited successfully";
                Toast.makeText(EditEvent.this, toast_mes, Toast.LENGTH_SHORT).show();

                //switch back to EventManager
                Intent intent = new Intent(getApplicationContext(), EventsManager.class);
                startActivity(intent);


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

    public void showDatePickerDialog(int month, int day, int year) {
        Calendar cal = Calendar.getInstance();
        Log.d("TimeErr", Integer.toString(year));
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                year,
                month,
                day);
        datePickerDialog.show();
    }

    public void showTimePickerDialog(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeView.setText(hourOfDay + ":" + minute);
                    }
                },
                hour,
                minute,
                false);
        timePickerDialog.show();
    }

    // after deadline was selected
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = month + "/" + dayOfMonth + "/" + year;
        deadlineView.setText(date);
    }

}
