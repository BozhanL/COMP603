package com.example.assessment.backend.types;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.time.LocalDate;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@With
@Value
@Immutable
@CheckReturnValue
public class StudentCourseInfo implements IStudentCourseInfo {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    String courseCode;
    @NonNull
    Grade grade;
    @NonNull
    LocalDate starts;
    @NonNull
    String location;
}
