package com.example.assessment.backend;

public enum Grade {
    AP("A+"), A("A"), AM("A-"),
    BP("B+"), B("B"), BM("B-"),
    CP("C+"), C("C"), CM("C-"),
    D("D"), DS("DS"), W("W"),
    S("S"), CO("CO"), YP("YP"),
    YX("YX"), STC("STC"), DNC("DNC"),
    DSN("DSN"), UX("UX");

    private final String name;

    private Grade(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
