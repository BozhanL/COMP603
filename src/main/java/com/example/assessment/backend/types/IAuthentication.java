package com.example.assessment.backend.types;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface IAuthentication {

    public abstract boolean safeCheckPassword(@NonNull String other);
}
