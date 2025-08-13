package com.example.assessment.backend.generic;

import java.io.Serial;
import lombok.experimental.StandardException;

@StandardException
public final class DatabaseCorruptedException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
}
