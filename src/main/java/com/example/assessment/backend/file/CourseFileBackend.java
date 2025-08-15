package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
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

    private CourseFileBackend(@NonNull String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    private CourseFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        super(p.resolve(DEFAULT_DATA_SUBPATH));
    }

    public static ICourseBackend of() throws IOException {
        return new CourseFileBackend();
    }

    public static ICourseBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return new CourseFileBackend(p);
    }

    public static ICourseBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return new CourseFileBackend(p);
    }

    @Override
    public ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException {
        Object obj = this.getObjectByPartPath(code);
        if (obj instanceof ICourse c) {
            return c;
        }
        return null;
    }

    @Override
    public void setCourse(@NonNull ICourse c) throws IOException {
        this.setObject(c);
    }

    @Override
    public boolean deleteCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException {
        return this.deleteObjectWithName(code);
    }

    @Override
    public void modifyCourse(@NonNull ICourse c) throws IOException {
        this.modifyObject(c);
    }
}
