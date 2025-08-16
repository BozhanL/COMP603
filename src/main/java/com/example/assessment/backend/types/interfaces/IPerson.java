package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.UserType;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface IPerson extends IAuthentication, Serializable {

    public abstract UserType getType();

    public abstract int getAge();

    public abstract IPerson withPassword(@NonNull String password);

    public abstract IPerson withLegalFirstName(@NonNull String legalFirstName);

    public abstract IPerson withLegalLastName(@NonNull String legalLastName);

    public abstract IPerson withDateOfBirth(@NonNull LocalDate dateOfBirth);

    public abstract IPerson withGender(@NonNull Gender gender);

    public abstract IPerson withEmail(@NonNull String email);

    public abstract IPerson withPhone(@NonNull String phone);

    public abstract IPerson withAddress(@NonNull IAddress address);

    public abstract String getId();

    public abstract String getLegalFirstName();

    public abstract String getLegalLastName();

    public abstract LocalDate getDateOfBirth();

    public abstract Gender getGender();

    public abstract String getEmail();

    public abstract String getPhone();

    public abstract IAddress getAddress();
}
