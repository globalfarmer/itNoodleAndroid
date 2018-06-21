package com.itnoodle.anhdo.itnoodle.models;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itnoodle.anhdo.itnoodle.MainActivity;
import com.itnoodle.anhdo.itnoodle.dummy.CourseContent;
import com.itnoodle.anhdo.itnoodle.utilities.ApiUtils;
import com.itnoodle.anhdo.itnoodle.viewmodels.StudentViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentInfo {
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

    public void loadInfo() {
        queue = Volley.newRequestQueue();
        queue.add(new StringRequest(Request.Method.GET, ApiUtils.getStudentUrl(studentCode, term, year.substring(0,4)),
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        showProgress(false);
                        Log.i(LOG_TAG, response);
                        try {
                            JSONObject student = new JSONObject(response);
                            Student.setCode(student.optString(Student.KEY_CODE));
                            Student.fullname = student.optString(Student.KEY_FULLNAME);
                            Student.birthday = student.optString(Student.KEY_BIRTHDAY);
                            Student.klass = student.optString(Student.KEY_KLASS);
                            Student.hasInfo = true;
                            setStudentInfo();
                            JSONArray courseCodes = student.optJSONArray(Student.KEY_COURSE_CODES);
                            Log.i(LOG_TAG, courseCodes.toString());
                            JSONObject slots = student.optJSONObject(Student.KEY_SLOTS);
                            Log.i(LOG_TAG, slots.toString());
                            Student.ITEMS.clear();
                            Student.ITEM_MAP.clear();
                            JSONObject course;
                            for(int i = 0; i < courseCodes.length(); i++) {
                                course = slots.optJSONObject(courseCodes.getString(i));
                                Student.addCourse(
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
                                    Student.updateFinaltest(
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
                                    Student.updateScoreboard(
                                            courseCodes.getString(i),
                                            course.optString(CourseContent.CourseItem.URL),
                                            ""
                                    );
                                }
                                Log.i(LOG_TAG, Student.ITEM_MAP.get(courseCodes.getString(i)).toString());
                            }
                        } catch(final JSONException e) {
                            Log.e(LOG_TAG, "Parse JSON failed");
                            Log.e(LOG_TAG, e.getMessage());
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false);
                Log.e(LOG_TAG, "request get student failed");
            }
        }));
    }
}
