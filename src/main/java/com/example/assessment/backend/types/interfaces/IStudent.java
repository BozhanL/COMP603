package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.types.classes.Student;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.time.LocalDate;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface IStudent extends IPerson {

    public static IStudent of(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull IAddress address,
            @NonNull Residency residencyStatus,
            @NonNull ImmutableMap<String, IStudentCourseInfo> courses
    ) {
        return Student.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    public static IStudent of(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull IAddress address,
            @NonNull Residency residencyStatus
    ) {
        return IStudent.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, ImmutableMap.of());
    }

    @Override
    public abstract IStudent withPassword(@NonNull String password);

    @Override
    public abstract IStudent withLegalFirstName(@NonNull String legalFirstName);

    @Override
    public abstract IStudent withLegalLastName(@NonNull String legalLastName);

    @Override
    public abstract IStudent withDateOfBirth(@NonNull LocalDate dateOfBirth);

    @Override
    public abstract IStudent withGender(@NonNull Gender gender);

    @Override
    public abstract IStudent withEmail(@NonNull String email);

    @Override
    public abstract IStudent withPhone(@NonNull String phone);

    @Override
    public abstract IStudent withAddress(@NonNull IAddress address);

    public abstract IStudent withResidencyStatus(@NonNull Residency residencyStatus);

    public abstract IStudent withCourses(@NonNull ImmutableMap<String, IStudentCourseInfo> courses);

    public abstract Residency getResidencyStatus();

    public abstract ImmutableMap<String, IStudentCourseInfo> getCourses();
}
