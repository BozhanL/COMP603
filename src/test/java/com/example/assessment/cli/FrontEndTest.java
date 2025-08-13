package com.example.assessment.cli;

import com.example.assessment.backend.*;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.mockito.Mockito.*;

public class FrontEndTest {

    @TempDir
    Path tempDir;

    @Test
    void testAddCourseFlow() throws Exception {
        // 1. Setup mock backend and test inputs
        ICourseBackend mockBackend = mock(ICourseBackend.class);
        String simulatedInput = "CS\n5\n01\nOOP\n100\nDescription\nY\n";
        Scanner testScanner = new Scanner(simulatedInput);

        // 2. Create course we expect to be saved
        Course expectedCourse = new Course(
                "CS501", // Use String to match implementation
                "OOP",
                100,
                "Description"
        );

        // 3. Create and test dashboard
        ManagerDashboard dashboard = new ManagerDashboard(testScanner, mockBackend);
        dashboard.addCourse();

        // 4. Verify the exact course was saved
        verify(mockBackend).setCourse(argThat(actualCourse
                -> actualCourse.getCode().toString().equals("CS501")
                && actualCourse.getName().equals("OOP")
                && actualCourse.getPoints() == 100
                && actualCourse.getDescription().equals("Description")
        ));
    }
}
