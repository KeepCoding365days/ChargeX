package com.example.chargex;

import java.sql.Time;

public class Slot {
    private String startTime;
    private String endTime;

    public Slot(){
        startTime="";
        endTime="";
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
