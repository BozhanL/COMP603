package com.example.assessment.backend;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.With;

@Value
@With
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Manager extends Person implements IAuthenticatable {

    private static final long serialVersionUID = 1L;
    private static final UserType TYPE = UserType.MANAGER;

    @NonNull
    @Getter(AccessLevel.PROTECTED)
    protected String password;

    public Manager(
            @NonNull String id,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull Address address,
            @NonNull String password
    ) throws IllegalArgumentException {
        super(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
        this.password = password;
    }

    @Override
    public Manager withId(@NonNull String id) throws IllegalArgumentException {
        return Objects.equals(this.id, id) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withLegalFirstName(@NonNull String legalFirstName) {
        return Objects.equals(this.legalFirstName, legalFirstName) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withLegalLastName(@NonNull String legalLastName) {
        return Objects.equals(this.legalLastName, legalLastName) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withDateOfBirth(@NonNull LocalDate dateOfBirth) {
        return Objects.equals(this.dateOfBirth, dateOfBirth) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withGender(@NonNull Gender gender) {
        return Objects.equals(this.gender, gender) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withEmail(@NonNull String email) {
        return Objects.equals(this.email, email) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withPhone(@NonNull String phone) {
        return Objects.equals(this.phone, phone) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withAddress(@NonNull Address address) {
        return Objects.equals(this.address, address) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    public Manager withPassword(@NonNull String password) {
        return Objects.equals(this.password, password) ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public UserType getType() {
        return getTypeStatic();
    }

    public static UserType getTypeStatic() {
        return TYPE;
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
