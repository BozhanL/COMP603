package com.example.assessment.cli;

import com.example.assessment.backend.Course;
import com.example.assessment.backend.CourseCode;
import com.example.assessment.backend.ICourseBackend;
import java.io.IOException;
import java.util.Scanner;

public class ManagerDashboard {

    private final Scanner scanner;
    private final ICourseBackend courseBackend;
    private final CourseInputHandler inputHandler;

    public ManagerDashboard(Scanner scanner, ICourseBackend courseBackend) {
        this.scanner = scanner;
        this.courseBackend = courseBackend;
        this.inputHandler = new CourseInputHandler(scanner);
    }

    public void displayMenu() {
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addCourse();
                    break;
                case "2":
                    deleteCourse();
                    break;
                case "3":
                    modifyCourse();
                    break;
                case "4":
                    viewCourse();
                    break;
                case "5":
                    return; // exit to previous menu
                default:
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
            CourseCode courseCode = new CourseCode(deptCode, level, courseNum);

            // confirmation
            boolean confirm = inputHandler.promptConfirmation(
                    "Create new course?\nCode: %s\nName: %s\nPoints: %d\nDescription: %s\nProceed?",
                    courseCode, name, points, description
            );

            // save
            if (confirm) {
                Course newCourse = new Course(courseCode, name, points, description);
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
