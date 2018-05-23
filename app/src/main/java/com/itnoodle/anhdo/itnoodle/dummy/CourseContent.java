package com.itnoodle.anhdo.itnoodle.dummy;

public class CourseContent {
    public static final String LOG_TAG = "COURSE_CONTENT";
    public static class CourseItem {
        public static final String CODE = "code";
        public static final String NAME = "name";
        public static final String CREDIT = "credit";
        public static final String GROUP = "group";
        public static final String NOTE = "note";
        public static final String SEAT_NO = "seat_no";
        public static final String DAY = "day";
        public static final String TIME = "time";
        public static final String SHIFT = "shift";
        public static final String ROOM = "room";
        public static final String BUILDING = "bulding";
        public static final String TYPE = "type";
        public static final String URL = "url";
        public static final String UPLOADTIME = "uploadtime";
        public String code;
        public String name;
        public String credit;
        public String group;
        public String note;
        public String seatNo;
        public String day;
        public String time;
        public String shift;
        public String room;
        public String building;
        public String type;
        public String url;
        public String uploadtime;
        public CourseItem(String code, String name, String credit, String group, String note) {
            this.code = code;
            this.name = name;
            this.credit = credit;
            this.group = group;
            this.note = note;
        }
        public void updateFinaltest(String seatNo, String day, String time, String shift, String room, String building, String type) {
            this.seatNo = seatNo;
            this.day = day;
            this.time = time;
            this.shift = shift;
            this.room = room;
            this.building = building;
            this.type = type;
        }
        public void updateScoreboard(String url, String uploadtime) {
            this.url = url;
            this.uploadtime = uploadtime;
        }
    }
}
