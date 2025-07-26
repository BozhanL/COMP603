package com.example.assessment.backend;

import java.nio.file.Path;
import java.time.LocalDate;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;

@With
@Value
@NonFinal
public abstract class Person implements ISelfSerializable {

    private static final long serialVersionUID = 1L;

    protected Person(
            String id,
            String legalFirstName,
            String legalLastName,
            LocalDate dateOfBirth,
            Gender gender,
            String email,
            String phone,
            Address address
    ) throws IllegalArgumentException {
        if (id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank!");
        }
        this.id = id;
        this.legalFirstName = legalFirstName;
        this.legalLastName = legalLastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @NonNull
    protected String id;
    @NonNull
    protected String legalFirstName;
    @NonNull
    protected String legalLastName;
    @NonNull
    protected LocalDate dateOfBirth;
    @NonNull
    protected Gender gender;

    @NonNull
    protected String email;
    @NonNull
    protected String phone;
    @NonNull
    protected Address address;

    public abstract UserType getType();

    @Override
    public Path getPath() {
        return Path.of(this.getId());
    }
}
