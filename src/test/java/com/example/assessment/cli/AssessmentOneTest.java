package com.example.assessment.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class AssessmentOneTest {

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
    void testMain() throws InterruptedException {
        Scanner sc = new Scanner(this.in, Charset.defaultCharset());
        PrintStream ps = new PrintStream(this.out);

        Thread thread = new Thread(() -> {
            AssessmentOne.main(new String[0]);
        });
        thread.start();

        assertEquals("               _                           ", sc.nextLine());
        assertEquals("              | |                          ", sc.nextLine());
        assertEquals(" __      _____| | ___ ___  _ __ ___   ___  ", sc.nextLine());
        assertEquals(" \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ ", sc.nextLine());
        assertEquals("  \\ V  V /  __/ | (_| (_) | | | | | |  __/ ", sc.nextLine());
        assertEquals("   \\_/\\_/_\\___|_|\\___\\___/|_| |_| |_|\\___| ", sc.nextLine());
        assertEquals("  / ____|_   _|  \\/  |/ ____|              ", sc.nextLine());
        assertEquals(" | (___   | | | \\  / | (___                ", sc.nextLine());
        assertEquals("  \\___ \\  | | | |\\/| |\\___ \\               ", sc.nextLine());
        assertEquals("  ____) |_| |_| |  | |____) |              ", sc.nextLine());
        assertEquals(" |_____/|_____|_|  |_|_____/               ", sc.nextLine());
        assertEquals("                                           ", sc.nextLine());

        ps.println(this.folder);
        assertEquals(String.format(
                "Enter database path (press Enter for default): Database = %s",
                this.folder
        ), sc.nextLine());

        ps.println("admin");
        ps.println("admin");
        assertEquals("Enter User ID: Enter Password: Login Successful", sc.nextLine());

        // thread.join();
    }
}
