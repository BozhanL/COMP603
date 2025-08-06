package com.example.assessment.backend;

import java.io.IOException;

import lombok.NonNull;

public interface IPersonBackend {

    Person getPersonByPartPath(@NonNull String fName) throws IOException, DatabaseCorruptedException;

    void setPerson(@NonNull Person p) throws IOException;

    boolean deletePersonByPartPath(@NonNull String fName) throws IOException;

    void modifyPerson(@NonNull Person p) throws IOException;

    default Person getPersonById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        return this.getPersonByPartPath(id);
    }

    default Person getPersonByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        return this.getPersonByPartPath(String.format("%s_%s", legalFirstName, legalLastName));
    }

    default Student getStudentById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        Person p = this.getPersonById(id);

        if (p instanceof Student s) {
            return s;
        }
        return null;
    }

    default Student getStudentByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        Person p = this.getPersonByName(legalFirstName, legalLastName);

        if (p instanceof Student s) {
            return s;
        }
        return null;
    }

    default Manager getManagerById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        Person p = this.getPersonById(id);

        if (p instanceof Manager m) {
            return m;
        }
        return null;
    }

    default Manager getManagerByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        Person p = this.getPersonByName(legalFirstName, legalLastName);

        if (p instanceof Manager m) {
            return m;
        }
        return null;
    }

    default boolean deletePersonById(@NonNull String id) throws IOException {
        return this.deletePersonByPartPath(id);
    }

    default boolean deletePersonByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException {
        return this.deletePersonByPartPath(String.format("%s_%s", legalFirstName, legalLastName));
    }
}
