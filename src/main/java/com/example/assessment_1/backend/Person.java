package com.example.assessment_1.backend;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public abstract class Person implements IPerson {

    @NonNull
    protected String id;
    @NonNull
    protected String legalFirstName;
    @NonNull
    protected String legalLastName;
    @NonNull
    protected LocalDate dateOfBirth;
    @NonNull
    protected Gender gender = Gender.OTHER;
    @NonNull
    protected Residency residencyStatus;

    protected String email;
    @NonNull
    protected String phone;
    @NonNull
    protected Address address = new Address();

    public static enum Gender {
        MALE, FEMALE, OTHER
    }

    public static enum Residency {
        DOMESTIC, INTERNATIONAL
    }

    @Data
    public static class Address {

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
}
