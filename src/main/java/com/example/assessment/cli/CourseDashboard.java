package com.example.assessment.cli;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CheckReturnValue
@AllArgsConstructor
public final class CourseDashboard implements IDashboard {

    @NonNull
    private final Scanner scanner;
    @NonNull
    private final ICourseBackend courseBackend;
    @NonNull
    private final CourseInputHandler inputHandler;

    public CourseDashboard(@NonNull Scanner scanner, @NonNull ICourseBackend courseBackend) {
        this(scanner, courseBackend, new CourseInputHandler(scanner));
    }

    private static void printMainMenu() {
        System.out.println("[COURSE DASHBOARD]");
        System.out.println("1. Add Course\t4. List Course");
        System.out.println("2. Get Course\t5. Delete Course");
        System.out.println("3. Modify Course");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    @Override
    public void displayMenu() {
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" ->
                        addCourse();
                    case "2" ->
                        getCourse();
                    case "3" ->
                        modifyCourse();
                    case "4" ->
                        listCourse();
                    case "5" ->
                        deleteCourse();
                    case "6" -> {
                        return; // exit to previous menu
                    }
                    default ->
                        System.out.println("Invalid option. Try again.\n");
                }
            } catch (StopOperationException e) {
                System.out.println("Operation canceled!");
            }
        }
    }

    public void addCourse() throws StopOperationException {
        // user inputs
        String deptCode = inputHandler.getDepartmentCode();
        int level = inputHandler.getCourseLevel();
        int courseNum = inputHandler.getCourseNumber();
        String name = inputHandler.getCourseName();
        int points = inputHandler.getCreditPoints();
        String description = inputHandler.getDescription();

        // construct course code
        // use backend function instead
        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

        // confirmation
        inputHandler.promptConfirmation(
                "Create new course?\nCode: %s\nName: %s\nPoints: %d\nDescription: %s\nProceed?",
                courseCode, name, points, description
        );

        // save
        ICourse newCourse = ICourse.of(courseCode, name, points, description);
        try {
            courseBackend.setCourse(newCourse);
            // use backend function
            System.out.println("Course " + newCourse.getCode() + " created successfully!");
        } catch (FileAlreadyExistsException e) {
            System.out.println("Error: Course already exists");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getCourse() throws StopOperationException {
        String deptCode = inputHandler.getDepartmentCode();
        int level = inputHandler.getCourseLevel();
        int courseNum = inputHandler.getCourseNumber();
        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

        ICourse c;
        try {
            c = courseBackend.getCourseByCode(courseCode.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Error: course not found");
            return;
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        } catch (DatabaseCorruptedException ex) {
            System.out.println("Error: data corrupted, try another course");
            return;
        }

        System.out.println(c.prettyToString());
    }

    private void modifyCourse() throws StopOperationException {
        String deptCode = inputHandler.getDepartmentCode();
        int level = inputHandler.getCourseLevel();
        int courseNum = inputHandler.getCourseNumber();
        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

        ICourse c;
        try {
            c = courseBackend.getCourseByCode(courseCode.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Error: course not found");
            return;
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        } catch (DatabaseCorruptedException ex) {
            System.out.println("Error: data corrupted, try another course");
            return;
        }

        c = inputHandler.getModifiedCourse(c);
        try {
            courseBackend.modifyCourse(c);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void listCourse() {
        ImmutableList<ICourse> cs;
        try {
            cs = courseBackend.listCourse();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        } catch (DatabaseCorruptedException ex) {
            System.out.println("Error: data corrupted");
            return;
        }

        for (ICourse c : cs) {
            System.out.println(c.prettyToString());
            System.out.println("------------------------------------------------");
        }
    }

    private void deleteCourse() throws StopOperationException {
        String deptCode = inputHandler.getDepartmentCode();
        int level = inputHandler.getCourseLevel();
        int courseNum = inputHandler.getCourseNumber();
        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

        try {
            courseBackend.deleteCourseByCode(courseCode.toString());
        } catch (IOException ex) {
            System.out.println("Error deleteing course: " + ex.getMessage());
        }
    }
}
