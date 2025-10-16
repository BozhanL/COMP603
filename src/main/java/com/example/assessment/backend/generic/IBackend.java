package com.example.assessment.backend.generic;

import com.google.errorprone.annotations.CheckReturnValue;
import java.nio.file.Path;

// Basic interface for a backend
@CheckReturnValue
public interface IBackend {

    public static final Path DEFAULT_DATA_LOCATION = Path.of(System.getProperty("user.dir"), "database").toAbsolutePath().normalize();

    public default Path getDefaultDataLocation() {
        return DEFAULT_DATA_LOCATION;
    }

    public static Path getDefaultDataLocationStatic() {
        return DEFAULT_DATA_LOCATION;
    }

    public abstract String getDb();
}
