package com.example.assessment.backend.types.classes;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@With
@Value
@Immutable
@CheckReturnValue
@AllArgsConstructor(staticName = "of")
public class Course implements ICourse {

    @Serial
    private static final long serialVersionUID = 1L;

    public Course(@NonNull String code, @NonNull String name, int points, @NonNull String description) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        this(ICourseCode.of(code), name, points, description);
    }

    public Course(@NonNull String departmentCode, int level, int courseNumber, @NonNull String name, int points, @NonNull String description) throws IllegalArgumentException {
        this(ICourseCode.of(departmentCode, level, courseNumber), name, points, description);
    }

    public static ICourse of(@NonNull String code, @NonNull String name, int points, @NonNull String description) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        return new Course(code, name, points, description);
    }

    public static ICourse of(@NonNull String departmentCode, int level, int courseNumber, @NonNull String name, int points, @NonNull String description) throws IllegalArgumentException {
        return new Course(departmentCode, level, courseNumber, name, points, description);
    }

    @NonNull
    ICourseCode code;
    @NonNull
    String name;
    int points;
    @NonNull
    String description;

    @Override
    public Path getPath() {
        return Path.of(String.format("%s.bin", this.getCode()));
    }
}
