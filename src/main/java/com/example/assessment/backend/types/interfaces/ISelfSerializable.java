package com.example.assessment.backend.types.interfaces;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.file.Path;

@Immutable
@CheckReturnValue
public interface ISelfSerializable extends Serializable {

    public abstract Path getPath();
}
