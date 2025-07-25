package com.example.assessment_1.backend;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Person implements IPerson {

    @Getter
    @Setter
    protected String legalFirstName;

    @Getter
    @Setter
    protected String legalLastName;

    @Getter
    @Setter
    protected LocalDate dateOfBirth;

    @Getter
    @Setter
    protected Gender gender;

    @Getter
    @Setter
    protected Residency residencyStatus;

    @Getter
    @Setter
    protected String email = "";

    @Getter
    @Setter
    protected String phone = "";

    @NonNull
    @Getter
    @Setter
    protected Address address = new Address();

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum Residency {
        DOMESTIC, INTERNATIONAL
    }

    @Data
    public static class Address {

        public String unit;
        public String streetNumber;
        public String streetName;
        public String suburb;
        public String city;
        public String state;
        public String country;
        public String postCode;

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
