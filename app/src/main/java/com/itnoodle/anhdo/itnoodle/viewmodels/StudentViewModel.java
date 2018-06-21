package com.itnoodle.anhdo.itnoodle.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.widget.ProgressBar;

import com.itnoodle.anhdo.itnoodle.Profile;
import com.itnoodle.anhdo.itnoodle.models.StudentInfo;

public class StudentViewModel extends ViewModel {
    public static final String LOG_TAG = StudentInfo.class.getSimpleName();
//    private String stdCode, term, year;
    private final MutableLiveData<StudentInfo> studentInfo;
    public StudentViewModel() {
        studentInfo = new MutableLiveData<>();
        studentInfo.setValue(new StudentInfo());
    }
    public void init(String stdCode, String term, String year, Profile profile) {
        Log.i(LOG_TAG, "init new student information");
//        this.stdCode = stdCode;
//        this.term = term;
//        this.year = year;
        this.studentInfo.getValue().code = stdCode;
        this.studentInfo.getValue().term = term;
        this.studentInfo.getValue().year = year;
        this.studentInfo.getValue().loadInfo(profile);
    }

    public LiveData<StudentInfo> studentInfo() {
        return studentInfo;
    }

    public StudentInfo getInfo() {
        return studentInfo.getValue();
    }
}
