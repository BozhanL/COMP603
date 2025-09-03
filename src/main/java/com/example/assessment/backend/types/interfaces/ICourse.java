package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.Course;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.text.ParseException;
import lombok.NonNull;

// This is the interface for courses at university
@Immutable
@CheckReturnValue
public interface ICourse extends Serializable, IPrettyPrint {

//    Static construtor to create ICourse
    public static ICourse of(@NonNull ICourseCode code, @NonNull String name, int points, @NonNull String description) {
        return Course.of(code, name, points, description);
    }

//    Static construtor to create ICourse
    public static ICourse of(@NonNull String code, @NonNull String name, int points, @NonNull String description) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        return Course.of(code, name, points, description);
    }

//    Static construtor to create ICourse
    public static ICourse of(@NonNull String departmentCode, int level, int courseNumber, @NonNull String name, int points, @NonNull String description) throws IllegalArgumentException {
        return Course.of(departmentCode, level, courseNumber, name, points, description);
    }

//    Create a new ICourse object with new code
    public abstract ICourse withCode(@NonNull ICourseCode code);

//    Create a new ICourse object with new name
    public abstract ICourse withName(@NonNull String name);

//    Create a new ICourse object with new points
    public abstract ICourse withPoints(int points);

//    Create a new ICourse object with new description
    public abstract ICourse withDescription(@NonNull String description);

//    Return the code of course
    public abstract ICourseCode getCode();

//    Return the name of course
    public abstract String getName();

//    Return the point of course
    public abstract int getPoints();

//    Return the description of course
    public abstract String getDescription();
}
