package com.example.assessment.backend.derby;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private ArrayList<Path> tempFolders;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        tempFolders = new ArrayList<>();

        // Generate 5 temporary folders inside baseTempDir
        for (int i = 0; i < 5; i++) {
            Path tempFolder = Files.createTempDirectory(baseTempDir, "tempFolder" + i + "_");
            tempFolders.add(tempFolder);
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getSessionFactory method, of class HibernateHelper.
     */
    @Test
    public void testGetSessionFactory() {
        System.out.println("getSessionFactory");
        HashMap<String, SessionFactory> sfs = new HashMap<>();

        for (Path p : baseTempDir) {
            String db = String.format("jdbc:derby:%s;create=true", p.toAbsolutePath().normalize());
            SessionFactory sf = HibernateHelper.getSessionFactory(db);
            assertNotNull(sf);
            sfs.put(db, sf);
        }

        for (Path p : baseTempDir) {
            String db = String.format("jdbc:derby:%s;create=true", p.toAbsolutePath().normalize());
            SessionFactory sf1 = HibernateHelper.getSessionFactory(db);
            assertNotNull(sf1);

            SessionFactory sf2 = sfs.get(db);
            assertSame(sf2, sf1);
            assertEquals(sf2, sf1);
        }

        for (Path p : baseTempDir) {
            String db = String.format("jdbc:derby:%s;create=true", p.toAbsolutePath().normalize());
            SessionFactory sf1 = HibernateHelper.getSessionFactory(db);
            assertNotNull(sf1);

            SessionFactory sf2 = sfs.get(db);
            assertSame(sf2, sf1);
            assertEquals(sf2, sf1);
        }
    }
}
