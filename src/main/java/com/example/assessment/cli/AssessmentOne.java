package com.example.assessment.cli;

import com.example.assessment.backend.file.CourseFileBackend;
import com.example.assessment.backend.file.PersonFileBackend;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Scanner;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssessmentOne {

    public static void main(String[] args) {

        @Cleanup
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset());

        Welcome.showAsciiArt();

        IPersonBackend pb;
        ICourseBackend cb;
        while (true) {
            Path p = Welcome.askForDatabaseLocation(scanner);
            try {
                if (p == null) {
                    pb = new PersonFileBackend();
                    cb = new CourseFileBackend();
                    break;
                } else {
                    pb = new PersonFileBackend(p);
                    cb = new CourseFileBackend(p);
                    break;
                }
            } catch (IOException | IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        IPerson unused = Welcome.login(scanner, pb);

        new ManagerDashboard(scanner, cb).displayMenu();
    }
}
