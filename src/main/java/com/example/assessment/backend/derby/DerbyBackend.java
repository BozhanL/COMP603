package com.example.assessment.backend.derby;

import com.example.assessment.backend.generic.IBackend;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.nio.file.Path;
import java.util.List;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@ToString
@CheckReturnValue
public abstract class DerbyBackend implements IBackend {

    protected static final Path EMPTY = Path.of("");

    @Getter
    protected final String db;
    @NonNull
    protected final SessionFactory sf;

    protected DerbyBackend(@NonNull Path p) {
        if (EMPTY.equals(p)) {
            throw new IllegalArgumentException("Path must not be empty!");
        }

        p = p.resolve("db");

        this.db = String.format("jdbc:derby:%s;create=true", p.toAbsolutePath().normalize());
        System.out.println(this.db);
        this.sf = HibernateHelper.getSessionFactory(this.db);
    }

    protected <T> T getObject(@NonNull Class<T> cl, @NonNull String id) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        T ret = se.find(cl, id);
        return ret;
    }

    protected <T> void setObject(@NonNull T obj) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        se.persist(obj);
        se.flush();
    }

    protected <T> boolean deleteObjectByID(@NonNull Class<T> cl, @NonNull String id) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        T o = se.find(cl, id);
        if (o == null) {
            return false;
        }

        se.remove(o);
        se.flush();
        return true;
    }

    protected <T> void modifyObject(@NonNull T obj) {
        Session se = this.sf.getCurrentSession();
        @Cleanup("commit")
        Transaction _transaction = se.beginTransaction();

        se.persist(obj);
        se.flush();
    }

    protected <T> ImmutableList<T> listObject(@NonNull Class<T> cl) {
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
