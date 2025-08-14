package com.example.assessment.backend.types;

import java.io.Serial;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@With
@Value
@AllArgsConstructor
public class Course implements ICourse {

    @Serial
    private static final long serialVersionUID = 1L;

    public Course(@NonNull String code, @NonNull String name, int points, @NonNull String description) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        this(new CourseCode(code), name, points, description);
    }

    public Course(@NonNull String departmentCode, int level, int courseNumber, @NonNull String name, int points, @NonNull String description) throws IllegalArgumentException {
        this(new CourseCode(departmentCode, level, courseNumber), name, points, description);
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
