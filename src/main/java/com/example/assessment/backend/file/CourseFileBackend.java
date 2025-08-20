package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.NonNull;
import lombok.ToString;

@CheckReturnValue
@ToString(callSuper = true)
public final class CourseFileBackend extends FileBackend implements ICourseBackend {

    private static final Path DEFAULT_DATA_SUBPATH = Path.of("course");

    private CourseFileBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    private CourseFileBackend(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        this(Path.of(p));
    }

    private CourseFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        super(p.resolve(DEFAULT_DATA_SUBPATH));
    }

    public static CourseFileBackend of() throws IOException {
        return new CourseFileBackend();
    }

    public static CourseFileBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return new CourseFileBackend(p);
    }

    public static CourseFileBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return new CourseFileBackend(p);
    }

    @Override
    public ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        return this.getObjectByPath(ICourse.class, pathFromCode(code));
    }

    @Override
    public void setCourse(@NonNull ICourse c) throws IOException {
        this.setObject(c, pathFromCourse(c));
    }

    @Override
    @CanIgnoreReturnValue
    public boolean deleteCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException {
        return this.deleteObjectWithPath(pathFromCode(code));
    }

    @Override
    public void modifyCourse(@NonNull ICourse c) throws IOException {
        this.modifyObject(c, pathFromCourse(c));
    }

    @Override
    public ImmutableList<ICourse> listCourse() throws IOException, DatabaseCorruptedException {
        return this.listObject(ICourse.class);
    }

    public static Path pathFromCourse(@NonNull ICourse c) {
        return pathFromCode(c.getCode().toString());
    }

    public static Path pathFromCode(@NonNull String id) {
        return Path.of(String.format("%s.bin", id));
    }
}
