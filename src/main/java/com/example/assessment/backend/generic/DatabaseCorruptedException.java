package com.example.assessment.backend.generic;

import java.io.Serial;
import lombok.experimental.StandardException;

// Throw this Exception if object in the database is in error form
@StandardException
public final class DatabaseCorruptedException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
}
