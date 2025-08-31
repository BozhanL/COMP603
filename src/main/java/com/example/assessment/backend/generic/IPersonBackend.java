package com.example.assessment.backend.generic;

import com.example.assessment.backend.file.PersonFileBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.NonNull;

// This is the interface for managing Person
@CheckReturnValue
public interface IPersonBackend extends IBackend {

    public static IPersonBackend of() throws IOException {
        return PersonFileBackend.of();
    }

    public static IPersonBackend of(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        return PersonFileBackend.of(p);
    }

    public static IPersonBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return PersonFileBackend.of(p);
    }

//    Store the Person
//    If the Person already exist in database, throw FileAlreadyExistsException
    public abstract void setPerson(@NonNull IPerson p) throws IOException, FileAlreadyExistsException;

//    Delete the Person
    @CanIgnoreReturnValue
    public abstract boolean deletePersonById(@NonNull String id) throws IOException;

//    Change the Person
    public abstract void modifyPerson(@NonNull IPerson p) throws IOException;

//    Return a Person with same ID
    public abstract IPerson getPersonById(@NonNull String id) throws IOException, DatabaseCorruptedException, FileNotFoundException;

//    Return a Student with same ID, null if type is not IStudent
    public default IStudent getStudentById(@NonNull String id) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        IPerson p = this.getPersonById(id);

        if (p instanceof IStudent s) {
            return s;
        }
        return null;
    }

//    Return a Manager with same ID, null if type is not IManager
    public default IManager getManagerById(@NonNull String id) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        IPerson p = this.getPersonById(id);

        if (p instanceof IManager m) {
            return m;
        }
        return null;
    }

//    List all Person in the database
    public abstract ImmutableList<IPerson> listPerson() throws IOException, DatabaseCorruptedException;

//    List all Student in the database
    public default ImmutableList<IStudent> listStudent() throws IOException, DatabaseCorruptedException {
        ImmutableList<IPerson> p = this.listPerson();
        ImmutableList<IStudent> out = p.stream().filter(IStudent.class::isInstance).map(IStudent.class::cast).collect(ImmutableList.toImmutableList());
        return out;
    }

//    List all Manager in the database
    public default ImmutableList<IManager> listManager() throws IOException, DatabaseCorruptedException {
        ImmutableList<IPerson> p = this.listPerson();
        ImmutableList<IManager> out = p.stream().filter(IManager.class::isInstance).map(IManager.class::cast).collect(ImmutableList.toImmutableList());
        return out;
    }
}
