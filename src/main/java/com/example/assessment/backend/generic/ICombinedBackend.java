package com.example.assessment.backend.generic;

import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.NonNull;

// This interface is a wrapper for IPersonBackend and ICourseBackend
@CheckReturnValue
public interface ICombinedBackend extends IPersonBackend, ICourseBackend {

    public static ICombinedBackend of() throws IOException, IllegalArgumentException, DatabaseCorruptedException {
        return CombinedBackend.of();
    }

    public static ICombinedBackend of(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException, IllegalArgumentException, DatabaseCorruptedException {
        return CombinedBackend.of(p);
    }

    public static ICombinedBackend of(@NonNull Path p) throws IOException, IllegalArgumentException, IllegalArgumentException, DatabaseCorruptedException {
        return CombinedBackend.of(p);
    }

    public static ICombinedBackend of(@NonNull IPersonBackend pb, @NonNull ICourseBackend cb) {
        return CombinedBackend.of(pb, cb);
    }
}
