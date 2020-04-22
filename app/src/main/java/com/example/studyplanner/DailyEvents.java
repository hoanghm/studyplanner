package com.example.studyplanner;

import java.util.List;

public class DailyEvents {
    private String date;
    private List<EventDBObject> children;
    private int length;

    public DailyEvents(String date){
        this.date = date;
    }

    public void setChildren(List<EventDBObject> c){
        this.children=c;
        this.length = c.size();
    }

    public String getDate(){
        return this.date;
    }

    public List<EventDBObject> getChildren() {
        return children;
    }

    public int getNbChild(){
        return children.size();
    }

    public void sortTimes() {
        for (int i=0; i<this.length; i++) {
            int smallest_index = i;
            for (int j=i+1; j<this.length; j++) {
                if(this.children.get(j).smallerThan(this.children.get(smallest_index)))
                    smallest_index = j;
            }
            EventDBObject smallest_time = this.children.get(smallest_index);
            this.children.set(smallest_index, this.children.get(i));
            this.children.set(i, smallest_time);
        }
    }

    public int compare(DailyEvents o) {
        String[] cur_deadline = this.date.split("/");
        int cur_month = Integer.parseInt(cur_deadline[0]);
        int cur_day = Integer.parseInt(cur_deadline[1]);
        int cur_year = Integer.parseInt(cur_deadline[2]);

        String[] o_deadline = o.date.split("/");
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

    public boolean smallerThan(DailyEvents o) {
        int result = this.compare(o);
        if (result==-1)
            return true;
        else
            return false;
    }

}
