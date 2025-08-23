package com.example.assessment.cli;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
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
        Welcome w = new Welcome(scanner);

        ICombinedBackend database = w.askForDatabase();

        IPerson p = w.login(database);

        IMainDashboard d = p.getDashboard(scanner, database);
        d.displayMenu();
    }
}
