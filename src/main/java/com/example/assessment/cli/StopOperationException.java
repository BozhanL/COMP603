package com.example.assessment.cli;

import java.io.Serial;
import lombok.experimental.StandardException;

// This eception should be throw when user want to stop current operation
// E.g. A user is adding a new student to the system. When he enter the name of
// the student, he wants to stop and cancel this. At that time, the method for
// handel user input can throw this exception
@StandardException
public final class StopOperationException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
}
