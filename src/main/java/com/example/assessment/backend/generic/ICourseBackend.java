package com.example.assessment.backend.generic;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import lombok.NonNull;

@CheckReturnValue
public interface ICourseBackend extends IBackend {

    public abstract ICourse getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException;

    public abstract void setCourse(@NonNull ICourse c) throws IOException;

    public abstract boolean deleteCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException;

    public abstract void modifyCourse(@NonNull ICourse c) throws IOException;
}
