package com.example.assessment.backend.types;

import lombok.NonNull;

public interface IAuthentication {

    public abstract boolean safeCheckPassword(@NonNull String other);
}
