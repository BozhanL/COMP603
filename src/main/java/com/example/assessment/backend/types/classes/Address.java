package com.example.assessment.backend.types.classes;

import com.example.assessment.backend.types.interfaces.IAddress;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@With
@Immutable
@CheckReturnValue
@Value(staticConstructor = "of")
public class Address implements IAddress {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    String unit;
    @NonNull
    String streetNumber;
    @NonNull
    String streetName;
    @NonNull
    String suburb;
    @NonNull
    String city;
    @NonNull
    String state;
    @NonNull
    String country;
    @NonNull
    String postCode;

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        appendIfNotBlank(s, this.unit);
        appendIfNotBlank(s, this.streetNumber);
        appendIfNotBlank(s, this.streetName);
        appendIfNotBlank(s, this.suburb);
        appendIfNotBlank(s, this.city);
        appendIfNotBlank(s, this.state);
        appendIfNotBlank(s, this.country);
        appendIfNotBlank(s, this.postCode);

        return s.toString().trim();
    }

    private static void appendIfNotBlank(StringBuilder s, String field) {
        if (!field.isBlank()) {
            s.append(field);
            s.append(' ');
        }
    }

    @Override
    public String prettyToString() {
        return this.toString();
    }
}
