package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.StudentCourseInfo;
import com.example.assessment.backend.types.enums.Grade;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.NonNull;

// This is the interface for course information for a student
@Immutable
@CheckReturnValue
public interface IStudentCourseInfo extends Serializable, IPrettyPrint {

//    Static construtor to create IStudentCourseInfo
    public static IStudentCourseInfo of(
            @NonNull String courseCode,
            @NonNull Grade grade,
            @NonNull LocalDate starts,
            @NonNull String location
    ) {
        return StudentCourseInfo.of(courseCode, grade, starts, location);
    }

//    Create a new IStudentCourseInfo object with new courseCode
    public abstract IStudentCourseInfo withCourseCode(String courseCode);

//    Create a new IStudentCourseInfo object with new grade
    public abstract IStudentCourseInfo withGrade(Grade grade);

//    Create a new IStudentCourseInfo object with new starts
    public abstract IStudentCourseInfo withStarts(LocalDate starts);

//    Create a new IStudentCourseInfo object with new location
    public abstract IStudentCourseInfo withLocation(String location);

//    Return the courseCode of IStudentCourseInfo
    public abstract String getCourseCode();

//    Return the grade of IStudentCourseInfo
    public abstract Grade getGrade();

//    Return the starts of IStudentCourseInfo
    public abstract LocalDate getStarts();

//    Return the location of IStudentCourseInfo
    public abstract String getLocation();
}
