package com.example.assessment.backend.generic;

import com.example.assessment.backend.file.PersonFileBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;

@CheckReturnValue
public interface IPersonBackend extends IBackend {

    public static IPersonBackend of() throws IOException {
        return PersonFileBackend.of();
    }

    public static IPersonBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return PersonFileBackend.of(p);
    }

    public static IPersonBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return PersonFileBackend.of(p);
    }

    public abstract IPerson getPersonByPartPath(@NonNull String fName) throws IOException, DatabaseCorruptedException;

    public abstract void setPerson(@NonNull IPerson p) throws IOException;

    public abstract boolean deletePersonByPartPath(@NonNull String fName) throws IOException;

    public abstract void modifyPerson(@NonNull IPerson p) throws IOException;

    public default IPerson getPersonById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        return this.getPersonByPartPath(id);
    }

    public default IPerson getPersonByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        return this.getPersonByPartPath(String.format("%s_%s", legalFirstName, legalLastName));
    }

    public default IStudent getStudentById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonById(id);

        if (p instanceof IStudent s) {
            return s;
        }
        return null;
    }

    public default IStudent getStudentByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonByName(legalFirstName, legalLastName);

        if (p instanceof IStudent s) {
            return s;
        }
        return null;
    }

    public default IManager getManagerById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonById(id);

        if (p instanceof IManager m) {
            return m;
        }
        return null;
    }

    public default IManager getManagerByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonByName(legalFirstName, legalLastName);

        if (p instanceof IManager m) {
            return m;
        }
        return null;
    }

    public default boolean deletePersonById(@NonNull String id) throws IOException {
        return this.deletePersonByPartPath(id);
    }

    public default boolean deletePersonByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException {
        return this.deletePersonByPartPath(String.format("%s_%s", legalFirstName, legalLastName));
    }

    public abstract ImmutableList<IPerson> listPerson() throws IOException, DatabaseCorruptedException;

    public default ImmutableList<IStudent> listStudent() throws IOException, DatabaseCorruptedException {
        ImmutableList<IPerson> p = this.listPerson();
        ImmutableList<IStudent> out = p.stream().filter(IStudent.class::isInstance).map(IStudent.class::cast).collect(ImmutableList.toImmutableList());
        return out;
    }

    public default ImmutableList<IManager> listManager() throws IOException, DatabaseCorruptedException {
        ImmutableList<IPerson> p = this.listPerson();
        ImmutableList<IManager> out = p.stream().filter(IManager.class::isInstance).map(IManager.class::cast).collect(ImmutableList.toImmutableList());
        return out;
    }
}
