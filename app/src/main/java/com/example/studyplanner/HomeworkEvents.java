package com.example.studyplanner;

import java.util.List;

public class HomeworkEvents {
    private String date;
    private List<EventDBObject> children;

    public HomeworkEvents(String date){
        this.date = date;
    }

    public void setChildren(List<EventDBObject> c){
        this.children=c;
    }

    public String getDate(){
        return date;
    }

    public List<EventDBObject> getChildren() {
        return children;
    }

    public int getNbChild(){
        return children.size();
    }
}
