package com.example.assessment.backend;

import com.google.common.collect.ImmutableList;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest {

    Student s;

    @BeforeEach
    void setUp() {
        Address a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");

        this.s = new Student("wby5780", "legalFirstName", "legalLastName", LocalDate.now(), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableList.of());
    }

    @Test
    void testGetId() {
        assertEquals(this.s.getId(), "wby5780");
    }

    @Test
    void testGetPath() {
        String str = this.s.getPath().toString();
        assertEquals(str, "wby5780");
        assertEquals(str, this.s.getId());
    }

    @Test
    void testGetType() {
        assertEquals(s.getType(), UserType.STUDENT);
        assertEquals(Student.getTypeStatic(), UserType.STUDENT);
    }
}
