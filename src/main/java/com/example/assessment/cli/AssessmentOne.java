package com.example.assessment.cli;

import com.example.assessment.backend.IPersonBackend;
import com.example.assessment.backend.Person;

import java.util.Scanner;
import lombok.Cleanup;

public class AssessmentOne {

    public static void main(String[] args) {

        @Cleanup
        Scanner scanner = new Scanner(System.in);

        Welcome.showAsciiArt();

        IPersonBackend pb = Welcome.askForDatabase(scanner);

        Person person = Welcome.login(scanner, pb);

    }
}
