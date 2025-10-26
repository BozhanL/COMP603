package com.example.assessment.backend.types.entity;

import com.example.assessment.backend.derby.IEntity;
import com.example.assessment.backend.derby.IToImmutable;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

// Entity for ICourse
@Data
@Entity
@CheckReturnValue
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseEntity implements IToImmutable<ICourse>, IEntity<ICourse> {

    @NonNull
    @EmbeddedId
    CourseCodeEntity code;

    @NonNull
    String name;

    int points;

    @NonNull
    String description;

    @Override
    public ICourse toImmutable() {
        return ICourse.of(code.toImmutable(), name, points, description);
    }
}
