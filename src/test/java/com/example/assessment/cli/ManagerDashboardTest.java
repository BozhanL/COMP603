package com.example.assessment.cli;

import com.example.assessment.backend.file.CourseFileBackend;
import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.classes.Course;
import com.example.assessment.backend.types.interfaces.ICourse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ManagerDashboardTest {

    @TempDir
    Path tempDir;

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
    void testAddCourseFlow() throws Exception {
        Scanner sc = new Scanner(this.in, Charset.defaultCharset());
        PrintStream ps = new PrintStream(this.out);

        // Setup real backend with temp directory
        ICourseBackend backend = new CourseFileBackend(tempDir);

        // Test the workflow
        Thread thread = new Thread(() -> {
            ManagerDashboard dashboard
                    = new ManagerDashboard(new Scanner(System.in, Charset.defaultCharset()), backend);
            dashboard.addCourse();
        });
        thread.start();

        ps.println("COMP\n5\n09\nOOP\n15\nDescription\nY");
        thread.join();
        String s = sc.tokens().collect(Collectors.joining(" "));
        assertThat(s, allOf(
                containsString("Create new course?"),
                containsString("COMP509"),
                containsString("OOP"),
                containsString("Points: 15"),
                containsString("Description")
        ));

        // Verify backend state
        ICourse expected = new Course("COMP509", "OOP", 15, "Description");
        ICourse savedCourse = backend.getCourseByCode("COMP509");
        assertNotNull(savedCourse);
        assertEquals(expected, savedCourse);
    }
}
