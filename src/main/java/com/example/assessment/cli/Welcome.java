package com.example.assessment.cli;

import com.example.assessment.backend.file.PersonFileBackend;
import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@Immutable
@UtilityClass
@CheckReturnValue
public class Welcome {

    public static void showAsciiArt() {
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
    }

    public static IPersonBackend askForDatabase(Scanner scanner) {
        IPersonBackend pb = null;

        while (pb == null) {
            System.out.print("Enter database path (press Enter for default): ");
            String path = scanner.nextLine().trim();

            try {
                if (path.isBlank()) {
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

        return pb;
    }

    public static IPerson login(Scanner scanner, IPersonBackend pb) {
        IPerson p = null;

        while (p == null) {
            String id = "";

            while (id.isBlank()) {
                System.out.print("Enter User ID: ");
                id = scanner.nextLine().trim();

                if (id.isBlank()) {
                    System.out.println("ID cannot be blank, try again");
                }
            }

            try {
                p = pb.getPersonById(id);

                if (p == null) {
                    System.out.println("No user found with that ID, try again.");
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DatabaseCorruptedException e) {
                System.out.println("Error: data corrupted, try another user");
            }
        }

        while (true) {
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            try {
                if (p.safeCheckPassword(password)) {
                    System.out.println("Login Successful");
                    break;
                } else {
                    System.out.println("Incorrect password, try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        return p;
    }
}
