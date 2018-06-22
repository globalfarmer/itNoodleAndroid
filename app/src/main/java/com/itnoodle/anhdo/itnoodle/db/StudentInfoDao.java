package com.itnoodle.anhdo.itnoodle.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.itnoodle.anhdo.itnoodle.models.StudentInfo;

@Dao
public interface StudentInfoDao {
    @Query("SELECT * FROM student_info WHERE code = :code")
    StudentInfo studentInfoByCode(String code);
    @Insert
    void insertStudentInfo(StudentInfo studentInfo);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStudentInfo(StudentInfo studentInfo);
    @Delete
    void deleteStudentInfo(StudentInfo studentInfo);
}
