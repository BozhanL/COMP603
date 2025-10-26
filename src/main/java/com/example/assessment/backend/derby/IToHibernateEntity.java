package com.example.assessment.backend.derby;

// Interface for convert an immutable object to a Hibernate Entity
public interface IToHibernateEntity<E extends IEntity<?>> {

//    Convert to a Hibernate Entity
    public abstract E toEntity();
}
