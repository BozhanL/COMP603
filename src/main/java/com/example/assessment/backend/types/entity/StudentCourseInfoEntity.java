package com.example.assessment.backend.types.entity;

import com.example.assessment.backend.derby.IEntity;
import com.example.assessment.backend.derby.IToImmutable;
import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDate;
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
public class StudentCourseInfoEntity implements IToImmutable<IStudentCourseInfo>, IEntity<IStudentCourseInfo> {

    @NonNull
    String courseCode;
    @NonNull
    @Enumerated(EnumType.STRING)
    Grade grade;
    @NonNull
    LocalDate starts;
    @NonNull
    String location;

    @Override
    public IStudentCourseInfo toImmutable() {
        return IStudentCourseInfo.of(courseCode, grade, starts, location);
    }
}
