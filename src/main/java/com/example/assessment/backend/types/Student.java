package com.example.assessment.backend.types;

import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.With;

@With
@Value
@Immutable
@CheckReturnValue
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Student extends Person implements IStudent {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final UserType TYPE = UserType.STUDENT;

    @NonNull
    Residency residencyStatus;
    @NonNull
    ImmutableMap<String, IStudentCourseInfo> courses;

    public Student(
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
    ) throws IllegalArgumentException {
        super(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
        this.residencyStatus = residencyStatus;
        this.courses = courses;
    }

    @Override
    public Student withPassword(@NonNull String password) {
        return Objects.equals(this.password, password) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withLegalFirstName(@NonNull String legalFirstName) {
        return Objects.equals(this.legalFirstName, legalFirstName) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withLegalLastName(@NonNull String legalLastName) {
        return Objects.equals(this.legalLastName, legalLastName) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withDateOfBirth(@NonNull LocalDate dateOfBirth) {
        return Objects.equals(this.dateOfBirth, dateOfBirth) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withGender(@NonNull Gender gender) {
        return Objects.equals(this.gender, gender) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withEmail(@NonNull String email) {
        return Objects.equals(this.email, email) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withPhone(@NonNull String phone) {
        return Objects.equals(this.phone, phone) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withAddress(@NonNull IAddress address) {
        return Objects.equals(this.address, address) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withResidencyStatus(@NonNull Residency residencyStatus) {
        return Objects.equals(this.residencyStatus, residencyStatus) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withCourses(@NonNull ImmutableMap<String, IStudentCourseInfo> courses) {
        return Objects.equals(this.courses, courses) ? this : new Student(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public UserType getType() {
        return getTypeStatic();
    }

    public static UserType getTypeStatic() {
        return TYPE;
    }
}
