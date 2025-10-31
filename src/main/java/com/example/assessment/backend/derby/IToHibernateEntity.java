package com.example.assessment.backend.derby;

import com.google.errorprone.annotations.CheckReturnValue;

// Interface for convert an immutable object to a Hibernate Entity
@CheckReturnValue
public interface IToHibernateEntity<E extends IEntity<?>> {

//    Convert to a Hibernate Entity
    public abstract E toEntity();
}
