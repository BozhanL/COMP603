package com.example.assessment.backend.types.classes;

import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.time.LocalDate;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

// This class implements IStudentCourseInfo,
// and to store the course information for student.
// Unlike Course class, this does not include description or points
@With
@Immutable
@CheckReturnValue
@Value(staticConstructor = "of")
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

//    Convert it to string with format of one variable per line
    @Override
    public String prettyToString() {
        StringBuilder sb = new StringBuilder();

        sb.append("courseCode: ");
        sb.append(courseCode);

        sb.append("\ngrade: ");
        sb.append(grade);

        sb.append("\nstarts: ");
        sb.append(starts);

        sb.append("\nlocation: ");
        sb.append(location);

        return sb.toString();
    }
}
