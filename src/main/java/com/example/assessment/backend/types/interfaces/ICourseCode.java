package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.CourseCode;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.text.ParseException;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface ICourseCode extends Serializable, IPrettyPrint {

    public static ICourseCode of(String departmentCode, int level, int courseNumber) {
        return CourseCode.of(departmentCode, level, courseNumber);
    }

    public static ICourseCode of(@NonNull String code) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        return CourseCode.of(code);
    }

    public abstract ICourseCode withDepartmentCode(@NonNull String departmentCode) throws IllegalArgumentException;

    public abstract ICourseCode withLevel(int level);

    public abstract ICourseCode withCourseNumber(int courseNumber);

    public abstract String getDepartmentCode();

    public abstract int getLevel();

    public abstract int getCourseNumber();
}
