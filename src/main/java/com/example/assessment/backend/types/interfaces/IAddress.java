package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.derby.IToHibernateEntity;
import com.example.assessment.backend.types.classes.Address;
import com.example.assessment.backend.types.entity.AddressEntity;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import lombok.NonNull;

// This is the interface for address of a person
@Immutable
@CheckReturnValue
public interface IAddress extends Serializable, IPrettyPrint, IToHibernateEntity<AddressEntity> {

//    Static construtor to create IAddress
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

//    Create a new IAddress object with new unit
    public abstract IAddress withUnit(@NonNull String unit);

//    Create a new IAddress object with new streetNumber
    public abstract IAddress withStreetNumber(@NonNull String streetNumber);

//    Create a new IAddress object with new streetName
    public abstract IAddress withStreetName(@NonNull String streetName);

//    Create a new IAddress object with new suburb
    public abstract IAddress withSuburb(@NonNull String suburb);

//    Create a new IAddress object with new city
    public abstract IAddress withCity(@NonNull String city);

//    Create a new IAddress object with new state
    public abstract IAddress withState(@NonNull String state);

//    Create a new IAddress object with new country
    public abstract IAddress withCountry(@NonNull String country);

//    Create a new IAddress object with new postCode
    public abstract IAddress withPostCode(@NonNull String postCode);

//    Return the unit of address
    public abstract String getUnit();

//    Return the streetNumber of address
    public abstract String getStreetNumber();

//    Return the streetName of address
    public abstract String getStreetName();

//    Return the suburb of address
    public abstract String getSuburb();

//    Return the city of address
    public abstract String getCity();

//    Return the state of address
    public abstract String getState();

//    Return the country of address
    public abstract String getCountry();

//    Return the postCode of address
    public abstract String getPostCode();
}
