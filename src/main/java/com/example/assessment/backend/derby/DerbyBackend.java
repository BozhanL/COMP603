package com.example.assessment.backend.derby;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IBackend;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.spi.ServiceException;

// Base abstract class for Derby backend
@ToString
@CheckReturnValue
public abstract class DerbyBackend implements IBackend {

    protected static final Path EMPTY = Path.of("");

    @Getter
    protected final String db;
    @NonNull
    protected final SessionFactory sf;

//    Copied from: https://stackoverflow.com/a/5937917
//    Check whether the folder is empty
    private static boolean isDirEmpty(@NonNull Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }

//    Constructor to initialized database connection
    protected DerbyBackend(@NonNull Path p) throws IOException, DatabaseCorruptedException {
//        Check path
        if (EMPTY.equals(p)) {
            throw new IllegalArgumentException("Path must not be empty!");
        }

//        Delete folder if it is empty
        if (Files.isDirectory(p) && isDirEmpty(p)) {
            Files.deleteIfExists(p);
        }

//        Create database connection string
        this.db = String.format("jdbc:derby:%s;create=true", p.toAbsolutePath().normalize());

//        Create SessionFactory
        try {
            this.sf = HibernateHelper.getSessionFactory(this.db);
        } catch (ServiceException e) {
            throw new DatabaseCorruptedException(e);
        }
    }

//    Get a object based on primary key
    protected <T extends IEntity<?>> T getObject(@NonNull Class<T> cl, @NonNull Object id) {
//        Get current session
        Session se = this.sf.getCurrentSession();

//        Create a transaction, auto commit on exit
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

//        Get the object
        T ret = se.find(cl, id);
        return ret;
    }

//    Save a object to database.
//    Will throw ConstraintViolationException if primary key already exist
//    To update or create a object, use modifyObject
    protected void setObject(@NonNull IEntity<?> obj) {
//        Get current session
        Session se = this.sf.getCurrentSession();

//        Create a transaction, auto commit on exit
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

//        Save the object
        se.persist(obj);
    }

//    Delete a object based on the primary key
//    Return true if exist and deleted, false otherwise
    protected <T extends IEntity<?>> boolean deleteObjectByID(@NonNull Class<T> cl, @NonNull Object id) {
//        Get current session
        Session se = this.sf.getCurrentSession();

//        Create a transaction, auto commit on exit
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

//        Get the object
        T o = se.find(cl, id);
//        Return false if not exist
        if (o == null) {
            return false;
        }

//        Remove the object
        se.remove(o);
//        Return true
        return true;
    }

//    Modify an object in database, create if not exist
    protected void modifyObject(@NonNull IEntity<?> obj) {
//        Get current session
        Session se = this.sf.getCurrentSession();

//        Create a transaction, auto commit on exit
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

//        Save the object
        se.merge(obj);
    }

//    Get a list of object with same class
    protected <T extends IEntity<?>> ImmutableList<T> listObject(@NonNull Class<T> cl) {
//        Get current session
        Session se = this.sf.getCurrentSession();

//        Create a transaction, auto commit on exit
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

//        Create query to find object with same class
        CriteriaBuilder cb = se.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(cl);
        Root<T> root = cq.from(cl);
        cq.select(root);

//        Get the result
        List<T> list = se.createQuery(cq).getResultList();

//        Return the list
        return ImmutableList.copyOf(list);
    }
}
