package com.example.assessment.cli;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.util.Locale;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CheckReturnValue
@AllArgsConstructor
public class CourseInputHandler {

    @NonNull
    private final Scanner scanner;

    public String getDepartmentCode() throws StopOperationException {
        while (true) {
            System.out.print("Enter department code(x for exit) (2-4 letters, e.g., 'COMP'): ");
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            } else if (input.matches("[A-Z]{2,4}")) {
                return input;
            }
            System.out.println("Invalid format. Use 2-4 letters (e.g., 'COMP', 'MATH').");
        }
    }

    public int getCourseLevel() throws StopOperationException {
        while (true) {
            System.out.print("Enter course level(x for exit) (1-9): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            try {
                int level = Integer.parseInt(input);
                if (level >= 1 && level <= 9) {
                    return level;
                }
                System.out.println("Error: Level must be between 1-9.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number (1-9).");
            }
        }
    }

    public int getCourseNumber() throws StopOperationException {
        while (true) {
            System.out.print("Enter course number(x for exit) (00-99): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            try {
                int number = Integer.parseInt(input);
                if (number >= 0 && number <= 99) {
                    return number;
                }
                System.out.println("Error: Number must be between 00-99.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter 2 digits (e.g., '00', '99').");
            }
        }
    }

    public String getCourseName() throws StopOperationException {
        while (true) {
            System.out.print("Enter course name(x for exit): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            if (!input.isBlank()) {
                return input;
            }
            System.out.println("Error: Course name cannot be blank.");
        }
    }

    public int getCreditPoints() throws StopOperationException {
        while (true) {
            System.out.print("Enter credit points(x for exit) (e.g., 15): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            try {
                int points = Integer.parseInt(input);
                if (points > 0) {
                    return points;
                }
                System.out.println("Error: Points must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    public String getDescription() throws StopOperationException {
        System.out.print("Enter course description(x for exit) (optional): ");
        String input = scanner.nextLine().trim();
        if ("x".equalsIgnoreCase(input)) {
            throw new StopOperationException();
        }

        return input;
    }

    public ICourse getModifiedCourse(ICourse ori) throws StopOperationException {
        while (true) {
            System.out.println("1. Change Course Code");
            System.out.println("2. Change Name");
            System.out.println("3. Change Points");
            System.out.println("4. Change Description");
            System.out.println("5. Save\t6. Exit");
            System.out.print("Select an option(1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    String deptCode = this.getDepartmentCode();
                    int level = this.getCourseLevel();
                    int courseNum = this.getCourseNumber();
                    ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

                    ori = ori.withCode(courseCode);
                }
                case "2" ->
                    ori = ori.withName(this.getCourseName());
                case "3" ->
                    ori = ori.withPoints(this.getCreditPoints());
                case "4" ->
                    ori = ori.withDescription(this.getDescription());
                case "5" -> {
                    return ori;
                }
                case "6" ->
                    throw new StopOperationException();
                default ->
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // generalized confirmation
    @FormatMethod
    public void promptConfirmation(@FormatString String message, Object... args) throws StopOperationException {
        String formattedMessage = String.format(message, args);

        while (true) {
            System.out.print(formattedMessage + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());

            if (input.equals("Y")) {
                return;
            } else if (input.equals("N")) {
                throw new StopOperationException();
            }
            System.out.println("Error: Please enter 'Y' or 'N'.");
        }
    }
}
