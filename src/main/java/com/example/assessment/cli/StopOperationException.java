package com.example.assessment.cli;

import java.io.Serial;
import lombok.experimental.StandardException;

@StandardException
public final class StopOperationException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
}
