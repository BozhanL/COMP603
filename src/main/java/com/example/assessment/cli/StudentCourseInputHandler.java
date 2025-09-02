package com.example.assessment.cli;

import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This is the helper to prompt user to enter student course information
@CheckReturnValue
@AllArgsConstructor
public class StudentCourseInputHandler {

    @NonNull
    private final Scanner scanner;

//    This method takes original student courses information as input,
//    and return new courses after modified
    public ImmutableMap<String, IStudentCourseInfo> changeCourse(
            @NonNull ImmutableMap<String, IStudentCourseInfo> courses
    ) throws StopOperationException {
//        Print original course information with id
        ImmutableList<IStudentCourseInfo> values = courses.values().asList();
        for (int i = 0; i < values.size(); i++) {
            System.out.printf("-----------------%d-----------------\n", i);
            System.out.println(values.get(i).prettyToString());
        }

//        Ask user to select one to modify
//        -1 means to create a new one
        int selected = Integer.MIN_VALUE;
        while (true) {
            System.out.print("Please enter the number on top of the course for change(-1: ceate new, x: exit): ");
//            Get the user input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or user want to stop this operation
            if (input.isBlank()) {
                System.out.println("Status must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

//            Convert the input to number
            try {
                selected = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                System.out.printf("Error: Please enter a number (-1-%d).\n", values.size() - 1);
                continue;
            }

//            Check whether the number is valid or not
            if (selected < -1 || selected >= values.size()) {
                System.out.printf("Error: Please enter a number (-1-%d).\n", values.size() - 1);
                continue;
            }
//            Stop the loop if user entered a currect value
            break;
        }

//        Convert the course to mutable map
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>(courses);
        IStudentCourseInfo change;
        if (selected < 0) {
//            Create a new course
            change = this.getStudentCourseInfo();
        } else {
//            Get the old one
            change = sci.remove(values.get(selected).getCourseCode());
//            modify it, may be null if user want to delete it
            change = modifyStudentCourseInfo(change);
        }

//        insert to the map if it is not null
        if (change != null) {
            sci.put(change.getCourseCode(), change);
        }

//        Return the immutable map
        return ImmutableMap.copyOf(sci);
    }

//    Ask user for the student course information and generate a new one
    private IStudentCourseInfo getStudentCourseInfo() throws StopOperationException {
//        Ask for course code
        String courseCode = this.getCourseCode();
//        Ask for grade
        Grade grade = this.getGrade();
//        Ask for start date
        LocalDate starts = this.getStarts();
//        Ask for study location
        String location = this.getLocation();

//        Create a new one
        IStudentCourseInfo c = IStudentCourseInfo.of(courseCode, grade, starts, location);

        return c;
    }

//    Ask user what to change for a course, and return a new one
    private IStudentCourseInfo modifyStudentCourseInfo(IStudentCourseInfo c)
            throws StopOperationException {
        while (true) {
//            Print options
            System.out.println("1. Change Course Code\t4. Change Location");
            System.out.println("2. Change Grade\t5. Delete this course");
            System.out.println("3. Change Start Date");
            System.out.println();
            System.out.println("6. Save\t7. Exit");
            System.out.print("Select an option(1-7): ");

//            Get the input
            String choice = scanner.nextLine().trim();

//            Execute
            switch (choice) {
                case "1" ->
                    c = c.withCourseCode(this.getCourseCode());
                case "2" ->
                    c = c.withGrade(this.getGrade());
                case "3" ->
                    c = c.withStarts(this.getStarts());
                case "4" ->
                    c = c.withLocation(this.getLocation());
                case "5" -> {
                    return null;
                }
                case "6" -> {
                    return c;
                }
                case "7" ->
                    throw new StopOperationException();
                default ->
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

//    Ask user for course code
    private String getCourseCode() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Course Code(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Course Code must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

//            print result
            System.out.printf("Course Code = '%s'\n", input);
            return input;
        }
    }

//    Ask user for grade
    private Grade getGrade() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Grade(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Grade must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            Grade g;
//            Convert it to grade
            try {
                g = Grade.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.printf("Error: input must be one of: %s\n", Grade.allValues());
                continue;
            }

            System.out.printf("Grade = '%s'\n", g);
            return g;
        }
    }

//    Ask user for start date
    private LocalDate getStarts() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Starts(x for exit) (e.g, 2025-08-16): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Starts must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            LocalDate date;
//            Convert it to LocalDate
            try {
                date = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format!");
                continue;
            }

            System.out.printf("Starts = '%s'\n", date);
            return date;
        }
    }

//    Ask user for study location
    private String getLocation() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Location(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Location must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Location = '%s'\n", input);
            return input;
        }
    }
}
