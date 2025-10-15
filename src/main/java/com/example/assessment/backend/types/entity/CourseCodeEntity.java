package com.example.assessment.backend.types.entity;

import com.example.assessment.backend.derby.IEntity;
import com.example.assessment.backend.derby.IToImmutable;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Embeddable
@CheckReturnValue
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseCodeEntity implements IToImmutable<ICourseCode>, IEntity<ICourseCode> {

//    For COMP500, store as:
//    E.g. COMP
    @NonNull
    String departmentCode;
//    E.g. 5
    int level;
//    E.g. 00
    int courseNumber;

    @Override
    public ICourseCode toImmutable() {
        return ICourseCode.of(departmentCode, level, courseNumber);
    }
}
