package com.example.assessment.backend.types.entity;

import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import java.time.LocalDate;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

// Entity for IStudent
@Data
@Entity
@CheckReturnValue
@ToString(callSuper = true)
@DiscriminatorValue("STUDENT")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentEntity extends PersonEntity<IStudent> {

//    The residency status for a student
    @NonNull
    @Enumerated(EnumType.STRING)
    Residency residencyStatus;

//    Courses that a student has enrolled
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    Map<String, StudentCourseInfoEntity> courses;

    private StudentEntity(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull AddressEntity address,
            @NonNull Residency residencyStatus,
            @NonNull Map<String, StudentCourseInfoEntity> courses
    ) {
        super(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
        this.residencyStatus = residencyStatus;
        this.courses = courses;
    }

    public static StudentEntity of(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull AddressEntity address,
            @NonNull Residency residencyStatus,
            @NonNull Map<String, StudentCourseInfoEntity> courses
    ) {
        return new StudentEntity(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public IStudent toImmutable() {
        ImmutableMap<String, IStudentCourseInfo> c = courses
                .entrySet()
                .stream()
                .collect(
                        ImmutableMap.toImmutableMap(
                                Map.Entry::getKey,
                                (e) -> e.getValue().toImmutable()
                        )
                );

        return IStudent.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address.toImmutable(), residencyStatus, c);
    }
}
