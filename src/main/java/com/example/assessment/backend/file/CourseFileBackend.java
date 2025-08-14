package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.ICourse;
import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
public final class CourseFileBackend extends FileBackend implements ICourseBackend {

    private static final Path DEFAULT_DATA_SUBPATH = Path.of("course");

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
