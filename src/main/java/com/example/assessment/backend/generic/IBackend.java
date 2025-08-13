package com.example.assessment.backend.generic;

import java.nio.file.Path;

public interface IBackend {

    public abstract Path getDefaultDataLocation();

    public abstract Path getDb();
}
