package com.example.assessment.cli;

import com.example.assessment.backend.Course;
import com.example.assessment.backend.CourseFileBackend;
import com.example.assessment.backend.ICourseBackend;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FrontEndTest {

    @TempDir
    Path tempDir;

    @Test
    void testAddCourseFlow() throws Exception {
        // 1. Setup real backend with temp directory
        ICourseBackend backend = new CourseFileBackend(tempDir);

        // 2. Simulate user input
        String simulatedInput = "COMP\n5\n09\nOOP\n15\nDescription\nY\n";
        Scanner testScanner = new Scanner(simulatedInput);

        // 3. Capture System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputBuffer));

        try {
            // 4. Test the workflow
            ManagerDashboard dashboard = new ManagerDashboard(testScanner, backend);
            dashboard.addCourse();

            // 5. Verify console output
            String consoleOutput = outputBuffer.toString();
            assertTrue(consoleOutput.contains("COMP509")); // Verify course code appears
            assertTrue(consoleOutput.contains("successfully")); // Verify success message
        } finally {
            // 6. Restore System.out
            System.setOut(originalOut);
        }

        // 7. Verify backend state
        Course savedCourse = backend.getCourseByCode("COMP509");
        assertNotNull(savedCourse);
        assertEquals("OOP", savedCourse.getName());
    }
}
