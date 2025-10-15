package com.example.assessment.backend.derby;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class HibernateHelperTest {

    @TempDir
    Path baseTempDir;

    private final ArrayList<Path> tempFolders = new ArrayList<>();

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        tempFolders.clear();

        // Generate 5 temporary folders inside baseTempDir
        for (int i = 0; i < 5; i++) {
            Path tempFolder = baseTempDir.resolve(Integer.toString(i));
            tempFolders.add(tempFolder);
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        for (Path p : tempFolders) {
            String db = getDb(p);
            HibernateHelper.closeSessionFactory(db);
        }

        tempFolders.clear();
    }

    /**
     * Test of getSessionFactory method, of class HibernateHelper.
     */
    @Test
    public void testGetSessionFactory() {
        System.out.println("getSessionFactory");
        HashMap<String, SessionFactory> sfs = new HashMap<>();

        for (Path p : tempFolders) {
            String db = getDb(p);
            SessionFactory sf = HibernateHelper.getSessionFactory(db);
            assertNotNull(sf);
            sfs.put(db, sf);
        }

        for (Path p : tempFolders) {
            String db = getDb(p);
            SessionFactory sf1 = HibernateHelper.getSessionFactory(db);
            assertNotNull(sf1);

            SessionFactory sf2 = sfs.get(db);
            assertSame(sf2, sf1);
            assertEquals(sf2, sf1);
        }

        for (Path p : tempFolders) {
            String db = getDb(p);
            SessionFactory sf1 = HibernateHelper.getSessionFactory(db);
            assertNotNull(sf1);

            SessionFactory sf2 = sfs.get(db);
            assertSame(sf2, sf1);
            assertEquals(sf2, sf1);
        }
    }

    private static String getDb(Path p) {
        return String.format("jdbc:derby:%s;create=true", p.toAbsolutePath().normalize());
    }
}
