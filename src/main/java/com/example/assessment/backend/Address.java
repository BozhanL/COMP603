package com.example.assessment.backend;

import java.io.Serializable;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@With
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

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
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (!unit.isBlank()) {
            s.append(unit);
            s.append(' ');
        }

        if (!streetNumber.isBlank()) {
            s.append(streetNumber);
            s.append(' ');
        }

        if (!streetName.isBlank()) {
            s.append(streetName);
            s.append(' ');
        }

        if (!suburb.isBlank()) {
            s.append(suburb);
            s.append(' ');
        }

        if (!city.isBlank()) {
            s.append(city);
            s.append(' ');
        }

        if (!state.isBlank()) {
            s.append(state);
            s.append(' ');
        }

        if (!country.isBlank()) {
            s.append(country);
            s.append(' ');
        }

        if (!postCode.isBlank()) {
            s.append(postCode);
        }

        return s.toString().trim();
    }
}
