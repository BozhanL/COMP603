package com.example.assessment.backend.types.classes;

import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.text.ParseException;
import java.util.Objects;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@With
@Value
@Immutable
@CheckReturnValue
public class CourseCode implements ICourseCode {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    String departmentCode;
    int level;
    int courseNumber;

    private CourseCode(@NonNull String code) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        this.departmentCode = code.replaceAll("\\d", "");
        String nums = code.replaceAll("[^\\d]", "");
        this.level = Integer.parseInt(nums.substring(0, 1));
        this.courseNumber = Integer.parseInt(nums.substring(1));

        if (this.getDepartmentCode().isBlank()) {
            throw new ParseException("Unable to identify departmentCode!", 0);
        }
    }

    private CourseCode(@NonNull String departmentCode, int level, int courseNumber) throws IllegalArgumentException {
        if (departmentCode.isBlank()) {
            throw new IllegalArgumentException("departmentCode must not be blank!");
        }

        this.departmentCode = departmentCode;
        this.level = level;
        this.courseNumber = courseNumber;
    }

    public static ICourseCode of(String departmentCode, int level, int courseNumber) {
        return new CourseCode(departmentCode, level, courseNumber);
    }

    public static ICourseCode of(@NonNull String code) throws ParseException, NumberFormatException, IndexOutOfBoundsException {
        return new CourseCode(code);
    }

    @Override
    public ICourseCode withDepartmentCode(@NonNull String departmentCode) throws IllegalArgumentException {
        if (departmentCode.isBlank()) {
            throw new IllegalArgumentException("departmentCode must not be blank!");
        }

        return Objects.equals(this.departmentCode, departmentCode) ? this : new CourseCode(departmentCode, level, courseNumber);
    }

    @Override
    public String toString() {
        return String.format("%s%01d%02d", this.getDepartmentCode(), this.getLevel(), this.getCourseNumber());
    }

    @Override
    public String prettyToString() {
        return this.toString();
    }
}
