package com.example.assessment.backend;

import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;

public class CourseFileBackend extends FileBackend implements ICourseBackend {

    public static final Path DEFAULT_DATABASE = Path.of(System.getProperty("user.home"), ".student/filedb", "course");

    public CourseFileBackend() throws IOException {
        this(DEFAULT_DATABASE);
    }

    public CourseFileBackend(@NonNull String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    public CourseFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        super(p);
    }

    @Override
    public Course getCourseByCode(@NonNull Path code) throws IOException, ClassNotFoundException {
        Object obj = this.getObjectByPath(code);
        if (obj instanceof Course c) {
            return c;
        }
        return null;
    }

    @Override
    public void setCourse(@NonNull Course c) throws IOException {
        this.setObjectWithName(c);
    }
}
