package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.stream.Stream;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.ToString;

@CheckReturnValue
@ToString(callSuper = true)
public final class PersonFileBackend extends FileBackend implements IPersonBackend {

    private static final Path DEFAULT_DATA_SUBPATH = Path.of("person");

    private PersonFileBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    private PersonFileBackend(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        this(Path.of(p));
    }

    private PersonFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        super(p.resolve(DEFAULT_DATA_SUBPATH));

        @Cleanup
        Stream<Path> stream = Files.list(this.db);
        if (stream.findAny().isEmpty()) {
            this.setPerson(IManager.defaultManager());
        }
    }

    public static PersonFileBackend of() throws IOException {
        return new PersonFileBackend();
    }

    public static PersonFileBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return new PersonFileBackend(p);
    }

    public static PersonFileBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return new PersonFileBackend(p);
    }

    @Override
    public IPerson getPersonById(String id) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        return this.getObjectByPath(IPerson.class, pathFromId(id));
    }

    @Override
    public void setPerson(@NonNull IPerson p) throws IOException, FileAlreadyExistsException {
        this.setObject(p, pathFromPerson(p));
    }

    @Override
    @CanIgnoreReturnValue
    public boolean deletePersonById(@NonNull String id) throws IOException {
        return this.deleteObjectWithPath(pathFromId(id));
    }

    @Override
    public void modifyPerson(@NonNull IPerson p) throws IOException {
        this.modifyObject(p, pathFromPerson(p));
    }

    @Override
    public ImmutableList<IPerson> listPerson() throws IOException, DatabaseCorruptedException {
        return this.listObject(IPerson.class);
    }

    @Override
    public ImmutableList<IStudent> listStudent() throws IOException, DatabaseCorruptedException {
        return this.listObject(IStudent.class);
    }

    @Override
    public ImmutableList<IManager> listManager() throws IOException, DatabaseCorruptedException {
        return this.listObject(IManager.class);
    }

    public static Path pathFromPerson(@NonNull IPerson p) {
        return pathFromId(p.getId());
    }

    public static Path pathFromId(@NonNull String id) {
        return Path.of(String.format("%s.bin", id));
    }
}
