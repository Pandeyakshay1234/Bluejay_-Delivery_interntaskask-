package com.ExcelParser.pojo;

import java.util.Calendar;

public class Shift {

    private Calendar timeIn;

    private Calendar timeOut;

    private Integer minutes;


    public Calendar getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Calendar timeIn) {
        this.timeIn = timeIn;
    }

    public Calendar getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Calendar timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

//    @Override
//    public String toString() {
//
//        if(timeIn == null  || timeOut == null)
//              return  "Shift{" +
//                        ", minutes=" + minutes +
//                        '}';
//
//
//        return "Shift{" +
//                "timeIn=" + timeIn.getTime()  +
//                ", timeOut=" + timeOut.getTime() +
//                ", minutes=" + minutes +
//                '}';
//    }
}
