package com.example.studyplanner;

public class EventDBObject {

    private String _ID;
    private String title;
    private String deadline;
    private String type;
    private String notes;
    private String time;

    public EventDBObject(String _ID, String title, String deadline, String type, String notes, String time) {
        this._ID=_ID;
        this.title = title;
        this.deadline = deadline;
        this.type = type;
        this.notes = notes;
        this.time = time;
    }

    public String get_ID(){
        return _ID;
    }

    public String getTitle(){
        return title;
    }

    public String getDeadline(){
        return deadline;
    }

    public String getType(){
        return type;
    }

    public String getNotes(){
        return notes;
    }

    public String getTime(){
        return time;
    }

}
