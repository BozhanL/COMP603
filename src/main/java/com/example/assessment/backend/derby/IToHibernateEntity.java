package com.example.assessment.backend.derby;

public interface IToHibernateEntity<E extends IEntity<?>> {

    public abstract E toEntity();
}
