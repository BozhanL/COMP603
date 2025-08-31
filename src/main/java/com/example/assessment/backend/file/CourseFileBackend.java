package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.NonNull;
import lombok.ToString;

// Managing Courses using File based backend
@CheckReturnValue
@ToString(callSuper = true)
public final class CourseFileBackend extends FileBackend implements ICourseBackend {

//    Default path is under DEFAULT_DATA_LOCATION/course
    private static final Path DEFAULT_DATA_SUBPATH = Path.of("course");

//    Create the database with default path
    private CourseFileBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    private CourseFileBackend(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        this(Path.of(p));
    }

    private CourseFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
//        Path is always at p/DEFAULT_DATA_SUBPATH
        super(p.resolve(DEFAULT_DATA_SUBPATH));
    }

//    These are static method to construct a Object
    public static CourseFileBackend of() throws IOException {
        return new CourseFileBackend();
    }

    public static CourseFileBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return new CourseFileBackend(p);
    }

    public static CourseFileBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return new CourseFileBackend(p);
    }

//    Get a Course object by course code
    @Override
    public ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        return this.getObjectByPath(ICourse.class, pathFromCode(code));
    }

//    Save a course to file
//    Throw FileAlreadyExistsException if already exist
//    Use modifyCourse to change content of an existing course
    @Override
    public void setCourse(@NonNull ICourse c) throws IOException, FileAlreadyExistsException {
        this.setObject(c, pathFromCourse(c));
    }

//    Delete a course by Course code
//    Return true if delete a file, false if file not exist
    @Override
    @CanIgnoreReturnValue
    public boolean deleteCourseByCode(@NonNull String code) throws IOException {
        return this.deleteObjectWithPath(pathFromCode(code));
    }

//    Change the content of a Course
    @Override
    public void modifyCourse(@NonNull ICourse c) throws IOException {
        this.modifyObject(c, pathFromCourse(c));
    }

//    List all course of the database
    @Override
    public ImmutableList<ICourse> listCourse() throws IOException, DatabaseCorruptedException {
        return this.listObject(ICourse.class);
    }

//    Get the path for a Course
    public static Path pathFromCourse(@NonNull ICourse c) {
        return pathFromCode(c.getCode().toString());
    }

//    Get the path for a Course code
    public static Path pathFromCode(@NonNull String code) {
//        Format is code + ".bin", because it is a binary file
        return Path.of(String.format("%s.bin", code));
    }
}
