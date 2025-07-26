package com.example.assessment.backend;

import java.time.LocalDate;
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
        return this.id == id ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withLegalFirstName(@NonNull String legalFirstName) {
        return this.legalFirstName == legalFirstName ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withLegalLastName(@NonNull String legalLastName) {
        return this.legalLastName == legalLastName ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withDateOfBirth(@NonNull LocalDate dateOfBirth) {
        return this.dateOfBirth == dateOfBirth ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withGender(@NonNull Gender gender) {
        return this.gender == gender ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withEmail(@NonNull String email) {
        return this.email == email ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withPhone(@NonNull String phone) {
        return this.phone == phone ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    @Override
    public Manager withAddress(@NonNull Address address) {
        return this.address == address ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
    }

    public Manager withPassword(@NonNull String password) {
        return this.password == password ? this : new Manager(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, password);
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
        byte[] a = this.getPassword().getBytes();
        byte[] b = other.getBytes();

        if (a.length != b.length) {
            return false;
        }

        int ret = 0;

        for (int i = 0; i < a.length; i++) {
            ret |= a[i] ^ b[i];
        }

        try {
            Thread.sleep(random.nextInt(200, 1000));
        } catch (InterruptedException ex) {
        }

        return ret == 0;
    }
}
