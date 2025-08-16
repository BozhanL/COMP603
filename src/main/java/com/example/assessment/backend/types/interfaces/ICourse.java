package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.Course;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.text.ParseException;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface ICourse extends Serializable {

    public static ICourse of(@NonNull ICourseCode code, @NonNull String name, int points, @NonNull String description) {
        return Course.of(code, name, points, description);
    }

    public static ICourse of(@NonNull String code, @NonNull String name, int points, @NonNull String description) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        return Course.of(code, name, points, description);
    }

    public static ICourse of(@NonNull String departmentCode, int level, int courseNumber, @NonNull String name, int points, @NonNull String description) throws IllegalArgumentException {
        return Course.of(departmentCode, level, courseNumber, name, points, description);
    }

    public abstract ICourse withCode(@NonNull ICourseCode code);

    public abstract ICourse withName(@NonNull String name);

    public abstract ICourse withPoints(int points);

    public abstract ICourse withDescription(@NonNull String description);

    public abstract ICourseCode getCode();

    public abstract String getName();

    public abstract int getPoints();

    public abstract String getDescription();
}
