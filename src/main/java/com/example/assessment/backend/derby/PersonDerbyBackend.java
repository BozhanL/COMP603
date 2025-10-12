package com.example.assessment.backend.derby;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.entity.PersonEntity;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import lombok.NonNull;
import lombok.ToString;

@CheckReturnValue
@ToString(callSuper = true)
public final class PersonDerbyBackend extends DerbyBackend implements IPersonBackend {

    private PersonDerbyBackend() throws IOException, DatabaseCorruptedException {
        this(DEFAULT_DATA_LOCATION);
    }

    private PersonDerbyBackend(@NonNull String p) throws IOException, DatabaseCorruptedException {
        this(Path.of(p));
    }

    private PersonDerbyBackend(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        super(p);

        if (this.listPerson().isEmpty()) {
            this.setPerson(IManager.defaultManager());
        }
    }

    public static PersonDerbyBackend of() throws IOException, DatabaseCorruptedException {
        return new PersonDerbyBackend();
    }

    public static PersonDerbyBackend of(@NonNull String p) throws IOException, DatabaseCorruptedException {
        return new PersonDerbyBackend(p);
    }

    public static PersonDerbyBackend of(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        return new PersonDerbyBackend(p);
    }

    @Override
    public void setPerson(@NonNull IPerson<?> p) throws IOException, FileAlreadyExistsException {
        this.setObject(p.toEntity());
    }

    @Override
    public boolean deletePersonById(@NonNull String id) throws IOException {
        return this.deleteObjectByID(PersonEntity.class, id);
    }

    @Override
    public void modifyPerson(@NonNull IPerson<?> p) throws IOException {
        this.modifyObject(p.toEntity());
    }

    @Override
    public IPerson<?> getPersonById(@NonNull String id) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        PersonEntity<? extends IPerson<?>> e = (PersonEntity<? extends IPerson<?>>) this.getObject(PersonEntity.class, id);
        return e.toImmutable();
    }

    @Override
    public ImmutableList<IPerson<?>> listPerson() throws IOException, DatabaseCorruptedException {
        var e = this.listObject(PersonEntity.class);
        return e.stream().map(PersonEntity<IPerson<?>>::toImmutable).collect(ImmutableList.toImmutableList());
    }
}
