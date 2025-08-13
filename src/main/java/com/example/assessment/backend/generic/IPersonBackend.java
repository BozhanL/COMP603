package com.example.assessment.backend.generic;

import com.example.assessment.backend.types.IManager;
import com.example.assessment.backend.types.IPerson;
import com.example.assessment.backend.types.IStudent;
import java.io.IOException;
import lombok.NonNull;

public interface IPersonBackend extends IBackend {

    public abstract IPerson getPersonByPartPath(@NonNull String fName) throws IOException, DatabaseCorruptedException;

    public abstract void setPerson(@NonNull IPerson p) throws IOException;

    public abstract boolean deletePersonByPartPath(@NonNull String fName) throws IOException;

    public abstract void modifyPerson(@NonNull IPerson p) throws IOException;

    public default IPerson getPersonById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        return this.getPersonByPartPath(id);
    }

    public default IPerson getPersonByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        return this.getPersonByPartPath(String.format("%s_%s", legalFirstName, legalLastName));
    }

    public default IStudent getStudentById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonById(id);

        if (p instanceof IStudent s) {
            return s;
        }
        return null;
    }

    public default IStudent getStudentByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonByName(legalFirstName, legalLastName);

        if (p instanceof IStudent s) {
            return s;
        }
        return null;
    }

    public default IManager getManagerById(@NonNull String id) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonById(id);

        if (p instanceof IManager m) {
            return m;
        }
        return null;
    }

    public default IManager getManagerByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException, DatabaseCorruptedException {
        IPerson p = this.getPersonByName(legalFirstName, legalLastName);

        if (p instanceof IManager m) {
            return m;
        }
        return null;
    }

    public default boolean deletePersonById(@NonNull String id) throws IOException {
        return this.deletePersonByPartPath(id);
    }

    public default boolean deletePersonByName(@NonNull String legalFirstName, @NonNull String legalLastName) throws IOException {
        return this.deletePersonByPartPath(String.format("%s_%s", legalFirstName, legalLastName));
    }
}
