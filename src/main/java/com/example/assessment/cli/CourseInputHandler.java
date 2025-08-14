package com.example.assessment.cli;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.util.Locale;
import java.util.Scanner;
import lombok.NonNull;

@CheckReturnValue
public class CourseInputHandler {

    private final Scanner scanner;

    public CourseInputHandler(@NonNull Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptDepartmentCode() {
        while (true) {
            System.out.print("Enter department code (2-4 letters, e.g., 'COMP'): ");
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());

            if (input.matches("[A-Z]{2,4}")) {
                return input;
            }
            System.out.println("Invalid format. Use 2-4 letters (e.g., 'COMP', 'MATH').\n");
        }
    }

    public int promptCourseLevel() {
        while (true) {
            System.out.print("Enter course level (1-9): ");
            String input = scanner.nextLine().trim();

            try {
                int level = Integer.parseInt(input);
                if (level >= 1 && level <= 9) {
                    return level;
                }
                System.out.println("Error: Level must be between 1-9.\n");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number (1-9).\n");
            }
        }
    }

    public int promptCourseNumber() {
        while (true) {
            System.out.print("Enter course number (00-99): ");
            String input = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(input);
                if (number >= 0 && number <= 99) {
                    return number;
                }
                System.out.println("Error: Number must be between 00-99.\n");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter 2 digits (e.g., '00', '99').\n");
            }
        }
    }

    public String promptCourseName() {
        while (true) {
            System.out.print("Enter course name: ");
            String input = scanner.nextLine().trim();

            if (!input.isBlank()) {
                return input;
            }
            System.out.println("Error: Course name cannot be blank.\n");
        }
    }

    public int promptCreditPoints() {
        while (true) {
            System.out.print("Enter credit points (e.g., 15): ");
            String input = scanner.nextLine().trim();

            try {
                int points = Integer.parseInt(input);
                if (points > 0) {
                    return points;
                }
                System.out.println("Error: Points must be positive.\n");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.\n");
            }
        }
    }

    public String promptDescription() {
        System.out.print("Enter course description (optional): ");
        return scanner.nextLine().trim();
    }

    // generalized confirmation
    @FormatMethod
    public boolean promptConfirmation(@FormatString String message, Object... args) {
        String formattedMessage = String.format(message, args);

        while (true) {
            System.out.print(formattedMessage + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());

            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            System.out.println("Error: Please enter 'Y' or 'N'.\n");
        }
    }

}
