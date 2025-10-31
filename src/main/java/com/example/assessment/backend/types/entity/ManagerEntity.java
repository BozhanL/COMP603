package com.example.assessment.backend.types.entity;

import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.interfaces.IManager;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

// Entity for IManager
@Data
@Entity
@CheckReturnValue
@ToString(callSuper = true)
@DiscriminatorValue("MANAGER")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ManagerEntity extends PersonEntity<IManager> {

    private ManagerEntity(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull AddressEntity address
    ) {
        super(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

    public static ManagerEntity of(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull AddressEntity address
    ) {
        return new ManagerEntity(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

    @Override
    public IManager toImmutable() {
        return IManager.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address.toImmutable());
    }
}
