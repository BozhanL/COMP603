package com.example.assessment.backend.generic;

import com.example.assessment.backend.types.ICourse;
import java.io.IOException;
import lombok.NonNull;

public interface ICourseBackend extends IBackend {

    public abstract ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException;

    public abstract void setCourse(@NonNull ICourse c) throws IOException;

    public abstract boolean deleteCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException;

    public abstract void modifyCourse(@NonNull ICourse c) throws IOException;
}
