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

@ToString
@CheckReturnValue
public abstract class DerbyBackend implements IBackend {

    protected static final Path EMPTY = Path.of("");

    @Getter
    protected final String db;
    @NonNull
    protected final SessionFactory sf;

//    Copied from: https://stackoverflow.com/a/5937917
    private static boolean isDirEmpty(final Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }

    protected DerbyBackend(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        if (EMPTY.equals(p)) {
            throw new IllegalArgumentException("Path must not be empty!");
        }

        if (Files.isDirectory(p) && isDirEmpty(p)) {
            Files.deleteIfExists(p);
        }

        this.db = String.format("jdbc:derby:%s;create=true", p.toAbsolutePath().normalize());

        try {
            this.sf = HibernateHelper.getSessionFactory(this.db);
        } catch (ServiceException e) {
            throw new DatabaseCorruptedException(e);
        }
    }

    protected <T> T getObject(@NonNull Class<T> cl, @NonNull Object id) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        T ret = se.find(cl, id);
        return ret;
    }

    protected void setObject(@NonNull IEntity<?> obj) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        se.persist(obj);
        se.flush();
    }

    protected <T extends IEntity<?>> boolean deleteObjectByID(@NonNull Class<T> cl, @NonNull Object id) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        T o = se.find(cl, id);
        if (o == null) {
            return false;
        }

        se.remove(o);
        return true;
    }

    protected void modifyObject(@NonNull IEntity<?> obj) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();
        se.merge(obj);
    }

    protected <T extends IEntity<?>> ImmutableList<T> listObject(@NonNull Class<T> cl) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        CriteriaBuilder cb = se.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(cl);
        Root<T> root = cq.from(cl);
        cq.select(root);

        List<T> list = se.createQuery(cq).getResultList();

        return ImmutableList.copyOf(list);
    }
}
