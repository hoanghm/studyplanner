package com.example.studyplanner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView titleView, typeView, deadlineView, timeView, notesView;
    RadioGroup typeRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleView = findViewById(R.id.titleView);
        typeRadioGroup = findViewById(R.id.typeRadio);
        deadlineView = findViewById(R.id.deadlineView);
        timeView = findViewById(R.id.timeView);
        notesView = findViewById(R.id.notesView);

        // Date Picker dialog for deadline view
        deadlineView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar cal = Calendar.getInstance();
                    showDatePickerDialog(cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH));
                }
            }
        }); //2nd time onclick
        deadlineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] cur_deadline = deadlineView.getText().toString().split("/");
                int year = Integer.parseInt(cur_deadline[2]);
                int month = Integer.parseInt(cur_deadline[0]);
                int day = Integer.parseInt(cur_deadline[1]);
                showDatePickerDialog(year, month, day);
            }
        });

        // Time picker dialog for time view
        timeView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    Calendar cal = Calendar.getInstance();
                    showTimePickerDialog(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
                }
            }
        }); //2nd time onclick
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

                typeView = findViewById(typeRadioGroup.getCheckedRadioButtonId());
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
                deadlineView.setText("");
                timeView.setText("");
                notesView.setText("");

                finish();

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

    public void showDatePickerDialog(int year, int month, int day) {
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
                        String res="";
                        if(hourOfDay<10){
                            res += "0"+hourOfDay;
                        }
                        else{
                            res+= hourOfDay;
                        }
                        res+=":";
                        if(minute<10){
                            res += "0"+minute;
                        }
                        else{
                            res+= minute;
                        }
                        timeView.setText(res);
                    }
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false);
        timePickerDialog.show();
    }

    // after deadline was selected
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = (month+1) + "/" + dayOfMonth + "/" + year;
        deadlineView.setText(date);
    }
}
