package com.itnoodle.anhdo.itnoodle.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.itnoodle.anhdo.itnoodle.models.StudentInfo;

public class StudentViewModel extends ViewModel {
//    private String stdCode, term, year;
    private final StudentInfo studentInfo = new StudentInfo();
    public void init(String stdCode, String term, String year) {
//        this.stdCode = stdCode;
//        this.term = term;
//        this.year = year;
        this.studentInfo.code = stdCode;
        this.studentInfo.term = term;
        this.studentInfo.year = year;
        this.studentInfo.loadInfo();
    }

    public StudentInfo studentInfo() {
        return studentInfo;
    }
}
