package com.example.assessment.cli;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import java.nio.charset.Charset;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssessmentOne {

    public static void main(String[] args) {
//        Scanner for stdin should never be closed.
        Scanner scanner = new Scanner(System.in, Charset.defaultCharset());

        while (true) {
            try {
//                Show hello message
                Welcome.showAsciiArt();

                Welcome w = new Welcome(scanner);

//                Get database
                ICombinedBackend database = w.askForDatabase();
//                Login
                IPerson p = w.login(database);

//                Create dashboard
                IMainDashboard d = p.getDashboard(scanner, database);
//                Show the menu
                d.displayMenu();

//                If displayMenu return normally without exception, exit the program
                break;
            } catch (Exception ex) {
//                Received unknown exception, print error message and restart it.
                System.out.printf("Unknown error: %s\n", ex.getMessage());
                System.out.println("Restarting the program!");
                System.out.println();
            }
        }

    }
}
