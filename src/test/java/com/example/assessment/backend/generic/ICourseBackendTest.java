package com.example.assessment.backend.generic;

import com.example.assessment.backend.derby.HibernateHelper;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ICourseBackendTest {

    @TempDir
    Path folder;

    ICourseBackend cb;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException, DatabaseCorruptedException {
        this.cb = ICourseBackend.of(folder);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        HibernateHelper.closeSessionFactory(this.cb.getDb());
    }

    @Test
    public void testSetAndGetCourseByCode() throws Exception {
        ICourse c1 = ICourse.of("COMP", 5, 9, "name", 15, "description");
        ICourse c2 = ICourse.of("COMP", 9, 9, "name", 15, "description");
        this.cb.setCourse(c1);
        this.cb.setCourse(c2);

        assertEquals(c1, this.cb.getCourseByCode(c1.getCode()));
        assertEquals(c2, this.cb.getCourseByCode(c2.getCode()));
    }

    @Test
    public void testDeleteCourseByCode_ICourseCode() throws ParseException {
        System.out.println("deleteCourseByCode");

        ICourse c1 = ICourse.of("COMP", 5, 9, "name", 15, "description");
        ICourse c2 = ICourse.of("COMP", 9, 9, "name", 15, "description");
        this.cb.setCourse(c1);
        this.cb.setCourse(c2);

        assertEquals(true, this.cb.deleteCourseByCode(c1.getCode()));
        assertEquals(false, this.cb.deleteCourseByCode(c1.getCode()));

        assertEquals(c2, this.cb.getCourseByCode(c2.getCode()));
    }

    @Test
    public void testDeleteCourseByCode_String() throws ParseException {
        System.out.println("deleteCourseByCode");

        ICourse c1 = ICourse.of("COMP", 5, 9, "name", 15, "description");
        ICourse c2 = ICourse.of("COMP", 9, 9, "name", 15, "description");
        this.cb.setCourse(c1);
        this.cb.setCourse(c2);

        assertEquals(true, this.cb.deleteCourseByCode(c1.getCode().toString()));
        assertEquals(false, this.cb.deleteCourseByCode(c1.getCode().toString()));

        assertEquals(c2, this.cb.getCourseByCode(c2.getCode()));
    }

    @Test
    public void testModifyCourse() {
        System.out.println("modifyCourse");

        ICourse old = ICourse.of("COMP", 5, 9, "name", 15, "description");
        ICourse newC = ICourse.of(old.getCode(), "new", 150, "new");
        this.cb.setCourse(old);

        assertEquals(old, this.cb.getCourseByCode(old.getCode()));

        this.cb.modifyCourse(newC);
        assertNotEquals(old, this.cb.getCourseByCode(old.getCode()));
        assertEquals(newC, this.cb.getCourseByCode(old.getCode()));
    }

    @Test
    public void testListCourse() {
        System.out.println("listCourse");

        ImmutableList<ICourse> expResult = ImmutableList.of();
        ImmutableList<ICourse> result = this.cb.listCourse();
        assertEquals(expResult, result);

        expResult = ImmutableList.of(
                ICourse.of("COMP", 5, 1, "name", 15, "description"),
                ICourse.of("COMP", 5, 2, "name", 15, "description"),
                ICourse.of("COMP", 5, 3, "name", 15, "description")
        );
        expResult.stream().forEach(this.cb::setCourse);

        result = this.cb.listCourse();
        assertEquals(expResult, result);
    }
}
