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
import lombok.NonNull;
import lombok.ToString;

// Managing Course using Derby based backend
@CheckReturnValue
@ToString(callSuper = true)
public final class CourseDerbyBackend extends DerbyBackend implements ICourseBackend {

//    Create the database with default path
    private CourseDerbyBackend() throws IOException, DatabaseCorruptedException {
        this(DEFAULT_DATA_LOCATION);
    }

    private CourseDerbyBackend(@NonNull String p) throws IOException, DatabaseCorruptedException {
        this(Path.of(p));
    }

    private CourseDerbyBackend(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        super(p);
    }

//    These are static method to construct a Object
    public static CourseDerbyBackend of() throws IOException, DatabaseCorruptedException {
        return new CourseDerbyBackend();
    }

    public static CourseDerbyBackend of(@NonNull String p) throws IOException, DatabaseCorruptedException {
        return new CourseDerbyBackend(p);
    }

    public static CourseDerbyBackend of(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        return new CourseDerbyBackend(p);
    }

//    Get a Course object by course code
    @Override
    public ICourse getCourseByCode(@NonNull ICourseCode code) {
//        Get the entity
        CourseEntity e = this.getObject(CourseEntity.class, code.toEntity());

//        Return null if it is null
        if (e == null) {
            return null;
        }

//        Convert it to immutable
        return e.toImmutable();
    }

//    Save a course to database
//    Throw ConstraintViolationException if already exist
//    Use modifyCourse to change content of an existing course
    @Override
    public void setCourse(@NonNull ICourse c) {
        this.setObject(c.toEntity());
    }

//    Delete a course based on the Course Code
//    Return true if exist and deleted, false otherwise
    @Override
    public boolean deleteCourseByCode(@NonNull ICourseCode code) {
        return this.deleteObjectByID(CourseEntity.class, code.toEntity());
    }

//    Modify an course in database, create if not exist
    @Override
    public void modifyCourse(@NonNull ICourse c) {
        this.modifyObject(c.toEntity());
    }

//    Get a list of course in database
    @Override
    public ImmutableList<ICourse> listCourse() {
//        Get the list of entity
        ImmutableList<CourseEntity> e = this.listObject(CourseEntity.class);
//        Convert the entity to immutable
        return e.stream().map(CourseEntity::toImmutable).collect(ImmutableList.toImmutableList());
    }
}
