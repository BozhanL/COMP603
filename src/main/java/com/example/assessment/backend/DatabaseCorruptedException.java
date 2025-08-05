package com.example.assessment.backend;

public class DatabaseCorruptedException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of <code>DatabaseCorruptedException</code> without
     * detail message.
     */
    public DatabaseCorruptedException() {
    }

    /**
     * Constructs an instance of <code>DatabaseCorruptedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DatabaseCorruptedException(String msg) {
        super(msg);
    }
}
