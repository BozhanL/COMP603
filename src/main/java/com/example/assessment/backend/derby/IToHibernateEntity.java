package com.example.assessment.backend.derby;

public interface IToHibernateEntity<E> {

    public abstract E toEntity();
}
