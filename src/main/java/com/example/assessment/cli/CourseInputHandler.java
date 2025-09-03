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

// This is the helper to prompt user to enter course information
@CheckReturnValue
@AllArgsConstructor
public class CourseInputHandler {

    @NonNull
    private final Scanner scanner;

//    Ask user for Department Code
    public String getDepartmentCode() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Enter department code(x for exit) (2-4 letters, e.g., 'COMP'): ");
//            Get the input
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
//            Check whether it is blank or want to stop
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            } else if (input.matches("[A-Z]{2,4}")) {
                return input;
            }
            System.out.println("Error: Invalid format. Use 2-4 letters (e.g., 'COMP', 'MATH').");
        }
    }

//    Ask user for Course Level
    public int getCourseLevel() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Enter course level(x for exit) (1-9): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: course level must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            try {
//                Convert it to int
                int level = Integer.parseInt(input);
//                If it is in range, return it
                if (level >= 1 && level <= 9) {
                    return level;
                }
                System.out.println("Error: Level must be between 1-9.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number (1-9).");
            }
        }
    }

//    Ask user for Course Number
    public int getCourseNumber() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Enter course number(x for exit) (00-99): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: course number must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            try {
//                Convert it to int
                int number = Integer.parseInt(input);
//                If it is in range, return it
                if (number >= 0 && number <= 99) {
                    return number;
                }
                System.out.println("Error: Number must be between 00-99.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter 2 digits (e.g., '00', '99').");
            }
        }
    }

//    Ask user for Course Name
    public String getCourseName() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Enter course name(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Course name cannot be blank.");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            return input;
        }
    }

//    Ask user for Credit Points
    public int getCreditPoints() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Enter credit points(x for exit) (e.g., 15): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: credit points cannot be blank.");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            try {
//                Convert it to int
                int points = Integer.parseInt(input);
//                If it is in range, return it
                if (points > 0) {
                    return points;
                }
                System.out.println("Error: Points must be positive.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

//    Ask user for Description
    public String getDescription() throws StopOperationException {
//            Ask for input
        System.out.print("Enter course description(x for exit) (optional): ");
//            Get the input
        String input = scanner.nextLine().trim();
//            Check whether user want to stop
        if ("x".equalsIgnoreCase(input)) {
            throw new StopOperationException();
        }

        return input;
    }

//    Prompt user to modify a course
    public ICourse getModifiedCourse(@NonNull ICourse ori) {
        ICourse n = ori;
        while (true) {
//            Print options
            System.out.println("1. Change Course Code");
            System.out.println("2. Change Name");
            System.out.println("3. Change Points");
            System.out.println("4. Change Description");
            System.out.println();
            System.out.println("5. Save\t6. Exit");
            System.out.print("Select an option(1-6): ");

//            Get the input
            String choice = scanner.nextLine().trim();

//            Execute
            try {
                switch (choice) {
                    case "1" -> {
                        String deptCode = this.getDepartmentCode();
                        int level = this.getCourseLevel();
                        int courseNum = this.getCourseNumber();
                        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

                        n = n.withCode(courseCode);
                    }
                    case "2" ->
                        n = n.withName(this.getCourseName());
                    case "3" ->
                        n = n.withPoints(this.getCreditPoints());
                    case "4" ->
                        n = n.withDescription(this.getDescription());
                    case "5" ->
                        ori = n;
                    case "6" -> {
                        return ori;
                    }
                    default ->
                        System.out.println("Error: Invalid option. Try again.");
                }
            } catch (StopOperationException e) {
                System.out.println("Operation canceled!");
            }
        }
    }

    // generalized confirmation
    @FormatMethod
    public void promptConfirmation(@FormatString String message, Object... args) throws StopOperationException {
//        Get the formatted message
        String formattedMessage = String.format(message, args);

        while (true) {
//            Ask for input
            System.out.print(formattedMessage + " (Y/N): ");
//            Get the input
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());

//            Check whether to comtinue
            if (input.equals("Y")) {
                return;
            } else if (input.equals("N")) {
                throw new StopOperationException();
            }
            System.out.println("Error: Please enter 'Y' or 'N'.");
        }
    }
}
