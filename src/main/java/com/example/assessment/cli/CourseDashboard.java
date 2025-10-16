package com.example.assessment.cli;

import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This is the dashboard to manage course in the system
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

    @Override
    public void displayMenu() {
        while (true) {
//            Print options
            printMainMenu();
//            Get the input
            String choice = scanner.nextLine().trim();

//            Execute
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
                        System.out.println("Error: Invalid option. Try again.");
                }
            } catch (StopOperationException e) {
                System.out.println("Operation canceled!");
            }
        }
    }

//    Add a new course to database
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

        courseBackend.setCourse(newCourse);
        // use backend function
        System.out.println("Course " + newCourse.getCode() + " created successfully!");
    }

//    Get a course by course code
    private void getCourse() throws StopOperationException {
//        Get the course code from user
        String deptCode = inputHandler.getDepartmentCode();
        int level = inputHandler.getCourseLevel();
        int courseNum = inputHandler.getCourseNumber();

//        Construct ICourseCode
        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

        ICourse c;
        try {
            //            Get the course from backend
            c = courseBackend.getCourseByCode(courseCode.toString());
        } catch (ParseException ex) {
            Logger.getLogger(CourseDashboard.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

//        print the course
        System.out.println(c.prettyToString());
    }

//    Modify and store it to backend
    private void modifyCourse() throws StopOperationException {
//        Get the course code from user
        String deptCode = inputHandler.getDepartmentCode();
        int level = inputHandler.getCourseLevel();
        int courseNum = inputHandler.getCourseNumber();

//        Construct ICourseCode
        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

        ICourse c;
        try {
            //            Get the course from backend
            c = courseBackend.getCourseByCode(courseCode.toString());
        } catch (ParseException ex) {
            Logger.getLogger(CourseDashboard.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

//        Modify the course
        c = inputHandler.getModifiedCourse(c);
//            Store it to the database
        courseBackend.modifyCourse(c);
    }

//    List all course from database
    private void listCourse() {
        ImmutableList<ICourse> cs;
//            List all course
        cs = courseBackend.listCourse();

//        print course one by one
        for (ICourse c : cs) {
            System.out.println(c.prettyToString());
            System.out.println("------------------------------------------------");
        }
    }

//    Delete a course from database
    private void deleteCourse() throws StopOperationException {
//        Get the course code from user
        String deptCode = inputHandler.getDepartmentCode();
        int level = inputHandler.getCourseLevel();
        int courseNum = inputHandler.getCourseNumber();

//        Construct ICourseCode
        ICourseCode courseCode = ICourseCode.of(deptCode, level, courseNum);

        try {
            //            Delete the course
            courseBackend.deleteCourseByCode(courseCode.toString());
        } catch (ParseException ex) {
            Logger.getLogger(CourseDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void printMainMenu() {
        System.out.println("[COURSE DASHBOARD]");
        System.out.println("1. Add Course\t\t4. List Course");
        System.out.println("2. Get Course\t\t5. Delete Course");
        System.out.println("3. Modify Course");
        System.out.println();
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }
}
