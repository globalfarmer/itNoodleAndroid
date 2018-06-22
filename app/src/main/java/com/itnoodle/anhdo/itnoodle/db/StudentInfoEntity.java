package com.itnoodle.anhdo.itnoodle.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "student_info")
public class StudentInfoEntity {
    public static final String CODE = "code";
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name="code")
    @Index(unique = true)
    private String code;
    @ColumnInfo(name="fullname")
    private String fullname;
    @ColumnInfo(name="klass")
    private String klass;
    @ColumnInfo(name="birthday")
    private String birthday;
    @ColumnInfo(name="term")
    private String term;
    @ColumnInfo(name="year")
    private String year;
    @Ignore
    public StudentInfoEntity(String code, String fullname, String klass, String birthday, String term, String year) {
        setCode(code);
        setFullname(fullname);
        setKlass(klass);
        setBirthday(birthday);
        setTerm(term);
        setYear(year);
    }
    public StudentInfoEntity(int id, String code, String fullname, String klass, String birthday, String term, String year) {
        setId(id);
        setCode(code);
        setFullname(fullname);
        setKlass(klass);
        setBirthday(birthday);
        setTerm(term);
        setYear(year);
    }

    public int getId() {
        return id;
    }
    public void setId(int _id) {
        id = _id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String _code) {
        code = _code;
    }
    public String getFullname() {
        return fullname;
    }
    public void setFullname(String _fullname) {
        fullname = _fullname;
    }
    public String getKlass() {
        return klass;
    }
    public void setKlass(String _klass) {
        klass = _klass;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String _birthday) {
        birthday = _birthday;
    }
    public String getTerm() {
        return term;
    }
    public void setTerm(String _term) {
        term = _term;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String _year) {
        year = _year;
    }
}
