package com.example.assessment.cli;

import java.util.Scanner;

public class CourseInputHandler {

    private final Scanner scanner;

    public CourseInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptDepartmentCode() {
        while (true) {
            System.out.print("Enter department code (2-4 letters, e.g., 'CS'): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("[A-Z]{2,4}")) {
                return input;
            }
            System.out.println("Invalid format. Use 2-4 letters (e.g., 'CS', 'MATH').\n");
        }
    }

    public int promptCourseLevel() {
        while (true) {
            System.out.print("Enter course level (5-10): ");
            String input = scanner.nextLine().trim();

            try {
                int level = Integer.parseInt(input);
                if (level >= 5 && level <= 10) {
                    return level;
                }
                System.out.println("Error: Level must be between 5-10.\n");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number (5-10).\n");
            }
        }
    }

    public int promptCourseNumber() {
        while (true) {
            System.out.print("Enter course number (05-99): ");
            String input = scanner.nextLine().trim();

            try {
                int number = Integer.parseInt(input);
                if (number >= 1 && number <= 99) {
                    return number;
                }
                System.out.println("Error: Number must be between 01-99.\n");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter 2 digits (e.g., '01', '99').\n");
            }
        }
    }

    public String promptCourseName() {
        while (true) {
            System.out.print("Enter course name: ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Course name cannot be blank.\n");
        }
    }

    public int promptCreditPoints() {
        while (true) {
            System.out.print("Enter credit points (e.g., 100): ");
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

    public boolean promptConfirmation(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Y")) {
                return true;
            } else if (input.equals("N")) {
                return false;
            }
            System.out.println("Error: Please enter 'Y' or 'N'.\n");
        }
    }
}
