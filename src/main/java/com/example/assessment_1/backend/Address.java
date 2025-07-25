package com.example.assessment_1.backend;

import java.util.Objects;
import lombok.Data;

@Data
public class Address {

    private String unit;
    private String streetNumber;
    private String streetName;
    private String suburb;
    private String city;
    private String state;
    private String country;
    private String postCode;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        if (Objects.nonNull(unit)) {
            s.append(unit);
            s.append(' ');
        }

        if (Objects.nonNull(streetNumber)) {
            s.append(streetNumber);
            s.append(' ');
        }

        if (Objects.nonNull(streetName)) {
            s.append(streetName);
            s.append(' ');
        }

        if (Objects.nonNull(suburb)) {
            s.append(suburb);
            s.append(' ');
        }

        if (Objects.nonNull(city)) {
            s.append(city);
            s.append(' ');
        }

        if (Objects.nonNull(state)) {
            s.append(state);
            s.append(' ');
        }

        if (Objects.nonNull(country)) {
            s.append(country);
            s.append(' ');
        }

        if (Objects.nonNull(postCode)) {
            s.append(postCode);
        }

        return s.toString().trim();
    }
}
