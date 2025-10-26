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
import lombok.Cleanup;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.Session;
import org.hibernate.Transaction;

// Managing Person using Derby based backend
@CheckReturnValue
@ToString(callSuper = true)
public final class PersonDerbyBackend extends DerbyBackend implements IPersonBackend {

//    Create the database with default path
    private PersonDerbyBackend() throws IOException, DatabaseCorruptedException {
        this(DEFAULT_DATA_LOCATION);
    }

    private PersonDerbyBackend(@NonNull String p) throws IOException, DatabaseCorruptedException {
        this(Path.of(p));
    }

    private PersonDerbyBackend(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        super(p);

//        Add a default manager if there is no manager
        if (this.listManager().isEmpty()) {
//        Get current session
            Session se = this.sf.getCurrentSession();

//        Create a transaction, auto commit on exit
            @Cleanup("commit")
            Transaction _transaction = se.beginTransaction();

//        Save the default manager
            se.persist(IManager.defaultManager().toEntity());
        }
    }

//    These are static method to construct a Object
    public static PersonDerbyBackend of() throws IOException, DatabaseCorruptedException {
        return new PersonDerbyBackend();
    }

    public static PersonDerbyBackend of(@NonNull String p) throws IOException, DatabaseCorruptedException {
        return new PersonDerbyBackend(p);
    }

    public static PersonDerbyBackend of(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        return new PersonDerbyBackend(p);
    }

//    Save a person to database
//    Throw ConstraintViolationException if already exist
//    Use modifyPerson to change content of an existing person
    @Override
    public void setPerson(@NonNull IPerson p) {
        this.setObject(p.toEntity());
    }

//    Delete a person based on the id
//    Return true if exist and deleted, false otherwise
    @Override
    public boolean deletePersonById(@NonNull String id) {
        return this.deleteObjectByID(PersonEntity.class, id);
    }

    @Override
    public void modifyPerson(@NonNull IPerson p) {
        this.modifyObject(p.toEntity());
    }

//    Get a Person object by id
    @Override
    public IPerson getPersonById(@NonNull String id) {
//        Get the entity
        PersonEntity<?> e = this.getObject(PersonEntity.class, id);

//        Return null if it is null
        if (e == null) {
            return null;
        }

//        Convert it to immutable
        return e.toImmutable();
    }

//    Get a list of person in database
    @Override
    public ImmutableList<IPerson> listPerson() {
//        Get the list of entity
        var e = this.listObject(PersonEntity.class);
//        Convert the entity to immutable
        return e.stream().map(PersonEntity<IPerson>::toImmutable).collect(ImmutableList.toImmutableList());
    }
}
