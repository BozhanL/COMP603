package com.example.assessment.backend;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@With
public class StudentCourseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    protected String courseCode;
    @NonNull
    protected Grade grade;
    @NonNull
    protected LocalDate starts;
    @NonNull
    protected String location;
}
