package com.example.assessment.cli;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This is the main dashboard for manager
@CheckReturnValue
@AllArgsConstructor
public class ManagerDashboard implements IMainDashboard {

    @NonNull
    private final Scanner scanner;
    @NonNull
    private final CourseDashboard course;
    @NonNull
    private final PersonDashboard person;

    public ManagerDashboard(@NonNull Scanner scanner, @NonNull ICombinedBackend cb) {
        this(scanner, new CourseDashboard(scanner, cb), new PersonDashboard(scanner, cb));
    }

//    Display the menu
    @Override
    public void displayMenu() {
        while (true) {
//            Print the menu
            printMainMenu();
//            Ask user for option
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" ->
                    this.course.displayMenu();
                case "2" ->
                    this.person.displayMenu();
                case "3" -> {
                    return; // exit to previous menu
                }
                default ->
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("[MANAGER DASHBOARD]");
        System.out.println("1. Manage Course");
        System.out.println("2. Manage Person");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
    }
}
