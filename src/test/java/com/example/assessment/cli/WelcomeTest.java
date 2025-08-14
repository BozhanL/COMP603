package com.example.assessment.cli;

import com.example.assessment.backend.file.PersonFileBackend;
import com.example.assessment.backend.types.classes.Address;
import com.example.assessment.backend.types.classes.Gender;
import com.example.assessment.backend.types.classes.Manager;
import com.example.assessment.backend.types.interfaces.IPerson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class WelcomeTest {

    @TempDir
    Path folder;

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private final PipedOutputStream out = new PipedOutputStream();
    private final PipedInputStream in = new PipedInputStream();

    @BeforeEach
    void setUp() throws IOException {
        System.setOut(new PrintStream(new PipedOutputStream(this.in)));
        System.setIn(new PipedInputStream(this.out));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testAskForDatabaseLocation() {
        Scanner sc = new Scanner(this.in, Charset.defaultCharset());
        PrintStream ps = new PrintStream(this.out);
        Welcome w = new Welcome(new Scanner(System.in, Charset.defaultCharset()));

        ps.println(this.folder);

        Path p = w.askForDatabaseLocation();
        assertNotNull(p);
        assertEquals(this.folder, p);

        assertEquals(String.format(
                "Enter database path (press Enter for default): Database = %s",
                this.folder
        ), sc.nextLine());
    }

    @Test
    void testLogin() throws IOException {
        Scanner sc = new Scanner(this.in, Charset.defaultCharset());
        PrintStream ps = new PrintStream(this.out);

        Welcome w = new Welcome(new Scanner(System.in, Charset.defaultCharset()));

        ps.println("admin");
        ps.println("admin");

        IPerson expected = new Manager(
                "admin",
                "admin",
                "admin",
                "admin",
                LocalDate.MIN,
                Gender.OTHER,
                "",
                "",
                new Address("", "", "", "", "", "", "", "")
        );
        IPerson p = w.login(new PersonFileBackend(this.folder));
        assertNotNull(p);
        assertEquals(expected, p);

        assertEquals("Enter User ID: Enter Password: Login Successful", sc.nextLine());
    }
}
