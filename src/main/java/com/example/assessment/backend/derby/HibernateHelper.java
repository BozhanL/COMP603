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
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

// thread safe singleton class for construct Hibernate SessionFactory
@UtilityClass
public class HibernateHelper {

    private final HashMap<String, SessionFactory> sfm = new HashMap<>();
    private final Pattern NORMAL_SHUTDOWN_PATTERN = Pattern.compile("^Database '.*' shutdown\\.$");

//    Get a SessionFactory based on connection string
//    Return same object if already initialized with same string
    public synchronized SessionFactory getSessionFactory(String url) throws IllegalStateException {
//        Check whether already exist
        if (!sfm.containsKey(url)) {
//            Create new configuration
            Configuration configuration = new Configuration();

//            Set connection string
            configuration.setProperty("hibernate.connection.url", url);

//            Create registry
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                    applySettings(configuration.getProperties());

            StandardServiceRegistry registry = builder.build();

//            Include Annotated Class
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

//            Save to Map
            sfm.put(url, sf);
        }

//        Return the SessionFactory from Map
        return sfm.get(url);
    }

//    Close a connection based on connection string
//    Throw null pointer exception if not exist
    public synchronized void closeSessionFactory(String url) throws SQLException {
//        Remove from Map
        SessionFactory sf = sfm.remove(url);

//        Close connection
        sf.close();
//        Close Derby connection
        try {
            DriverManager.getConnection(url + ";shutdown=true");
        } catch (SQLException e) {
            if (!isNormalShutdown(e)) { // normal shutdown
                throw e;
            }
        }
    }

//    Check SQLException, return true if is normal shutdown message
    private boolean isNormalShutdown(SQLException e) {
        return "08006".equals(e.getSQLState()) && NORMAL_SHUTDOWN_PATTERN.matcher(e.getMessage()).matches();
    }
}
