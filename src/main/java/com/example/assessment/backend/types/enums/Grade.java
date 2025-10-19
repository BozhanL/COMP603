package com.example.assessment.backend.types.enums;

import java.util.stream.Collectors;
import java.util.stream.Stream;

// This contains grade for student
public enum Grade {
    AP("A+"), A("A"), AM("A-"),
    BP("B+"), B("B"), BM("B-"),
    CP("C+"), C("C"), CM("C-"),
    D("D"), DS("DS"), W("W"),
    S("S"), CO("CO"), YP("YP"),
    YX("YX"), STC("STC"), DNC("DNC"),
    DSN("DSN"), UX("UX"), NA("N/A");

    private final String name;

    private Grade(String name) {
        this.name = name;
    }

//    Return the Grade that have same name compare to input
    public static Grade getEnum(String value) {
        for (Grade v : values()) {
            if (v.toString().equalsIgnoreCase(value)) {
                return v;
            }
        }

        throw new IllegalArgumentException();
    }

//    Return a string contain all values
    public static String allValues() {
        return Stream.of(values()).map((v) -> v.toString()).collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return this.name;
    }
}
