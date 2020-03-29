package com.example.studyplanner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "events.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_EVENT = "CREATE TABLE" + EventTable.EventEntry.TABLE_NAME + " (" +
            EventTable.EventEntry._ID + " INTEGER PRIMRY KEY" + COMMA_SEP +
            EventTable.EventEntry.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
            EventTable.EventEntry.COLUMN_TYPE + TEXT_TYPE + COMMA_SEP +
            EventTable.EventEntry.COLUMN_DEADLINE + TEXT_TYPE + COMMA_SEP +
            EventTable.EventEntry.COLUMN_NOTES + TEXT_TYPE + COMMA_SEP + " )";

    private static final String SQL_DELETE = "DROP TABLE IF EXISTS" + EventTable.EventEntry.TABLE_NAME;

    public EventDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

}
