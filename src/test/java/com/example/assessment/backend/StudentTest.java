package com.example.assessment.backend;

import static com.example.assessment.backend.types.classes.Student.getTypeStatic;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.enums.UserType;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableMap;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest {

    IStudent s;

    @BeforeEach
    void setUp() {
        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        this.s = IStudent.of("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
    }

    @Test
    void testGetter() {
        assertEquals(this.s.getAddress(), IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600"));

        assertEquals(this.s.getCourses(), ImmutableMap.of(
                "COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"),
                "COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North")
        ));

        assertEquals(this.s.getDateOfBirth(), LocalDate.now(ZoneId.systemDefault()));

        assertEquals(this.s.getEmail(), "email");

        assertEquals(this.s.getGender(), Gender.MALE);

        assertEquals(this.s.getId(), "wby5780");

        assertEquals(this.s.getLegalFirstName(), "legalFirstName");

        assertEquals(this.s.getLegalLastName(), "legalLastName");

        assertEquals(this.s.getPhone(), "phone");

        assertEquals(this.s.getResidencyStatus(), Residency.INTERNATIONAL);

        assertEquals(this.s.getType(), UserType.STUDENT);
        assertEquals(getTypeStatic(), UserType.STUDENT);
    }

    @Test
    void testGetPath() {
        assertEquals(
                this.s.getPath().toString(),
                String.format(
                        "%s_%s_%s.bin",
                        this.s.getId(),
                        this.s.getLegalFirstName(),
                        this.s.getLegalLastName()
                )
        );
    }

    @Test
    void testWith() {
        IStudent n;

        n = this.s.withAddress(IAddress.of("", "", "", "", "", "", "NZ", "0600"));
        assertEquals(n.getAddress(), IAddress.of("", "", "", "", "", "", "NZ", "0600"));

        n = this.s.withCourses(ImmutableMap.of());
        assertEquals(n.getCourses(), ImmutableMap.of());

        n = this.s.withDateOfBirth(LocalDate.EPOCH);
        assertEquals(n.getDateOfBirth(), LocalDate.EPOCH);

        n = this.s.withEmail("new email");
        assertEquals(n.getEmail(), "new email");

        n = this.s.withGender(Gender.OTHER);
        assertEquals(n.getGender(), Gender.OTHER);

        n = this.s.withLegalFirstName("James");
        assertEquals(n.getLegalFirstName(), "James");

        n = this.s.withLegalLastName("Liang");
        assertEquals(n.getLegalLastName(), "Liang");

        n = this.s.withPassword("abc");
        assertTrue(n.safeCheckPassword("abc"));

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

    @Test
    void testSafeCheckPassword() {
        assertTrue(this.s.safeCheckPassword("password"));
        assertFalse(this.s.safeCheckPassword("password?"));
    }
}
