package com.example.assessment_1.backend;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class Settings {

    @NonNull
    @Getter
    @Setter
    private static String databaseLocation = "~/.student/";
}
