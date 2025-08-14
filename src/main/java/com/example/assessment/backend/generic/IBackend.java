package com.example.assessment.backend.generic;

import com.google.errorprone.annotations.CheckReturnValue;
import java.nio.file.Path;

@CheckReturnValue
public interface IBackend {

    public abstract Path getDefaultDataLocation();
}
