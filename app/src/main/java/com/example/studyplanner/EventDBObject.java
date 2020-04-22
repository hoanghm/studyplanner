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

    public int compare_time(EventDBObject o) {
        String[] cur_time = this.time.split(":");
        int cur_hour = Integer.parseInt(cur_time[0]);
        int cur_minute = Integer.parseInt(cur_time[1]);

        String[] o_time = o.time.split(":");
        int o_hour = Integer.parseInt(o_time[0]);
        int o_minute = Integer.parseInt(o_time[1]);

        //compare hour
        if (cur_hour != o_hour)
            return Integer.compare(cur_hour, o_hour);
        else    //compare minute
            return Integer.compare(cur_minute, o_minute);
    }

    public boolean smallerThan(EventDBObject o) {
        int result = compare_time(o);
        if (result == -1)
            return true;
        else
            return false;
    }

}
