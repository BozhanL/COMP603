package com.example.assessment.backend;

import lombok.NonNull;

public interface IAuthenticatable {

    public boolean safeCheckPassword(@NonNull String other);
}
