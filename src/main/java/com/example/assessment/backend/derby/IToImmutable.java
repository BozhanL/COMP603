package com.example.assessment.backend.derby;

// Interface for convert a Hibernate Entity to an immutable object
public interface IToImmutable<E> {

//    Convert to an immutable object
    public abstract E toImmutable();
}
