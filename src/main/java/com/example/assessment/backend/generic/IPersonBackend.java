package com.example.assessment.backend.generic;

import com.example.assessment.backend.derby.PersonDerbyBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;

// This is the interface for managing Person
@CheckReturnValue
public interface IPersonBackend extends IBackend {

    public static IPersonBackend of() throws IOException, DatabaseCorruptedException {
        return PersonDerbyBackend.of();
    }

    public static IPersonBackend of(@NonNull String p) throws IOException, DatabaseCorruptedException {
        return PersonDerbyBackend.of(p);
    }

    public static IPersonBackend of(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        return PersonDerbyBackend.of(p);
    }

//    Store the Person
//    If the Person already exist in database, throw FileAlreadyExistsException
    public abstract void setPerson(@NonNull IPerson p);

//    Delete the Person
    @CanIgnoreReturnValue
    public abstract boolean deletePersonById(@NonNull String id);

//    Change the Person
    public abstract void modifyPerson(@NonNull IPerson p);

//    Return a Person with same ID
    public abstract IPerson getPersonById(@NonNull String id);

//    Return a Student with same ID, null if type is not IStudent
    public default IStudent getStudentById(@NonNull String id) {
        IPerson p = this.getPersonById(id);

        if (p instanceof IStudent s) {
            return s;
        }
        return null;
    }

//    Return a Manager with same ID, null if type is not IManager
    public default IManager getManagerById(@NonNull String id) {
        IPerson p = this.getPersonById(id);

        if (p instanceof IManager m) {
            return m;
        }
        return null;
    }

//    List all Person in the database
    public abstract ImmutableList<IPerson> listPerson();

    //    List all Student in the database
    public default ImmutableList<IStudent> listStudent() {
        ImmutableList<IPerson> p = this.listPerson();
        ImmutableList<IStudent> out = p.stream().filter(IStudent.class::isInstance).map(IStudent.class::cast).collect(ImmutableList.toImmutableList());
        return out;
    }

//    List all Manager in the database
    public default ImmutableList<IManager> listManager() {
        ImmutableList<IPerson> p = this.listPerson();
        ImmutableList<IManager> out = p.stream().filter(IManager.class::isInstance).map(IManager.class::cast).collect(ImmutableList.toImmutableList());
        return out;
    }
}
