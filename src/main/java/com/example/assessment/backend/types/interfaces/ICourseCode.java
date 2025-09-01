package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.CourseCode;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.text.ParseException;
import lombok.NonNull;

// This is the interface for course code of a course
@Immutable
@CheckReturnValue
public interface ICourseCode extends Serializable, IPrettyPrint {

//    Static construtor to create ICourseCode
    public static ICourseCode of(String departmentCode, int level, int courseNumber) {
        return CourseCode.of(departmentCode, level, courseNumber);
    }

//    Static construtor to create ICourseCode
    public static ICourseCode of(@NonNull String code) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        return CourseCode.of(code);
    }

//    Create a new IAddress object with new departmentCode
    public abstract ICourseCode withDepartmentCode(@NonNull String departmentCode) throws IllegalArgumentException;

//    Create a new IAddress object with new level
    public abstract ICourseCode withLevel(int level);

//    Create a new IAddress object with new courseNumber
    public abstract ICourseCode withCourseNumber(int courseNumber);

//    Return the departmentCode of course code
    public abstract String getDepartmentCode();

//    Return the level of course code
    public abstract int getLevel();

//    Return the course number of course code
    public abstract int getCourseNumber();
}
