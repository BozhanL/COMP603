package com.example.assessment.backend.derby;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.entity.PersonEntity;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
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
    public void setPerson(@NonNull IPerson p) {
        this.setObject(p.toEntity());
    }

    @Override
    public boolean deletePersonById(@NonNull String id) {
        return this.deleteObjectByID(PersonEntity.class, id);
    }

    @Override
    public void modifyPerson(@NonNull IPerson p) {
        this.modifyObject(p.toEntity());
    }

    @Override
    public IPerson getPersonById(@NonNull String id) {
        var e = (PersonEntity<? extends IPerson>) this.getObject(PersonEntity.class, id);
        if (e == null) {
            return null;
        }
        return e.toImmutable();
    }

    @Override
    public ImmutableList<IPerson> listPerson() {
        var e = this.listObject(PersonEntity.class);
        return e.stream().map(PersonEntity<IPerson>::toImmutable).collect(ImmutableList.toImmutableList());
    }
}
