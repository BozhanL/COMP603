package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.stream.Stream;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.ToString;

// Managing Person using File based backend
@CheckReturnValue
@ToString(callSuper = true)
public final class PersonFileBackend extends FileBackend implements IPersonBackend {

//    Default path is under DEFAULT_DATA_LOCATION/person
    private static final Path DEFAULT_DATA_SUBPATH = Path.of("person");

//    Create the database with default path
    private PersonFileBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    private PersonFileBackend(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        this(Path.of(p));
    }

    private PersonFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
//        Path is always at p/DEFAULT_DATA_SUBPATH
        super(p.resolve(DEFAULT_DATA_SUBPATH));

//        Create a stream to list files in the path
//        @Cleanup will close stream when automatically close stream
        @Cleanup
        Stream<Path> stream = Files.list(this.db);
        if (stream.findAny().isEmpty()) {
//            Create a default manager when database is empty
            IManager o = IManager.defaultManager();
            Path path = this.db.resolve(pathFromPerson(o));

            File f = path.toFile();

            try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
                oos.writeObject(o);
            }
        }
    }

//    These are static method to construct a Object
    public static PersonFileBackend of() throws IOException {
        return new PersonFileBackend();
    }

    public static PersonFileBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return new PersonFileBackend(p);
    }

    public static PersonFileBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return new PersonFileBackend(p);
    }

//    Get a Person with ID
    @Override
    public IPerson getPersonById(String id) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        return this.getObjectByPath(IPerson.class, pathFromId(id));
    }

//    Store a Person with ID
//    Throw FileAlreadyExistsException if already exist
//    Use modifyPerson to change content of an existing Person
    @Override
    public void setPerson(@NonNull IPerson p) throws IOException, FileAlreadyExistsException {
        this.setObject(p, pathFromPerson(p));
    }

//    Fix spell by Copilot
//    Delete a Person with ID
//    Return true if delete a file, false if file not exist
    @Override
    @CanIgnoreReturnValue
    public boolean deletePersonById(@NonNull String id) throws IOException {
        return this.deleteObjectWithPath(pathFromId(id));
    }

//    Change the content of a Person
    @Override
    public void modifyPerson(@NonNull IPerson p) throws IOException {
        this.modifyObject(p, pathFromPerson(p));
    }

//    List all Person in folder
    @Override
    public ImmutableList<IPerson> listPerson() throws IOException, DatabaseCorruptedException {
        return this.listObject(IPerson.class);
    }

//    List all Student in folder
    @Override
    public ImmutableList<IStudent> listStudent() throws IOException, DatabaseCorruptedException {
        return this.listObject(IStudent.class);
    }

//    List all Manager in folder
    @Override
    public ImmutableList<IManager> listManager() throws IOException, DatabaseCorruptedException {
        return this.listObject(IManager.class);
    }

//    Get the path for a Person
    public static Path pathFromPerson(@NonNull IPerson p) {
        return pathFromId(p.getId());
    }

//    Get the path for a Person with its ID
    public static Path pathFromId(@NonNull String id) {
        return Path.of(String.format("%s.bin", id));
    }
}
