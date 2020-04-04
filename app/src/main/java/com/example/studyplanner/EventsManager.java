package com.example.studyplanner;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EventsManager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EventDBHelper dao = new EventDBHelper(this.getApplicationContext());
        SQLiteDatabase db = dao.getReadableDatabase();

        String[] projection = {
                EventTable.EventEntry.COLUMN_DEADLINE,
                EventTable.EventEntry.COLUMN_NOTES,
                EventTable.EventEntry.COLUMN_TIME,
                EventTable.EventEntry.COLUMN_TITLE,
                EventTable.EventEntry.COLUMN_TYPE
        };

        String[] bind = {
                EventTable.EventEntry._ID,
                EventTable.EventEntry.COLUMN_DEADLINE,
                EventTable.EventEntry.COLUMN_NOTES,
                EventTable.EventEntry.COLUMN_TIME,
                EventTable.EventEntry.COLUMN_TITLE,
                EventTable.EventEntry.COLUMN_TYPE
        };

        Cursor cursor = db.query(EventTable.EventEntry.TABLE_NAME,
                bind,
                null,
                null,
                EventTable.EventEntry.COLUMN_DEADLINE,
                null,
                EventTable.EventEntry.COLUMN_DEADLINE+" ASC");

        int[] to = new int[]{
                R.id.date,
                R.id.event_title
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.event_list_component, cursor, projection, to, 0);

        final ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);

        TextView emptyView = findViewById(android.R.id.empty);
        list.setEmptyView(emptyView);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                String selectedItem = (String) cursor.getString(cursor.getColumnIndex(EventTable.EventEntry.COLUMN_TITLE));

                Toast toast = Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT);
                toast.show();
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
            //break;
        }


        return super.onOptionsItemSelected(item);
    }

}
