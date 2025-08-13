package com.example.assessment.backend.types;

import java.io.Serializable;
import lombok.NonNull;

public interface IAddress extends Serializable {

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
