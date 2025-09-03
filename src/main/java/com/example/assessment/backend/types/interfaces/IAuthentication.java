package com.example.assessment.backend.types.interfaces;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import lombok.NonNull;

// This interface defined a method to safely check password
@Immutable
@CheckReturnValue
public interface IAuthentication {

//    This method safely comapre the password
//    Return true if password is same, false is different
    public abstract boolean safeCheckPassword(@NonNull String other);
}
