package com.example.assessment.backend.derby;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.entity.CourseEntity;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.NonNull;
import lombok.ToString;

@CheckReturnValue
@ToString(callSuper = true)
public final class CourseDerbyBackend extends DerbyBackend implements ICourseBackend {

    private CourseDerbyBackend() throws IOException, DatabaseCorruptedException {
        this(DEFAULT_DATA_LOCATION);
    }

    private CourseDerbyBackend(@NonNull String p) throws IOException, DatabaseCorruptedException {
        this(Path.of(p));
    }

    private CourseDerbyBackend(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        super(p);
    }

    public static CourseDerbyBackend of() throws IOException, DatabaseCorruptedException {
        return new CourseDerbyBackend();
    }

    public static CourseDerbyBackend of(@NonNull String p) throws IOException, DatabaseCorruptedException {
        return new CourseDerbyBackend(p);
    }

    public static CourseDerbyBackend of(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        return new CourseDerbyBackend(p);
    }

    @Override
    public ICourse getCourseByCode(@NonNull String code) throws ParseException {
        var e = this.getObject(CourseEntity.class, ICourseCode.of(code).toEntity());
        if (e == null) {
            return null;
        }
        return e.toImmutable();
    }

    @Override
    public void setCourse(@NonNull ICourse c) {
        this.setObject(c.toEntity());
    }

    @Override
    public boolean deleteCourseByCode(@NonNull ICourseCode code) {
        return this.deleteObjectByID(CourseEntity.class, code.toEntity());
    }

    @Override
    public void modifyCourse(@NonNull ICourse c) {
        this.modifyObject(c.toEntity());
    }

    @Override
    public ImmutableList<ICourse> listCourse() {
        var e = this.listObject(CourseEntity.class);
        return e.stream().map(CourseEntity::toImmutable).collect(ImmutableList.toImmutableList());
    }
}
