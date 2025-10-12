package com.example.assessment.backend.types.classes;

import com.example.assessment.backend.types.entity.PersonEntity;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;

// This is the base class for all person in this system
@With
@Value
@NonFinal
@Immutable
@CheckReturnValue
public abstract class Person<T extends PersonEntity<?>> implements IPerson<T> {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    @With(AccessLevel.NONE)
    protected String id;

    @NonNull
    @ToString.Exclude
    @Getter(AccessLevel.PROTECTED)
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

    @Override
    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now(ZoneId.systemDefault())).normalized().getYears();
    }

//    Safely check password, ensure constant time comparison
    @Override
    public boolean safeCheckPassword(@NonNull String other) {
        byte[] a = this.getPassword().getBytes(StandardCharsets.UTF_8);
        byte[] b = other.getBytes(StandardCharsets.UTF_8);

//        Compare the length
        if (a.length != b.length) {
            return false;
        }

        int ret = 0;

//        Compare the data without early return
        for (int i = 0; i < a.length; i++) {
            ret |= a[i] ^ b[i];
        }

        return ret == 0;
    }

//    Convert it to string with format of one variable per line
    @Override
    public String prettyToString() {
        StringBuilder sb = new StringBuilder();

        sb.append("id: ");
        sb.append(id);

        sb.append("\nlegalFirstName: ");
        sb.append(legalFirstName);

        sb.append("\nlegalLastName: ");
        sb.append(legalLastName);

        sb.append("\ndateOfBirth: ");
        sb.append(dateOfBirth);

        sb.append("\nage: ");
        sb.append(getAge());

        sb.append("\ngender: ");
        sb.append(gender);

        sb.append("\nemail: ");
        sb.append(email);

        sb.append("\nphone: ");
        sb.append(phone);

        sb.append("\naddress: ");
        sb.append(address.prettyToString());

        return sb.toString();
    }
}
