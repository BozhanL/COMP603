package com.example.assessment.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.stream.Stream;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.ToString;

@ToString
public final class PersonFileBackend extends FileBackend implements IPersonBackend {

    protected static final Path DEFAULT_DATA_SUBPATH = Path.of("person");

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
    public Person getPersonByPartPath(@NonNull String fName) throws IOException, ClassNotFoundException {
        Object o = this.getObjectByPartPath(fName);
        if (o instanceof Person person) {
            return person;
        }

        return null;
    }

    @Override
    public void setPerson(@NonNull Person p) throws IOException {
        this.setObject(p);
    }

    @Override
    public boolean deletePersonByPartPath(@NonNull String fName) throws IOException {
        return this.deleteObjectWithName(fName);
    }

    @Override
    public void modifyPerson(@NonNull Person p) throws IOException {
        this.modifyObject(p);
    }
}
