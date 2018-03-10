package com.example.ayush_harshit.bunkmaster.Adapters;

import android.widget.TextView;

/**
 * Created by ayush on 25/2/18.
 */

public class Subject {

    public String tvCourseCode;
    public String tvCourseName;
    public String tvCourseAttendance;

    public Subject(String tvCourseCode,String tvCourseName,String tvCourseAttendance){
        this.tvCourseCode = tvCourseCode;
        this.tvCourseName = tvCourseName;
        this.tvCourseAttendance = tvCourseAttendance;
    }

    public void setTvCourseCode(String tvCourseCode) {
        this.tvCourseCode = tvCourseCode;
    }

    public void setTvCourseName(String tvCourseName) {
        this.tvCourseName = tvCourseName;
    }

    public void setTvCourseAttendance(String tvCourseAttendance) {
        this.tvCourseAttendance = tvCourseAttendance;
    }

    public String getTvCourseCode(){
        return tvCourseCode;
    }

    public String getTvCourseName() {
        return tvCourseName;
    }

    public String getTvCourseAttendance() {
        return tvCourseAttendance;
    }
}
