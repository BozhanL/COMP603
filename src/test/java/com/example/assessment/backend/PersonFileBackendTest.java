package com.example.assessment.backend;

import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PersonFileBackendTest {

    @TempDir
    Path folder;

    PersonFileBackend pfb;

    @BeforeEach
    void setUp() throws IOException {
        this.pfb = new PersonFileBackend(folder);
    }

    @Test
    void testSetAndGetStudent() throws IOException, DatabaseCorruptedException {
        Address a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, StudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        Student s = new Student("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        Student id = this.pfb.getStudentById(s.getId());
        Student name = this.pfb.getStudentByName(s.getLegalFirstName(), s.getLegalLastName());

        assertEquals(s, id);
        assertEquals(s, name);
        assertEquals(id, name);
    }

    @Test
    void testGetDefaultManager() throws IOException, DatabaseCorruptedException {
        Manager DEFAULT_MANAGER = new Manager(
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
        Manager id = this.pfb.getManagerById("admin");
        Manager name = this.pfb.getManagerByName("admin", "admin");

        assertEquals(DEFAULT_MANAGER, id);
        assertEquals(DEFAULT_MANAGER, name);
        assertEquals(id, name);
    }

    @Test
    void testDeletePerson() throws IOException, DatabaseCorruptedException {
        Address a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, StudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        Student s = new Student("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));
        this.pfb.deletePersonById(s.getId());

        Exception exception;
        exception = assertThrows(IOException.class,
                () -> {
                    this.pfb.getStudentById(s.getId());
                }
        );
        assertEquals(String.format("No file found matching the name: %s", s.getId()), exception.getMessage());

        exception = assertThrows(IOException.class,
                () -> {
                    this.pfb.getStudentByName(s.getLegalFirstName(), s.getLegalLastName());
                }
        );
        assertEquals(String.format("No file found matching the name: %s_%s", s.getLegalFirstName(), s.getLegalLastName()), exception.getMessage());
    }

    @Test
    void testSetExistPerson() throws IOException, DatabaseCorruptedException {
        Address a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, StudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        Student s = new Student("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));

        Exception exception;
        exception = assertThrows(FileAlreadyExistsException.class,
                () -> {
                    this.pfb.setPerson(s);
                }
        );
        assertEquals(this.pfb.db.resolve(s.getPath()).toString(), exception.getMessage());
    }
}
