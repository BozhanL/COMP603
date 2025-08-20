package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.StudentCourseInfo;
import com.example.assessment.backend.types.enums.Grade;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface IStudentCourseInfo extends Serializable, IPrettyPrint {

    public static IStudentCourseInfo of(
            @NonNull String courseCode,
            @NonNull Grade grade,
            @NonNull LocalDate starts,
            @NonNull String location
    ) {
        return StudentCourseInfo.of(courseCode, grade, starts, location);
    }

    public abstract IStudentCourseInfo withCourseCode(String courseCode);

    public abstract IStudentCourseInfo withGrade(Grade grade);

    public abstract IStudentCourseInfo withStarts(LocalDate starts);

    public abstract IStudentCourseInfo withLocation(String location);

    public abstract String getCourseCode();

    public abstract Grade getGrade();

    public abstract LocalDate getStarts();

    public abstract String getLocation();
}
