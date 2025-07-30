package com.example.assessment.backend;

import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;

public interface ICourseBackend {

    Course getCourseByCode(@NonNull Path code) throws IOException, ClassNotFoundException;

    void setCourse(@NonNull Course c) throws IOException;

    default Course getCourseByCode(@NonNull String code) throws IOException, ClassNotFoundException {
        return this.getCourseByCode(Path.of(code));
    }
}
