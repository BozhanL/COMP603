package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Files;
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

    private PersonFileBackend(@NonNull String p) throws IOException, IllegalArgumentException {
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

    public static IPersonBackend of() throws IOException {
        return new PersonFileBackend();
    }

    public static IPersonBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return new PersonFileBackend(p);
    }

    public static IPersonBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return new PersonFileBackend(p);
    }

    @Override
    public IPerson getPersonByPartPath(@NonNull String fName) throws IOException, DatabaseCorruptedException {
        Object o = this.getObjectByPartPath(fName);
        if (o instanceof IPerson person) {
            return person;
        }

        return null;
    }

    @Override
    public void setPerson(@NonNull IPerson p) throws IOException {
        this.setObject(p);
    }

    @Override
    public boolean deletePersonByPartPath(@NonNull String fName) throws IOException {
        return this.deleteObjectWithName(fName);
    }

    @Override
    public void modifyPerson(@NonNull IPerson p) throws IOException {
        this.modifyObject(p);
    }

    @Override
    public ImmutableList<IPerson> listPerson() throws IOException, DatabaseCorruptedException {
        ImmutableList<Object> obj = this.listObject();
        ImmutableList<IPerson> out = obj.stream().filter(IPerson.class::isInstance).map(IPerson.class::cast).collect(ImmutableList.toImmutableList());
        return out;
    }
}
