package com.example.assessment.backend.types.enums;

import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This contains grade for student
@CheckReturnValue
@AllArgsConstructor
public enum Grade {
    AP("A+"), A("A"), AM("A-"),
    BP("B+"), B("B"), BM("B-"),
    CP("C+"), C("C"), CM("C-"),
    D("D"), DS("DS"), W("W"),
    S("S"), CO("CO"), YP("YP"),
    YX("YX"), STC("STC"), DNC("DNC"),
    DSN("DSN"), UX("UX"), NA("N/A");

    private final String name;

//    Return the Grade that have same name compare to input
    public static Grade getEnum(@NonNull String value) {
        Optional<Grade> g = Arrays.stream(values())
                .filter((v) -> v.toString().equalsIgnoreCase(value))
                .findAny();

        return g.orElseThrow(IllegalArgumentException::new);
    }

//    Return a string contain all values
    public static String allValues() {
        return Stream.of(values())
                .map(Objects::toString)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return this.name;
    }
}
