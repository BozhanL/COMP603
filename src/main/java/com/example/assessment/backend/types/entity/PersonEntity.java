package com.example.assessment.backend.types.entity;

import com.example.assessment.backend.derby.IEntity;
import com.example.assessment.backend.derby.IToImmutable;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

// Entity for IPerson
@Data
@Entity
@Inheritance
@CheckReturnValue
@DiscriminatorColumn(name = "TYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PersonEntity<T extends IPerson> implements IToImmutable<T>, IEntity<T> {

    @Id
    @NonNull
    protected String id;

    @NonNull
    @ToString.Exclude
    protected String password;

    @NonNull
    protected String legalFirstName;
    @NonNull
    protected String legalLastName;
    @NonNull
    protected LocalDate dateOfBirth;
    @NonNull
    @Enumerated(EnumType.STRING)
    protected Gender gender;

    @NonNull
    protected String email;
    @NonNull
    protected String phone;

    @NonNull
    @Embedded
    protected AddressEntity address;
}
