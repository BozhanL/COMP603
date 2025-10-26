package com.example.assessment.backend.generic;

import com.example.assessment.backend.derby.HibernateHelper;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class IPersonBackendTest {

    @TempDir
    Path folder;

    IPersonBackend pfb;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException, DatabaseCorruptedException {
        this.pfb = IPersonBackend.of(folder);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HibernateHelper.closeSessionFactory(this.pfb.getDb());
    }

//    Ensure backend can save and load student
    @Test
    void testSetAndGetStudent() {
        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = IStudent.of("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        IStudent id = this.pfb.getStudentById(s.getId());

        assertEquals(s, id);
    }

//    Ensure backend will automatically create manager when there is no manager
    @Test
    void testGetDefaultManager() {
        IManager DEFAULT_MANAGER = IManager.defaultManager();
        IManager id = this.pfb.getManagerById("admin");

        assertEquals(DEFAULT_MANAGER, id);
    }

//    Ensure backend can delete person
    @Test
    void testDeletePerson() {
        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = IStudent.of("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));
        assertTrue(this.pfb.deletePersonById(s.getId()));
    }

//    Ensure backend can modify person
    @Test
    void testModifyPerson() {
        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        IStudent s = IStudent.of("wby5780", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci));
        this.pfb.setPerson(s);

        assertEquals(s, this.pfb.getStudentById(s.getId()));

        IStudent newS = s.withEmail("New email");

        ConstraintViolationException e = assertThrows(ConstraintViolationException.class, () -> {
            this.pfb.setPerson(newS);
        });
        assertThat(
                e.getMessage(),
                containsStringIgnoringCase("The statement was aborted because it would have caused a duplicate key value in a unique or primary key constraint or unique index identified by")
        );
        assertEquals(s, this.pfb.getStudentById(s.getId()));
        assertNotEquals(newS, this.pfb.getStudentById(s.getId()));

        this.pfb.modifyPerson(newS);
        assertEquals(newS, this.pfb.getStudentById(s.getId()));
    }

//    Ensure backend can list person
    @Test
    public void testListPerson() {
        System.out.println("listPerson");
        IPersonBackend instance = this.pfb;
        ImmutableList<IPerson> expResult = ImmutableList.of(IManager.defaultManager());
        ImmutableList<IPerson> result = instance.listPerson();
        assertEquals(expResult, result);
    }

//    Ensure backend can list student
    @Test
    public void testListStudent() {
        System.out.println("listStudent");
        IPersonBackend instance = this.pfb;
        ImmutableList<IStudent> expResult = ImmutableList.of();
        ImmutableList<IStudent> result = instance.listStudent();
        assertEquals(expResult, result);

        IAddress a = IAddress.of("", "561", "Blockhouse Bay Road", "Blockhouse Bay", "Auckland", "Auckland", "NZ", "0600");
        HashMap<String, IStudentCourseInfo> sci = new HashMap<>();
        sci.put("COMP500", IStudentCourseInfo.of("COMP500", Grade.AP, LocalDate.of(2024, 2, 12), "City"));
        sci.put("COMP501", IStudentCourseInfo.of("COMP501", Grade.A, LocalDate.of(2021, 2, 12), "North"));
        expResult = ImmutableList.of(
                IStudent.of("1", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci)),
                IStudent.of("2", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci)),
                IStudent.of("3", "password", "legalFirstName", "legalLastName", LocalDate.now(ZoneId.systemDefault()), Gender.MALE, "email", "phone", a, Residency.INTERNATIONAL, ImmutableMap.copyOf(sci))
        );
        expResult.stream().forEach(this.pfb::setPerson);

        result = instance.listStudent();
        assertEquals(expResult, result);
    }

//    Ensure backend can list manager
    @Test
    public void testListManager() {
        System.out.println("listManager");
        IPersonBackend instance = this.pfb;
        ImmutableList<IManager> expResult = ImmutableList.of(IManager.defaultManager());
        ImmutableList<IManager> result = instance.listManager();
        assertEquals(expResult, result);
    }
}
