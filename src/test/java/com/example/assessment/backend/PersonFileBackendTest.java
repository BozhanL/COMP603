package com.example.assessment.backend;

import com.example.assessment.backend.file.PersonFileBackend;
import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.classes.Address;
import com.example.assessment.backend.types.classes.Gender;
import com.example.assessment.backend.types.classes.Grade;
import com.example.assessment.backend.types.classes.Manager;
import com.example.assessment.backend.types.classes.Residency;
import com.example.assessment.backend.types.classes.Student;
import com.example.assessment.backend.types.classes.StudentCourseInfo;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PersonFileBackendTest {

    @TempDir
    Path folder;

    IPersonBackend pfb;

    @BeforeEach
    void setUp() throws IOException {
        this.pfb = new PersonFileBackend(folder);
    }

    @Test
    void testSetAndGetStudent() throws IOException, DatabaseCorruptedException {
        IAddress a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = new Student("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        IStudent id = this.pfb.getStudentById(s.getId());
        IStudent name = this.pfb.getStudentByName(s.getLegalFirstName(), s.getLegalLastName());

        assertEquals(s, id);
        assertEquals(s, name);
        assertEquals(id, name);
    }

    @Test
    void testGetDefaultManager() throws IOException, DatabaseCorruptedException {
        IManager DEFAULT_MANAGER = new Manager(
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
        IManager id = this.pfb.getManagerById("admin");
        IManager name = this.pfb.getManagerByName("admin", "admin");

        assertEquals(DEFAULT_MANAGER, id);
        assertEquals(DEFAULT_MANAGER, name);
        assertEquals(id, name);
    }

    @Test
    void testDeletePerson() throws IOException, DatabaseCorruptedException {
        IAddress a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = new Student("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));
        assertTrue(this.pfb.deletePersonById(s.getId()));

        IOException exception = assertThrows(IOException.class,
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
        IAddress a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = new Student("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));

        FileAlreadyExistsException exception = assertThrows(FileAlreadyExistsException.class,
                () -> {
                    this.pfb.setPerson(s);
                }
        );

        assertThat(exception.getMessage(), containsString(s.getPath().toString()));
    }
}
