package com.example.assessment.backend.derby;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.entity.CourseEntity;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.text.ParseException;
import lombok.NonNull;
import lombok.ToString;

@CheckReturnValue
@ToString(callSuper = true)
public final class CourseDerbyBackend extends DerbyBackend implements ICourseBackend {

    private CourseDerbyBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    private CourseDerbyBackend(@NonNull String p) throws IOException {
        this(Path.of(p));
    }

    private CourseDerbyBackend(@NonNull Path p) throws IOException {
        super(p);
    }

    public static CourseDerbyBackend of() throws IOException {
        return new CourseDerbyBackend();
    }

    public static CourseDerbyBackend of(@NonNull String p) throws IOException {
        return new CourseDerbyBackend(p);
    }

    public static CourseDerbyBackend of(@NonNull Path p) throws IOException {
        return new CourseDerbyBackend(p);
    }

    @Override
    public ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException, FileNotFoundException, ParseException {
        var e = this.getObject(CourseEntity.class, ICourseCode.of(code).toEntity());
        if (e == null) {
            return null;
        }
        return e.toImmutable();
    }

    @Override
    public void setCourse(@NonNull ICourse c) throws IOException, FileAlreadyExistsException {
        this.setObject(c.toEntity());
    }

    @Override
    public boolean deleteCourseByCode(@NonNull String code) throws IOException, ParseException {
        return this.deleteObjectByID(CourseEntity.class, ICourseCode.of(code).toEntity());
    }

    @Override
    public void modifyCourse(@NonNull ICourse c) throws IOException {
        this.modifyObject(c.toEntity());
    }

    @Override
    public ImmutableList<ICourse> listCourse() throws IOException, DatabaseCorruptedException {

        var e = this.listObject(CourseEntity.class);
        return e.stream().map(CourseEntity::toImmutable).collect(ImmutableList.toImmutableList());
    }
}
