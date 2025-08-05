package com.example.assessment.backend;

import java.io.IOException;
import lombok.NonNull;

public interface ICourseBackend {

    Course getCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException;

    void setCourse(@NonNull Course c) throws IOException;

    boolean deleteCourseByCode(@NonNull String code) throws IOException, DatabaseCorruptedException;

    void modifyCourse(@NonNull Course c) throws IOException;
}
