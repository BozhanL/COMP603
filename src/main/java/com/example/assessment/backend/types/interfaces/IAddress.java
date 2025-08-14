package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.Address;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface IAddress extends Serializable {

    public static IAddress of(
            @NonNull String unit,
            @NonNull String streetNumber,
            @NonNull String streetName,
            @NonNull String suburb,
            @NonNull String city,
            @NonNull String state,
            @NonNull String country,
            @NonNull String postCode
    ) {
        return Address.of(unit, streetNumber, streetName, suburb, city, state, country, postCode);
    }

    public abstract IAddress withUnit(@NonNull String unit);

    public abstract IAddress withStreetNumber(@NonNull String streetNumber);

    public abstract IAddress withStreetName(@NonNull String streetName);

    public abstract IAddress withSuburb(@NonNull String suburb);

    public abstract IAddress withCity(@NonNull String city);

    public abstract IAddress withState(@NonNull String state);

    public abstract IAddress withCountry(@NonNull String country);

    public abstract IAddress withPostCode(@NonNull String postCode);

    public abstract String getUnit();

    public abstract String getStreetNumber();

    public abstract String getStreetName();

    public abstract String getSuburb();

    public abstract String getCity();

    public abstract String getState();

    public abstract String getCountry();

    public abstract String getPostCode();
}
