package com.example.assessment.backend.types.classes;

import com.example.assessment.backend.types.entity.AddressEntity;
import com.example.assessment.backend.types.entity.ManagerEntity;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.UserType;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IManager;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.With;

// This class implements IManager,
// and to store information for someone who has full access to the system
@With
@Value
@Immutable
@CheckReturnValue
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Manager extends Person implements IManager {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final UserType TYPE = UserType.MANAGER;

    private Manager(
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
        super(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

    public static Manager of(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull IAddress address
    ) {
        return new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new password
    @Override
    public Manager withPassword(@NonNull String password) {
        return Objects.equals(this.password, password) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new legalFirstName
    @Override
    public Manager withLegalFirstName(@NonNull String legalFirstName) {
        return Objects.equals(this.legalFirstName, legalFirstName) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new legalLastName
    @Override
    public Manager withLegalLastName(@NonNull String legalLastName) {
        return Objects.equals(this.legalLastName, legalLastName) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new dateOfBirth
    @Override
    public Manager withDateOfBirth(@NonNull LocalDate dateOfBirth) {
        return Objects.equals(this.dateOfBirth, dateOfBirth) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new gender
    @Override
    public Manager withGender(@NonNull Gender gender) {
        return Objects.equals(this.gender, gender) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new email
    @Override
    public Manager withEmail(@NonNull String email) {
        return Objects.equals(this.email, email) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new phone
    @Override
    public Manager withPhone(@NonNull String phone) {
        return Objects.equals(this.phone, phone) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Create a new Manager object with new address
    @Override
    public Manager withAddress(@NonNull IAddress address) {
        return Objects.equals(this.address, address) ? this : new Manager(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Get the type of this class
//    Always return UserType.MANAGER
    @Override
    public UserType getType() {
        return getTypeStatic();
    }

//    Get the type of this class
//    Always return UserType.MANAGER
    public static UserType getTypeStatic() {
        return TYPE;
    }

    @Override
    public String prettyToString() {
        return super.prettyToString();
    }

    @Override
    public ManagerEntity toEntity() {
        AddressEntity ae = address.toEntity();
        return ManagerEntity.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, ae);
    }
}
