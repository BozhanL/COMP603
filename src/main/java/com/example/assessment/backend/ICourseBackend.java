package com.example.assessment.backend;

import java.io.IOException;
import lombok.NonNull;

public interface ICourseBackend {

    Course getCourseByCode(@NonNull String code) throws IOException, ClassNotFoundException;

    void setCourse(@NonNull Course c) throws IOException;
}
