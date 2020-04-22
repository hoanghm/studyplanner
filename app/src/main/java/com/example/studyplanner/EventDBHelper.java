package com.example.studyplanner;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class EventDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "events.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_EVENT = "CREATE TABLE " + EventTable.EventEntry.TABLE_NAME + " (" +
            EventTable.EventEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
            EventTable.EventEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
            EventTable.EventEntry.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
            EventTable.EventEntry.COLUMN_DEADLINE + TEXT_TYPE + COMMA_SEP +
            EventTable.EventEntry.COLUMN_TIME + TEXT_TYPE + COMMA_SEP +
            EventTable.EventEntry.COLUMN_NOTES + TEXT_TYPE + " )";
    private Context context;

    private static final String SQL_DELETE = "DROP TABLE IF EXISTS " + EventTable.EventEntry.TABLE_NAME;

    public EventDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }


    public Cursor getDataAsCursor(String[] bind, boolean distinct){
        return this.getReadableDatabase().query(distinct,
                EventTable.EventEntry.TABLE_NAME,
                bind,
                null,
                null,
                null,
                null,
                EventTable.EventEntry.COLUMN_DEADLINE,
                null);
    }

    /**
     *
     * @return list of all DailyEvents containing each event.
     */
    public List<DailyEvents> getDailyEvents(){
        String[] dateBind = {EventTable.EventEntry.COLUMN_DEADLINE};
        Cursor c = getDataAsCursor(dateBind, true);
        List<DailyEvents> list = new ArrayList<>();
        if(c!=null && c.moveToFirst()){
            do{
                list.add(new DailyEvents(c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_DEADLINE))));
            }while(c.moveToNext());
        }
        c.close();

        String[] eventBind = {EventTable.EventEntry._ID,
                EventTable.EventEntry.COLUMN_TITLE,
                EventTable.EventEntry.COLUMN_DEADLINE,
                EventTable.EventEntry.COLUMN_TYPE,
                EventTable.EventEntry.COLUMN_NOTES,
                EventTable.EventEntry.COLUMN_TIME};
        c=getDataAsCursor(eventBind, false);
        if(c!=null && c.moveToFirst()){
          for(DailyEvents d: list){
              List<EventDBObject> children= new ArrayList<>();
              do{
                  if(d.getDate().equals(c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_DEADLINE)))){
                      children.add(new EventDBObject(c.getString(c.getColumnIndex(EventTable.EventEntry._ID)),
                                                     c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TITLE)),
                                                     c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_DEADLINE)),
                                                     c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TYPE)),
                                                     c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_NOTES)),
                                                     c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TIME))));
                  }
              }while(c.moveToNext());
              d.setChildren(children);
              d.sortTimes();
              c.moveToFirst();
          }
        }
        c.close();

        //sort the list by dates
        int list_length = list.size();
        for (int i=0; i<list_length; i++) {
            int smallest_index = i;
            for (int j=i+1; j<list_length; j++) {
                if(list.get(j).smallerThan(list.get(smallest_index)))
                    smallest_index = j;
            }
            DailyEvents smallest_date = list.get(smallest_index);
            list.set(smallest_index, list.get(i));
            list.set(i, smallest_date);
        }

        return list;

    }


    public List<CategoricalEvents> getCategoricalEvents() {

        Resources res = this.context.getResources();
        String[] types = res.getStringArray(R.array.event_types);

        Hashtable<String, CategoricalEvents> category_map = new Hashtable<>();
        for (String s: types) {
            category_map.put(s, new CategoricalEvents(s));
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EventTable.EventEntry.TABLE_NAME, null);
        if (c != null && c.moveToFirst()) {
            do {
                String cur_type = c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TYPE));
                EventDBObject cur_object = new EventDBObject(c.getString(c.getColumnIndex(EventTable.EventEntry._ID)),
                        c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TITLE)),
                        c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_DEADLINE)),
                        cur_type,
                        c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_NOTES)),
                        c.getString(c.getColumnIndex(EventTable.EventEntry.COLUMN_TIME)));

                category_map.get(cur_type).getChildren().add(cur_object);
            } while (c.moveToNext());
        }

        List<CategoricalEvents> events_by_type = new ArrayList<>();
        events_by_type.addAll(category_map.values());

        return events_by_type;
    }

}
