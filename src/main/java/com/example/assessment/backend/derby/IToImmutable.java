package com.example.assessment.backend.derby;

import com.google.errorprone.annotations.CheckReturnValue;

// Interface for convert a Hibernate Entity to an immutable object
@CheckReturnValue
public interface IToImmutable<E> {

//    Convert to an immutable object
    public abstract E toImmutable();
}
