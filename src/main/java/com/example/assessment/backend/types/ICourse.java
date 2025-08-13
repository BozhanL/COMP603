package com.example.assessment.backend.types;

import lombok.NonNull;

public interface ICourse extends ISelfSerializable {

    public abstract ICourse withCode(@NonNull ICourseCode code);

    public abstract ICourse withName(@NonNull String name);

    public abstract ICourse withPoints(int points);

    public abstract ICourse withDescription(@NonNull String description);

    public abstract ICourseCode getCode();

    public abstract String getName();

    public abstract int getPoints();

    public abstract String getDescription();
}
