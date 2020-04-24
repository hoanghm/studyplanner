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

    public int compare_date(EventDBObject o) {
        String[] cur_deadline = this.deadline.split("/");
        int cur_month = Integer.parseInt(cur_deadline[0]);
        int cur_day = Integer.parseInt(cur_deadline[1]);
        int cur_year = Integer.parseInt(cur_deadline[2]);

        String[] o_deadline = o.deadline.split("/");
        int o_month = Integer.parseInt(o_deadline[0]);
        int o_day = Integer.parseInt(o_deadline[1]);
        int o_year = Integer.parseInt(o_deadline[2]);

        //compare year
        if (cur_year != o_year)
            return Integer.compare(cur_year, o_year);
        else {
            //compare month
            if (cur_month != o_month)
                return Integer.compare(cur_month, o_month);
            else {
                //compare day
                return Integer.compare(cur_day, o_day);
            }
        }

    }

    public boolean timeSmallerThan(EventDBObject o) {
        int result = compare_time(o);
        if (result == -1)
            return true;
        else
            return false;
    }

    public boolean dateSmallerThan(EventDBObject o) {
        int result = compare_date(o);
        if (result == -1)
            return true;
        else
            return false;
    }

}
