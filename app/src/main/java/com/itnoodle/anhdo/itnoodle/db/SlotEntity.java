package com.itnoodle.anhdo.itnoodle.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.itnoodle.anhdo.itnoodle.models.StudentInfo;

import java.util.Date;

@Entity(tableName = "slot")
public class SlotEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "student_code")
    @ForeignKey(entity = StudentInfo.class, parentColumns = StudentInfoEntity.KEY_CODE, childColumns = SlotEntity.STUDENT_CODE)
    private int stdCode;
    @ColumnInfo(name="term")
    private String term;
    @ColumnInfo(name="year")
    private String year;
    @ColumnInfo(name="course_code")
    private String courseCode;
    @ColumnInfo(name="course_name")
    private String courseName;
    @ColumnInfo(name="credit")
    private String credit;
    @ColumnInfo(name="group")
    private String group;
    @ColumnInfo(name="note")
    private String note;
    @ColumnInfo(name="seat_no")
    private String seatNo;
    @ColumnInfo(name="time")
    private Date time;
    @ColumnInfo(name="shift")
    private String shift;
    @ColumnInfo(name="room")
    private String room;
    @ColumnInfo(name="type")
    private String type;
    @ColumnInfo(name="url")
    private String url;
    @ColumnInfo(name="uploadtime")
    private Date uploadtime;
    public SlotEntity(int id, int stdInfoId, String term, String year, String courseCode, String courseName, String credit, String group, String note) {
        setId(id);
        setStdCode(stdInfoId);
        setTerm(term);
        setYear(year);
        setCourseCode(courseCode);
        setCourseName(courseName);
        setCredit(credit);
        setGroup(group);
        setNote(note);
    }
    @Ignore
    public SlotEntity(int stdInfoId, String term, String year, String courseCode, String courseName, String credit, String group, String note) {
        setStdCode(stdInfoId);
        setTerm(term);
        setYear(year);
        setCourseCode(courseCode);
        setCourseName(courseName);
        setCredit(credit);
        setGroup(group);
        setNote(note);
    }
    public void updateFinaltest(String seatNo, Date time, String shift, String room, String type) {
        setSeatNo(seatNo);
        setTime(time);
        setShift(shift);
        setRoom(room);
        setType(type);
    }
    public void updateScoreboard(String url, Date uploadtime) {
        setUrl(url);
        setUploadtime(uploadtime);
    }
    public int getId() { return id;}
    public void setId(int _id) { id = _id;}
    public int getStdCode() { return stdCode;}
    public void setStdCode(int _stdCode) { stdCode = _stdCode; }
    public String getTerm() { return term; }
    public void setTerm(String _term) {term = _term; }
    public String getYear() { return year; }
    public void setYear(String _year) {year = _year; }
    public String getCourseCode() {return courseCode;}
    public void setCourseCode(String _cc) { courseCode = _cc; }
    public String getCourseName() {return courseName;}
    public void setCourseName(String _cn) { courseName = _cn; }
    public String getCredit() { return credit; }
    public void setCredit(String _credit) { credit = _credit; }
    public String getGroup() { return group; }
    public void setGroup(String _group) { group = _group; }
    public String getNote() { return note; }
    public void setNote(String _note) { note = _note; }
    public String getSeatNo() { return seatNo; }
    public void setSeatNo(String _seatNo) { seatNo = _seatNo; }
    public String getShift() { return shift; }
    public void setShift(String _shift) { shift = _shift;}
    public String getRoom() { return room; }
    public void setRoom(String _room) { room = _room; }
    public String getType() { return type; }
    public void setType(String _type) { type = _type;}
    public String getUrl() { return url; }
    public void setUrl(String _url) { url = _url; }
    public Date getTime() { return time;}
    public void setTime(Date _time) { time = _time; }
    public Date getUploadtime() { return uploadtime; }
    public void setUploadtime(Date _uploadtime) { uploadtime = _uploadtime; }
}
