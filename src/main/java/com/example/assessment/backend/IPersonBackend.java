package com.example.assessment.backend;

import java.io.IOException;
import lombok.NonNull;

public interface IPersonBackend {

    Person getPersonById(@NonNull String id) throws IOException, ClassNotFoundException;

    void setPerson(@NonNull Person p) throws IOException;

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
