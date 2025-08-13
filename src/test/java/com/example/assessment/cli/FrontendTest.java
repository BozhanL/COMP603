package com.example.assessment.cli;

import com.example.assessment.backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FrontendTest {

    @TempDir
    Path tempDir;
    private ICourseBackend mockCourseBackend;
    private IPersonBackend mockPersonBackend;
    private Scanner scanner;
    private final String testDbPath = "test_db";

    @BeforeEach
    void setUp() throws IOException {
        mockCourseBackend = mock(ICourseBackend.class);
        mockPersonBackend = mock(IPersonBackend.class);
        when(mockPersonBackend.getPersonById(anyString())).thenReturn(
                new Manager("admin", "admin", "Admin", "User",
                        LocalDate.now(), Gender.OTHER, "admin@school.edu",
                        "1234567890", new Address("", "", "", "", "", "", "", ""))
        );
    }

    // --- Welcome Screen Tests --- //
    @Test
    void testWelcomeDatabasePrompt_DefaultPath() {
        String input = "\n"; // Simulate pressing Enter
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        IPersonBackend pb = Welcome.askForDatabase(new Scanner(System.in));
        assertNotNull(pb);
    }

    @Test
    void testLogin_ValidCredentials() {
        String input = "admin\nadmin\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Person person = Welcome.login(new Scanner(System.in), mockPersonBackend);
        assertEquals("admin", person.getId());
    }

    // --- Course Input Handler Tests --- //
    @Test
    void testPromptDepartmentCode_ValidInput() {
        String input = "CS\n";
        CourseInputHandler handler = new CourseInputHandler(new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertEquals("CS", handler.promptDepartmentCode());
    }

    @Test
    void testPromptCourseLevel_InvalidThenValid() {
        String input = "X\n5\n";
        CourseInputHandler handler = new CourseInputHandler(new Scanner(new ByteArrayInputStream(input.getBytes())));
        assertEquals(5, handler.promptCourseLevel());
    }

    // --- Manager Dashboard Tests --- //
    @Test
    void testAddCourse_HappyPath() throws Exception {
        // Mock user input sequence
        String input = "CS\n5\n01\nOOP\n100\nDescription\nY\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ManagerDashboard dashboard = new ManagerDashboard(new Scanner(System.in), mockCourseBackend);
        dashboard.addCourse();

        verify(mockCourseBackend).setCourse(any(Course.class));
    }

    @Test
    void testAddCourse_Cancellation() throws Exception {
        String input = "CS\n5\n01\nOOP\n100\nDescription\nN\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ManagerDashboard dashboard = new ManagerDashboard(new Scanner(System.in), mockCourseBackend);
        dashboard.addCourse();

        verify(mockCourseBackend, never()).setCourse(any());
    }
}
