package com.example.assessment.backend.types;

import java.time.LocalDate;
import lombok.NonNull;

public interface IManager extends IPerson {

    @Override
    public abstract IManager withPassword(@NonNull String password);

    @Override
    public abstract IManager withLegalFirstName(@NonNull String legalFirstName);

    @Override
    public abstract IManager withLegalLastName(@NonNull String legalLastName);

    @Override
    public abstract IManager withDateOfBirth(@NonNull LocalDate dateOfBirth);

    @Override
    public abstract IManager withGender(@NonNull Gender gender);

    @Override
    public abstract IManager withEmail(@NonNull String email);

    @Override
    public abstract IManager withPhone(@NonNull String phone);

    @Override
    public abstract IManager withAddress(@NonNull IAddress address);
}
