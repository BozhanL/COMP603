package com.example.assessment.backend.types.entity;

import com.example.assessment.backend.derby.IEntity;
import com.example.assessment.backend.derby.IToImmutable;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Embeddable
@CheckReturnValue
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressEntity implements IToImmutable<IAddress>, IEntity<IAddress> {

    @NonNull
    String unit;
    @NonNull
    String streetNumber;
    @NonNull
    String streetName;
    @NonNull
    String suburb;
    @NonNull
    String city;
    @NonNull
    String state;
    @NonNull
    String country;
    @NonNull
    String postCode;

    @Override
    public IAddress toImmutable() {
        return IAddress.of(unit, streetNumber, streetName, suburb, city, state, country, postCode);
    }
}
