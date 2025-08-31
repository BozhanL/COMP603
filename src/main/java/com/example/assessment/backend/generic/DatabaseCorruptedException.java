package com.example.assessment.backend.generic;

import java.io.Serial;
import lombok.experimental.StandardException;

// Throw this Exception if an object in the database is in an error form
@StandardException
public final class DatabaseCorruptedException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
}
