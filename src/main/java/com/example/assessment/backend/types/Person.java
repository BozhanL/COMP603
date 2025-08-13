package com.example.assessment.backend.types;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;

@With
@Value
@NonFinal
public abstract class Person implements IPerson {

    private static final long serialVersionUID = 1L;

    protected Person(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull IAddress address
    ) throws IllegalArgumentException {
        if (id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank!");
        }
        this.id = id;
        this.password = password;
        this.legalFirstName = legalFirstName;
        this.legalLastName = legalLastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    @NonNull
    @With(AccessLevel.NONE)
    protected String id;

    @NonNull
    @ToString.Exclude
    @Getter(AccessLevel.PRIVATE)
    protected String password;

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
    protected IAddress address;

    @Override
    public Path getPath() {
        return Path.of(String.format("%s_%s_%s.bin", this.getId(), this.getLegalFirstName(), this.getLegalLastName()));
    }

    @Override
    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).normalized().getYears();
    }

    @Override
    public boolean safeCheckPassword(@NonNull String other) {
        byte[] a = this.getPassword().getBytes(StandardCharsets.UTF_8);
        byte[] b = other.getBytes(StandardCharsets.UTF_8);

        if (a.length != b.length) {
            return false;
        }

        int ret = 0;

        for (int i = 0; i < a.length; i++) {
            ret |= a[i] ^ b[i];
        }

        return ret == 0;
    }
}
