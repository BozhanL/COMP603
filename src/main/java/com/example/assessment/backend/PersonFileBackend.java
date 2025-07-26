package com.example.assessment.backend;

import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;
import lombok.ToString;

@ToString
public class PersonFileBackend extends FileBackend implements IPersonBackend {

    public static final Path DEFAULT_DATABASE = Path.of(System.getProperty("user.home"), ".student/filedb", "person");

    public PersonFileBackend() throws IOException {
        this(DEFAULT_DATABASE);
    }

    public PersonFileBackend(@NonNull String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    public PersonFileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        super(p);
    }

    @Override
    public Person getPersonById(@NonNull Path id) throws IOException, ClassNotFoundException {
        Object o = this.getObjectByPath(id);
        if (o instanceof Person person) {
            return person;
        }

        return null;
    }

    @Override
    public void setPerson(@NonNull Person p) throws IOException {
        this.setObjectWithName(p);
    }
}
