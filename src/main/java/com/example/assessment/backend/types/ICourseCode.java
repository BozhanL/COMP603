package com.example.assessment.backend.types;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface ICourseCode extends Serializable {

    public abstract ICourseCode withDepartmentCode(@NonNull String departmentCode) throws IllegalArgumentException;

    public abstract ICourseCode withLevel(int level);

    public abstract ICourseCode withCourseNumber(int courseNumber);

    public abstract String getDepartmentCode();

    public abstract int getLevel();

    public abstract int getCourseNumber();
}
