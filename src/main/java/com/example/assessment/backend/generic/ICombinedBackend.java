package com.example.assessment.backend.generic;

import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.NonNull;

@CheckReturnValue
public interface ICombinedBackend extends IPersonBackend, ICourseBackend {

    public static ICombinedBackend of() throws IOException {
        return CombinedBackend.of();
    }

    public static ICombinedBackend of(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        return CombinedBackend.of(p);
    }

    public static ICombinedBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return CombinedBackend.of(p);
    }

    public static ICombinedBackend of(@NonNull IPersonBackend pb, @NonNull ICourseBackend cb) {
        return CombinedBackend.of(pb, cb);
    }
}
