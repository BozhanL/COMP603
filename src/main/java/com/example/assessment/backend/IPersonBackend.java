package com.example.assessment.backend;

import java.io.IOException;
import java.nio.file.Path;
import lombok.NonNull;

public interface IPersonBackend {

    Person getPersonById(@NonNull Path id) throws IOException, ClassNotFoundException;

    void setPerson(@NonNull Person p) throws IOException;

    default Person getPersonById(@NonNull String id) throws IOException, ClassNotFoundException {
        return this.getPersonById(Path.of(id));
    }

    default Manager getManagerById(@NonNull String id) throws IOException, ClassNotFoundException {
        Person p = this.getPersonById(id);

        if (p instanceof Manager m) {
            return m;
        }
        return null;
    }

    default Student getStudentById(@NonNull String id) throws IOException, ClassNotFoundException {
        Person p = this.getPersonById(id);

        if (p instanceof Student s) {
            return s;
        }
        return null;
    }
}
