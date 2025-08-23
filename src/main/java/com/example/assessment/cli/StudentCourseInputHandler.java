package com.example.assessment.cli;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@CheckReturnValue
@AllArgsConstructor
public class StudentCourseInputHandler {

    @NonNull
    private final Scanner scanner;

    public ImmutableMap<String, IStudentCourseInfo> changeCourse(
            @NonNull ImmutableMap<String, IStudentCourseInfo> courses
    ) throws StopOperationException {
        ImmutableList<IStudentCourseInfo> values = courses.values().asList();
        for (int i = 0; i < values.size(); i++) {
            System.out.printf("-----------------%d-----------------\n", i);
            System.out.println(values.get(i).prettyToString());
        }
        int selected = 0;
        while (true) {
            System.out.print("Please enter the number on top of the course for change(-1: ceate new, x: exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Status must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            try {
                selected = Integer.parseInt(input);
                if (selected < -1 || selected >= values.size()) {
                    System.out.printf("Error: Please enter a number (-1-%d).\n", values.size() - 1);
                    continue;
                }
                break;
            } catch (NumberFormatException ex) {
                System.out.printf("Error: Please enter a number (-1-%d).\n", values.size() - 1);
            }
        }

        HashMap<String, IStudentCourseInfo> sci = new HashMap<>(courses);
        IStudentCourseInfo change;
        if (selected < 0) {
            change = this.getStudentCourseInfo();
        } else {
            change = sci.remove(values.get(selected).getCourseCode());
            change = modifyStudentCourseInfo(change);
        }

        if (Objects.nonNull(change)) {
            sci.put(change.getCourseCode(), change);
        }

        return ImmutableMap.copyOf(sci);
    }

    private IStudentCourseInfo getStudentCourseInfo() throws StopOperationException {
        String courseCode = this.getCourseCode();
        Grade grade = this.getGrade();
        LocalDate starts = this.getStarts();
        String location = this.getLocation();

        IStudentCourseInfo c = IStudentCourseInfo.of(courseCode, grade, starts, location);

        return c;
    }

    private IStudentCourseInfo modifyStudentCourseInfo(IStudentCourseInfo c)
            throws StopOperationException {
        while (true) {
            System.out.println("1. Change Course Code\t4. Change Location");
            System.out.println("2. Change Grade\t5. Delete this course");
            System.out.println("3. Change Start Date");
            System.out.println();
            System.out.println("6. Save\t7. Exit");
            System.out.print("Select an option(1-7): ");

            String choice = scanner.nextLine().trim();

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

    private String getCourseCode() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Course Code(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Course Code must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Course Code = '%s'\n", input);
            return input;
        }
    }

    private Grade getGrade() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Grade(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Grade must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            Grade g;
            try {
                g = Grade.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: input must be one of: DOMESTIC, INTERNATIONAL!");
                continue;
            }

            System.out.printf("Grade = '%s'\n", g);
            return g;
        }
    }

    private LocalDate getStarts() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Starts(x for exit) (e.g, 2025-08-16): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Starts must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            LocalDate date;
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

    private String getLocation() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Location(x for exit): ");
            String input = scanner.nextLine().trim();
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
