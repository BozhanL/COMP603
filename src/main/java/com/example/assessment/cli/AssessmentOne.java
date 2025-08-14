package com.example.assessment.cli;

import com.example.assessment.backend.types.CourseFileBackend;
import com.example.assessment.backend.types.ICourseBackend;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.Person;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssessmentOne {

    public static void main(String[] args) {

        @Cleanup
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset());

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
