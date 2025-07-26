package com.example.assessment.backend;

import java.security.SecureRandom;
import lombok.NonNull;

public interface IAuthenticatable {

    public static final SecureRandom random = new SecureRandom();

    public boolean safeCheckPassword(@NonNull String other);
}
