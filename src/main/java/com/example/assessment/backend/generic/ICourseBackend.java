package com.example.assessment.backend.generic;

import com.example.assessment.backend.derby.CourseDerbyBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.NonNull;

// This is the interface for managing course
@CheckReturnValue
public interface ICourseBackend extends IBackend {

    public static ICourseBackend of() throws IOException {
        return CourseDerbyBackend.of();
    }

    public static ICourseBackend of(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        return CourseDerbyBackend.of(p);
    }

    public static ICourseBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return CourseDerbyBackend.of(p);
    }

//    Return a Course with same course code as argument
    public abstract ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException, FileNotFoundException, ParseException;

//    Store the Course
//    If the Course already exist in database, throw FileAlreadyExistsException
    public abstract void setCourse(@NonNull ICourse c) throws IOException, FileAlreadyExistsException;

//    Delete the Course with same course code
    @CanIgnoreReturnValue
    public abstract boolean deleteCourseByCode(@NonNull String code) throws IOException, ParseException;

//    Change the Course
    public abstract void modifyCourse(@NonNull ICourse c) throws IOException;

//    List all course in database
    public abstract ImmutableList<ICourse> listCourse() throws IOException, DatabaseCorruptedException;
}
