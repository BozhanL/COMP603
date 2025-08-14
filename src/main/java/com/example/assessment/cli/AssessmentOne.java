package com.example.assessment.cli;

import com.example.assessment.backend.CourseFileBackend;
import com.example.assessment.backend.ICourseBackend;
import com.example.assessment.backend.IPersonBackend;
import com.example.assessment.backend.Person;
import java.io.IOException;
import java.util.Scanner;
import lombok.Cleanup;

public class AssessmentOne {

    public static void main(String[] args) {

        @Cleanup
        Scanner scanner = new Scanner(System.in);

        Welcome.showAsciiArt();

        IPersonBackend pb = Welcome.askForDatabase(scanner);

        try {
            Person p = Welcome.login(scanner, pb);

            ICourseBackend cb = new CourseFileBackend();
            new ManagerDashboard(scanner, cb).displayMenu();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
