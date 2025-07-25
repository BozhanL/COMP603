package com.example.assessment_1.backend;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public abstract class Person implements IPerson {

    @NonNull
    protected String id;
    @NonNull
    protected String legalFirstName;
    @NonNull
    protected String legalLastName;
    @NonNull
    protected LocalDate dateOfBirth;
    @NonNull
    protected Gender gender = Gender.OTHER;

    @NonNull
    protected String email;
    protected String phone;
    @NonNull
    protected Address address = new Address();
}
