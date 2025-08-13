package com.example.assessment.cli;

import com.example.assessment.backend.*;
import java.nio.file.Path;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FrontEndTest {

    @TempDir
    Path tempDir;

    @Test
    void testAddCourseFlow() throws Exception {
        // 1. Setup real backend with temp directory
        ICourseBackend backend = new CourseFileBackend(tempDir.toString());

        // 2. Simulate user input
        String simulatedInput = "COMP\n5\n09\nOOP\n15\nDescription\nY\n";
        Scanner testScanner = new Scanner(simulatedInput);

        // 3. Test the workflow
        ManagerDashboard dashboard = new ManagerDashboard(testScanner, backend);
        dashboard.addCourse();

        // 4. Verify through backend
        Course savedCourse = backend.getCourseByCode("COMP509");
        assertNotNull(savedCourse);
        assertEquals("OOP", savedCourse.getName());
        assertEquals(15, savedCourse.getPoints());
        assertEquals("Description", savedCourse.getDescription());

    }
}
