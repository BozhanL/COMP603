package com.example.assessment.backend.generic;

import com.example.assessment.backend.derby.CourseDerbyBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.NonNull;

// This is the interface for managing course
@CheckReturnValue
public interface ICourseBackend extends IBackend {

    public static ICourseBackend of() throws IOException, DatabaseCorruptedException {
        return CourseDerbyBackend.of();
    }

    public static ICourseBackend of(@NonNull String p) throws IOException, DatabaseCorruptedException {
        return CourseDerbyBackend.of(p);
    }

    public static ICourseBackend of(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        return CourseDerbyBackend.of(p);
    }

//    Return a Course with same course code as argument
    public abstract ICourse getCourseByCode(@NonNull String code) throws ParseException;

//    Store the Course
//    If the Course already exist in database, throw FileAlreadyExistsException
    public abstract void setCourse(@NonNull ICourse c);

//    Delete the Course with same course code
    @CanIgnoreReturnValue
    public abstract boolean deleteCourseByCode(@NonNull ICourseCode code);
//    Delete the Course with same course code

    @CanIgnoreReturnValue
    public default boolean deleteCourseByCode(@NonNull String code) throws ParseException {
        return this.deleteCourseByCode(ICourseCode.of(code));
    }

//    Change the Course
    public abstract void modifyCourse(@NonNull ICourse c);

//    List all course in database
    public abstract ImmutableList<ICourse> listCourse();
}
