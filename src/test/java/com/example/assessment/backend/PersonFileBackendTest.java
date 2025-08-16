package com.example.assessment.backend;

import com.example.assessment.backend.file.PersonFileBackend;
import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableMap;
import java.io.FileNotFoundException;
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

    PersonFileBackend pfb;

    @BeforeEach
    void setUp() throws IOException {
        this.pfb = PersonFileBackend.of(folder);
    }

    @Test
    void testSetAndGetStudent() throws IOException, DatabaseCorruptedException {
        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = IStudent.of("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        IStudent id = this.pfb.getStudentById(s.getId());

        assertEquals(s, id);
    }

    @Test
    void testGetDefaultManager() throws IOException, DatabaseCorruptedException {
        IManager DEFAULT_MANAGER = IManager.defaultManager();
        IManager id = this.pfb.getManagerById("admin");

        assertEquals(DEFAULT_MANAGER, id);
    }

    @Test
    void testDeletePerson() throws IOException, DatabaseCorruptedException {
        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = IStudent.of("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));
        assertTrue(this.pfb.deletePersonById(s.getId()));

        FileNotFoundException exception = assertThrows(FileNotFoundException.class,
                () -> {
                    this.pfb.getStudentById(s.getId());
                }
        );
        assertThat(exception.getMessage(), containsString(PersonFileBackend.pathFromPerson(s).toString()));
    }

    @Test
    void testSetExistPerson() throws IOException, DatabaseCorruptedException {
        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = IStudent.of("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));

        FileAlreadyExistsException exception = assertThrows(FileAlreadyExistsException.class,
                () -> {
                    this.pfb.setPerson(s);
                }
        );

        assertThat(exception.getMessage(), containsString(PersonFileBackend.pathFromPerson(s).toString()));
    }
}
