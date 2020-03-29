package com.example.studyplanner;


import android.provider.BaseColumns;

public final class EventTable {
    public EventTable() {
    }

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DEADLINE = "deadline";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_NOTES = "notes";
    }

}