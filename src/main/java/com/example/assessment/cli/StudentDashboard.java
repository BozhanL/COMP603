package com.example.assessment.cli;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This is the main dashboard for student
@CheckReturnValue
@AllArgsConstructor
public class StudentDashboard implements IMainDashboard {

    @NonNull
    private final Scanner scanner;
    @NonNull
    private final ICombinedBackend cb;
    @NonNull
    private IStudent p;
    @NonNull
    private final PersonInputHandler pih;

    public StudentDashboard(@NonNull Scanner scanner, @NonNull ICombinedBackend cb, @NonNull IStudent p) {
        this(scanner, cb, p, new PersonInputHandler(scanner));
    }

//    Display the menu
    @Override
    public void displayMenu() {
        while (true) {
//            Print the menu
            printMainMenu();
//            Ask user for option
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" ->
                        System.out.println(this.p.prettyToString());
                    case "2" ->
                        this.p = this.p.withPassword(this.pih.getPassword());
                    case "3" ->
                        this.p = this.p.withGender(this.pih.getGender());
                    case "4" ->
                        this.p = this.p.withEmail(this.pih.getEmail());
                    case "5" ->
                        this.p = this.p.withPhone(this.pih.getPhone());
                    case "6" -> {
                        System.out.println(this.p.getCourses().keySet());
                    }
                    case "7" ->
                        this.saveStudent();
                    case "8" -> {
                        return;
                    }
                    default ->
                        System.out.println("Error: Invalid option. Try again.");
                }
            } catch (StopOperationException e) {
                System.out.println("Operation canceled!");
            }
        }
    }

//    Save modified student into database
    private void saveStudent() {
        boolean success = true;
//        Max retry is 3
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
//                modify the student in database
                this.cb.modifyPerson(this.p);
//                exit the loop
                break;
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                success = false;
            }
        }

//        print error message is failed after three tries
        if (!success) {
            System.out.println("Error: Unable to modify student!");
        } else {
            System.out.println("Success");
        }
    }

    private static void printMainMenu() {
        System.out.println("[STUDENT DASHBOARD]");
        System.out.println("1. Get Current Information\t4. Change Email");
        System.out.println("2. Change Password\t\t5. Change Phone");
        System.out.println("3. Change Gender\t\t6. List Course");
        System.out.println();
        System.out.println("7. Save\t8. Exit");
        System.out.print("Select an option: ");
    }
}
