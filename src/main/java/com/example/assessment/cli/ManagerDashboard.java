package com.example.assessment.cli;

import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import java.io.IOException;
import java.util.Scanner;
import lombok.NonNull;

public class ManagerDashboard {

    private final Scanner scanner;
    private final ICourseBackend courseBackend;
    private final CourseInputHandler inputHandler;

    public ManagerDashboard(@NonNull Scanner scanner, @NonNull ICourseBackend courseBackend) {
        this.scanner = scanner;
        this.courseBackend = courseBackend;
        this.inputHandler = new CourseInputHandler(scanner);
    }

    public void displayMenu() {
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" ->
                    addCourse();
                case "2" ->
                    deleteCourse();
                case "3" ->
                    modifyCourse();
                case "4" ->
                    viewCourse();
                case "5" -> {
                    return; // exit to previous menu
                }
                default ->
                    System.out.println("Invalid option. Try again.\n");
            }
        }
    }

    public void addCourse() {
        try {
            // user inputs
            String deptCode = inputHandler.promptDepartmentCode();
            int level = inputHandler.promptCourseLevel();
            int courseNum = inputHandler.promptCourseNumber();
            String name = inputHandler.promptCourseName();
            int points = inputHandler.promptCreditPoints();
            String description = inputHandler.promptDescription();

            // construct course code
            // use backend function instead
            ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

            // confirmation
            boolean confirm = inputHandler.promptConfirmation(
                    "Create new course?\nCode: %s\nName: %s\nPoints: %d\nDescription: %s\nProceed?",
                    courseCode, name, points, description
            );

            // save
            if (confirm) {
                ICourse newCourse = ICourse.of(courseCode, name, points, description);
                courseBackend.setCourse(newCourse);
                // use backend function
                System.out.println("Course " + newCourse.getCode() + " created successfully!");
            } else {
                System.out.println("Course creation cancelled.");
            }

        } catch (IOException e) {
            System.out.println("Error saving course: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid course data: " + e.getMessage());
        }
    }

    private void deleteCourse() {

    }

    private void modifyCourse() {

    }

    private void viewCourse() {

    }

    private void printMainMenu() {
        System.out.println("\n[MANAGER DASHBOARD]");
        System.out.println("1. Add Course");
        System.out.println("2. Delete Course");
        System.out.println("3. Modify Course");
        System.out.println("4. View Course Details");
        System.out.println("5. Exit");
        System.out.print("Select an option: ");
    }
}
