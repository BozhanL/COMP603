package com.example.assessment.backend.types.classes;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.text.ParseException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

// This class implements ICourse,
// and used to store information about course detail.
// Unlike StudentCourseInfo, this does not include grade or start date
@With
@Value
@Immutable
@CheckReturnValue
@AllArgsConstructor(staticName = "of")
public class Course implements ICourse {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    ICourseCode code;
    @NonNull
    String name;
    int points;
    @NonNull
    String description;

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

//    Convert it to string with format of one variable per line
    @Override
    public String prettyToString() {
        StringBuilder sb = new StringBuilder();

        sb.append("code: ");
        sb.append(code.prettyToString());

        sb.append("\nname: ");
        sb.append(name);

        sb.append("\npoints: ");
        sb.append(points);

        sb.append("\ndescription: ");
        sb.append(description);

        return sb.toString();
    }
}
