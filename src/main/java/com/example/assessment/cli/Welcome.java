package com.example.assessment.cli;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This class is used only when the program starts
// This class includes methods for initialize the program
@CheckReturnValue
@AllArgsConstructor
public class Welcome {

    @NonNull
    private final Scanner scanner;

//    Welcome message
    public static void showAsciiArt() {
        System.out.println("               _                                  ");
        System.out.println("              | |                                 ");
        System.out.println(" __      _____| | ___ ___  _ __ ___   ___         ");
        System.out.println(" \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ ");
        System.out.println("  \\ V  V /  __/ | (_| (_) | | | | | |  __/       ");
        System.out.println("   \\_/\\_/_\\___|_|\\___\\___/|_| |_| |_|\\___|  ");
        System.out.println("  / ____|_   _|  \\/  |/ ____|                    ");
        System.out.println(" | (___   | | | \\  / | (___                      ");
        System.out.println("  \\___ \\  | | | |\\/| |\\___ \\                 ");
        System.out.println("  ____) |_| |_| |  | |____) |                     ");
        System.out.println(" |_____/|_____|_|  |_|_____/                      ");
        System.out.println("                                                  ");
    }

//    Fix spell by Copilot
//    Ask for database location and construct it.
    public ICombinedBackend askForDatabase() {
        ICombinedBackend pb = null;

        while (pb == null) {
            System.out.printf("Enter database path (press Enter for %s): ", ICombinedBackend.DEFAULT_DATA_LOCATION);
            String path = scanner.nextLine().trim();

            try {
                if (path.isBlank()) {
                    pb = ICombinedBackend.of();
                    System.out.printf("Database = %s\n", ICombinedBackend.DEFAULT_DATA_LOCATION);
                } else {
                    pb = ICombinedBackend.of(path);
                    System.out.printf("Database = %s\n", path);
                }
            } catch (IOException e) {
                System.out.printf("Error: %s\n", e.getMessage());
            } catch (InvalidPathException e) {
                System.out.println("Error: invalid path");
            }
        }

        return pb;
    }

//    Login to the system
    public IPerson login(@NonNull IPersonBackend pb) {
        IPerson p = null;

        while (true) {
//            Get person from ID
            while (p == null) {
                String id = "";
                while (id.isBlank()) {
                    System.out.print("Enter User ID: ");
                    id = scanner.nextLine().trim();

                    if (id.isBlank()) {
                        System.out.println("Error: ID cannot be blank, try again.");
                    }
                }

                try {
                    p = pb.getPersonById(id);
                } catch (FileNotFoundException e) {
                    System.out.println("Error: No user found with that ID, try again.");
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (DatabaseCorruptedException e) {
                    System.out.println("Error: data corrupted, try another user");
                }

                if (p == null) {
                    System.out.printf("Error: unable to load '%s'\n", id);
                }
            }

//            Check whether user knows the password
            System.out.print("Enter Password: ");
            String password = scanner.nextLine().trim();
            if (p.safeCheckPassword(password)) {
                System.out.println("Login Successful");
                return p;
            } else {
//                Reset login status, ask for ID again
                p = null;
                System.out.println("Error: Incorrect password.");
            }
        }
    }
}
