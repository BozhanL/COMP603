package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.Address;
import com.example.assessment.backend.types.Gender;
import com.example.assessment.backend.types.IPerson;
import com.example.assessment.backend.types.Manager;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.ToString;

@CheckReturnValue
@ToString(callSuper = true)
public final class PersonFileBackend extends FileBackend implements IPersonBackend {

    private static final Path DEFAULT_DATA_SUBPATH = Path.of("person");

    public PersonFileBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    public PersonFileBackend(@NonNull String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    public PersonFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        super(p.resolve(DEFAULT_DATA_SUBPATH));

        @Cleanup
        Stream<Path> stream = Files.list(this.db);
        if (stream.findAny().isEmpty()) {
            Manager DEFAULT_MANAGER = new Manager(
                    "admin",
                    "admin",
                    "admin",
                    "admin",
                    LocalDate.MIN,
                    Gender.OTHER,
                    "",
                    "",
                    new Address("", "", "", "", "", "", "", "")
            );
            this.setPerson(DEFAULT_MANAGER);
        }
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
}
