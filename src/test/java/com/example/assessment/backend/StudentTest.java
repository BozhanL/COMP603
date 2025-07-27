package com.example.assessment.backend;

import com.google.common.collect.ImmutableMap;
import java.time.LocalDate;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest {

    Student s;

    @BeforeEach
    void setUp() {
        Address a = new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, StudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        this.s = new Student("wby5780", "legalFirstName", "legalLastName", LocalDate.now(), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
    }

    @Test
    void testGetter() {
        assertEquals(this.s.getAddress(), new Address("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600"));

        assertEquals(this.s.getCourses(), ImmutableMap.of(
                "COMP500", new StudentCourseInfo("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"),
                "COMP501", new StudentCourseInfo("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North")
        ));

        assertEquals(this.s.getDateOfBirth(), LocalDate.now());

        assertEquals(this.s.getEmail(), "email");

        assertEquals(this.s.getGender(), Gender.MALE);

        assertEquals(this.s.getId(), "wby5780");
        assertEquals(this.s.getPath().toString(), this.s.getId());

        assertEquals(this.s.getLegalFirstName(), "legalFirstName");

        assertEquals(this.s.getLegalLastName(), "legalLastName");

        assertEquals(this.s.getPhone(), "phone");

        assertEquals(this.s.getResidencyStatus(), Residency.INTERNATIONAL);

        assertEquals(this.s.getType(), UserType.STUDENT);
        assertEquals(Student.getTypeStatic(), UserType.STUDENT);
    }

    @Test
    void testWith() {
        Student n;

        n = this.s.withAddress(new Address("", "", "", "", "", "", "NZ", "0600"));
        assertEquals(n.getAddress(), new Address("", "", "", "", "", "", "NZ", "0600"));

        n = this.s.withCourses(ImmutableMap.of());
        assertEquals(n.getCourses(), ImmutableMap.of());

        n = this.s.withDateOfBirth(LocalDate.EPOCH);
        assertEquals(n.getDateOfBirth(), LocalDate.EPOCH);

        n = this.s.withEmail("new email");
        assertEquals(n.getEmail(), "new email");

        n = this.s.withGender(Gender.OTHER);
        assertEquals(n.getGender(), Gender.OTHER);

        n = this.s.withId("abc1234");
        assertEquals(n.getId(), "abc1234");
        assertEquals(n.getPath().toString(), n.getId());

        n = this.s.withLegalFirstName("James");
        assertEquals(n.getLegalFirstName(), "James");

        n = this.s.withLegalLastName("Liang");
        assertEquals(n.getLegalLastName(), "Liang");

        n = this.s.withPhone("+642222222222");
        assertEquals(n.getPhone(), "+642222222222");

        n = this.s.withResidencyStatus(Residency.DOMESTIC);
        assertEquals(n.getResidencyStatus(), Residency.DOMESTIC);
    }

    @Test
    void testToString() {
        String str = String.format(
                "Student(super=Person(id=%s, legalFirstName=%s, legalLastName=%s, dateOfBirth=%s, gender=%s, email=%s, phone=%s, address=%s), residencyStatus=%s, courses=%s)",
                this.s.getId(),
                this.s.getLegalFirstName(),
                this.s.getLegalLastName(),
                this.s.getDateOfBirth(),
                this.s.getGender(),
                this.s.getEmail(),
                this.s.getPhone(),
                this.s.getAddress(),
                this.s.getResidencyStatus(),
                this.s.getCourses()
        );
        assertEquals(this.s.toString(), str);
    }
}
