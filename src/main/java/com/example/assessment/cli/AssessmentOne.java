package com.example.assessment.cli;

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
        Welcome w = new Welcome(scanner);

        IPersonBackend pb;
        ICourseBackend cb;
        while (true) {
            Path p = w.askForDatabaseLocation();
            try {
                if (p == null) {
                    pb = IPersonBackend.of();
                    cb = ICourseBackend.of();
                    break;
                } else {
                    pb = IPersonBackend.of(p);
                    cb = ICourseBackend.of(p);
                    break;
                }
            } catch (IOException | IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }

        IPerson unused = w.login(pb);

        new ManagerDashboard(scanner, cb).displayMenu();
    }
}
