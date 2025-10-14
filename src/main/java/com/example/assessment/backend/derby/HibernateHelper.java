package com.example.assessment.backend.derby;

import com.example.assessment.backend.types.entity.AddressEntity;
import com.example.assessment.backend.types.entity.CourseCodeEntity;
import com.example.assessment.backend.types.entity.CourseEntity;
import com.example.assessment.backend.types.entity.ManagerEntity;
import com.example.assessment.backend.types.entity.PersonEntity;
import com.example.assessment.backend.types.entity.StudentCourseInfoEntity;
import com.example.assessment.backend.types.entity.StudentEntity;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateHelper {

    static final HashMap<String, SessionFactory> sfm = new HashMap<>();

    public static synchronized SessionFactory getSessionFactory(String db) {
        if (!sfm.containsKey(db)) {
            Configuration configuration = new Configuration();

            configuration.setProperty("hibernate.connection.url", db);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                    applySettings(configuration.getProperties());

            StandardServiceRegistry registry = builder.build();

            SessionFactory sf = new MetadataSources(registry)
                    .addAnnotatedClass(AddressEntity.class)
                    .addAnnotatedClass(CourseEntity.class)
                    .addAnnotatedClass(CourseCodeEntity.class)
                    .addAnnotatedClass(ManagerEntity.class)
                    .addAnnotatedClass(PersonEntity.class)
                    .addAnnotatedClass(StudentEntity.class)
                    .addAnnotatedClass(StudentCourseInfoEntity.class)
                    .buildMetadata()
                    .buildSessionFactory();

            sfm.put(db, sf);
        }

        return sfm.get(db);
    }

    public static synchronized void closeSessionFactory(String db) throws SQLException {
        SessionFactory sf = sfm.remove(db);

        sf.close();
        try {
            DriverManager.getConnection(db + ";shutdown=true");
        } catch (SQLException e) {
            if (!"08006".equals(e.getSQLState())) { // normal shutdown
                throw e;
            }
        }
    }
}
