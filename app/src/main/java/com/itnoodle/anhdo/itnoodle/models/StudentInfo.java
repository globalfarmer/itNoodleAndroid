package com.itnoodle.anhdo.itnoodle.models;

import android.arch.lifecycle.LiveData;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.itnoodle.anhdo.itnoodle.AppExecutors;
import com.itnoodle.anhdo.itnoodle.MainActivity;
import com.itnoodle.anhdo.itnoodle.Profile;
import com.itnoodle.anhdo.itnoodle.dummy.CourseContent;
import com.itnoodle.anhdo.itnoodle.utilities.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentInfo {
    public static final String LOG_TAG = StudentInfo.class.getSimpleName();
    public static final String KEY_CODE = "code";
    public static final String KEY_FULLNAME = "fullname";
    public static final String KEY_KLASS = "klass";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_SLOTS = "slots";
    public static final String KEY_COURSE_CODES = "course_codes";
    public final List<CourseContent.CourseItem> items = new ArrayList<CourseContent.CourseItem>();
    public final Map<String, CourseContent.CourseItem> itemMap = new HashMap<String, CourseContent.CourseItem>();
    public String code;
    public String term;
    public String year;
    public String fullname;
    public String klass;
    public String birthday;
    public String email;
    public static RequestQueue queue;
    public StudentInfo() {
        this.code = "13020752";
        this.term = "2";
        this.year = "2017-2018";
    }

    public void addCourse(CourseContent.CourseItem courseItem) {
        items.add(courseItem);
        itemMap.put(courseItem.id, courseItem);
    }

    public void setCode(String _code) {
        code = _code;
        email = code + "@vnu.edu.vn";
    }
    public void updateFinaltest(String courseCode, String seatNo, String day, String time, String shift, String room, String building, String type) {
        CourseContent.CourseItem course = itemMap.get(courseCode);
        if(course != null)
            course.updateFinaltest(seatNo, day, time, shift, room, building, type);
    }
    void updateScoreboard(String courseCode, String url, String uploadtime) {
        CourseContent.CourseItem course = itemMap.get(courseCode);
        if(course != null)
            course.updateScoreboard(url, uploadtime);
    }
    public String getEmail() {
        return email;
    }

    public void loadInfo(final Profile profile) {
        try {
            final URL studentURL = new URL(ApiUtils.getStudentUrl(code, term, year));
            Log.i(LOG_TAG, studentURL.toString());
            AppExecutors.getInstance().networkIO().execute(new Runnable() {
                @Override
                public void run() {
                    String response = null;
                    try {
                        response = ApiUtils.getResponseFromHttpUrl(studentURL);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(LOG_TAG, response);
                    try {
                        JSONObject student = new JSONObject(response);
                        setCode(student.optString(KEY_CODE));
                        fullname = student.optString(KEY_FULLNAME);
                        birthday = student.optString(KEY_BIRTHDAY);
                        klass = student.optString(KEY_KLASS);
                        JSONArray courseCodes = student.optJSONArray(KEY_COURSE_CODES);
                        Log.i(LOG_TAG, courseCodes.toString());
                        JSONObject slots = student.optJSONObject(KEY_SLOTS);
                        Log.i(LOG_TAG, slots.toString());
                        items.clear();
                        itemMap.clear();
                        JSONObject course;
                        for(int i = 0; i < courseCodes.length(); i++) {
                            course = slots.optJSONObject(courseCodes.getString(i));
                            addCourse(
                                    new CourseContent.CourseItem(
                                            courseCodes.getString(i),
                                            course.optString(CourseContent.CourseItem.CODE),
                                            course.optString(CourseContent.CourseItem.NAME),
                                            course.optString(CourseContent.CourseItem.CREDIT),
                                            course.optString(CourseContent.CourseItem.GROUP),
                                            course.optString(CourseContent.CourseItem.NOTE)
                                    )
                            );
                            if(!TextUtils.isEmpty(course.optString(CourseContent.CourseItem.DAY))) {
                                updateFinaltest(
                                        courseCodes.getString(i),
                                        "",
                                        course.optString(CourseContent.CourseItem.DAY),
                                        "",
                                        "",
                                        course.optString(CourseContent.CourseItem.ROOM),
                                        "",
                                        course.optString(CourseContent.CourseItem.TYPE)
                                );
                            }
                            if(!TextUtils.isEmpty(course.optString(CourseContent.CourseItem.URL))) {
                                updateScoreboard(
                                        courseCodes.getString(i),
                                        course.optString(CourseContent.CourseItem.URL),
                                        ""
                                );
                            }
                            Log.i(LOG_TAG, itemMap.get(courseCodes.getString(i)).toString());
                            AppExecutors.getInstance().mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    profile.updateInfo(MainActivity.studentViewModel.getInfo());
                                }
                            });
                        }
                    } catch(final JSONException e) {
                        Log.e(LOG_TAG, "Parse JSON failed");
                        Log.e(LOG_TAG, e.getMessage());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
