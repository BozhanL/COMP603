package com.example.assessment.cli;

import com.example.assessment.backend.IAuthenticatable;
import com.example.assessment.backend.IPersonBackend;
import com.example.assessment.backend.Person;
import com.example.assessment.backend.PersonFileBackend;

import java.util.Scanner;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class AssessmentOne {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Welcome page - use ASCII art
        System.out.println("               _                           ");
        System.out.println("              | |                          ");
        System.out.println(" __      _____| | ___ ___  _ __ ___   ___  ");
        System.out.println(" \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ ");
        System.out.println("  \\ V  V /  __/ | (_| (_) | | | | | |  __/ ");
        System.out.println("   \\_/\\_/_\\___|_|\\___\\___/|_| |_| |_|\\___| ");
        System.out.println("  / ____|_   _|  \\/  |/ ____|              ");
        System.out.println(" | (___   | | | \\  / | (___                ");
        System.out.println("  \\___ \\  | | | |\\/| |\\___ \\               ");
        System.out.println("  ____) |_| |_| |  | |____) |              ");
        System.out.println(" |_____/|_____|_|  |_|_____/               ");
        System.out.println("                                           ");

        // Ask for database path
        System.out.print("Enter database path (press Enter for default): ");
        String path = scanner.nextLine().trim();

        IPersonBackend pb = null;

        while (pb == null) {
            // Testing pathway
            try {
                if (path.isBlank()) {  // use ifBlank > isEmpty
                    pb = new PersonFileBackend();
                    System.out.println("Database = default");
                } else {
                    pb = new PersonFileBackend(path);
                    System.out.println("Database = " + path);
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (InvalidPathException e) {
                System.out.println("Error: invalid path");
            }
        }

        // Logging in
        String id = "";

        while (id.isBlank()) {
            System.out.print("Enter User ID: ");
            id = scanner.nextLine().trim();

            if (id.isBlank()) {
                System.out.println("ID cannot be blank, try again");
            }
        }

        try {
            Person p = pb.getPersonById(id);
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }

        while (true) {
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            try {
                if (p.safeCheckPassword(password)) {
                    System.out.println("Login Successful");
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
    }

}

// catch IO excception
// catch invalid path
