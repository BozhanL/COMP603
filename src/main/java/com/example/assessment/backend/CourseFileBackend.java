package com.example.assessment.backend;

import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;

public final class CourseFileBackend extends FileBackend implements ICourseBackend {

    protected static final Path DEFAULT_DATA_SUBPATH = Path.of("course");

    public CourseFileBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    public CourseFileBackend(@NonNull String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    public CourseFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        super(p.resolve(DEFAULT_DATA_SUBPATH));
    }

    @Override
    public Course getCourseByCode(@NonNull String code) throws IOException, ClassNotFoundException {
        Object obj = this.getObjectByPartPath(code);
        if (obj instanceof Course c) {
            return c;
        }
        return null;
    }

    @Override
    public void setCourse(@NonNull Course c) throws IOException {
        this.setObject(c);
    }

    @Override
    public boolean deleteCourseByCode(@NonNull String code) throws IOException, ClassNotFoundException {
        return this.deleteObjectWithName(code);
    }

    @Override
    public void modifyCourse(@NonNull Course c) throws IOException {
        this.modifyObject(c);
    }
}
