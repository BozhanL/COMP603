package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.Grade;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.time.LocalDate;

@Immutable
@CheckReturnValue
public interface IStudentCourseInfo extends Serializable {

    public abstract IStudentCourseInfo withCourseCode(String courseCode);

    public abstract IStudentCourseInfo withGrade(Grade grade);

    public abstract IStudentCourseInfo withStarts(LocalDate starts);

    public abstract IStudentCourseInfo withLocation(String location);

    public abstract String getCourseCode();

    public abstract Grade getGrade();

    public abstract LocalDate getStarts();

    public abstract String getLocation();
}
