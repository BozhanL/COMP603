package com.example.assessment.backend.generic;

import com.example.assessment.backend.file.CourseFileBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.NonNull;

@CheckReturnValue
public interface ICourseBackend extends IBackend {

    public static ICourseBackend of() throws IOException {
        return CourseFileBackend.of();
    }

    public static ICourseBackend of(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        return CourseFileBackend.of(p);
    }

    public static ICourseBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return CourseFileBackend.of(p);
    }

    public abstract ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException, FileNotFoundException;

    public abstract void setCourse(@NonNull ICourse c) throws IOException;

    @CanIgnoreReturnValue
    public abstract boolean deleteCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException;

    public abstract void modifyCourse(@NonNull ICourse c) throws IOException;

    public abstract ImmutableList<ICourse> listCourse() throws IOException, DatabaseCorruptedException;
}
