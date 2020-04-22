package com.example.studyplanner;

import java.util.ArrayList;
import java.util.List;

public class CategoricalEvents {
    private String type;
    private List<EventDBObject> children;

    public CategoricalEvents(String type){
        this.type = type;
        this.children = new ArrayList<>();
    }

    public void setChildren(List<EventDBObject> c){
        this.children=c;
    }

    public String getType(){
        return this.type;
    }

    public List<EventDBObject> getChildren() {
        return this.children;
    }

    public int getNbChild(){
        return children.size();
    }

}
