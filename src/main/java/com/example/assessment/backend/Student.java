package com.example.assessment.backend;

import java.time.LocalDate;
import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@With
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {

    private static final long serialVersionUID = 1L;
    private static final UserType TYPE = UserType.STUDENT;

    @NonNull
    protected Residency residencyStatus;
    @NonNull
    protected ImmutableList<StudentCourseInfo> courses;

    public Student(
            @NonNull String id,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull Address address,
            @NonNull Residency residencyStatus,
            @NonNull ImmutableList<StudentCourseInfo> courses
    ) throws IllegalArgumentException {
        super(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
        this.residencyStatus = residencyStatus;
        this.courses = courses;
    }

    @Override
    public Student withId(@NonNull String id) throws IllegalArgumentException {
        return this.id == id ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withLegalFirstName(@NonNull String legalFirstName) {
        return this.legalFirstName == legalFirstName ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withLegalLastName(@NonNull String legalLastName) {
        return this.legalLastName == legalLastName ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withDateOfBirth(@NonNull LocalDate dateOfBirth) {
        return this.dateOfBirth == dateOfBirth ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withGender(@NonNull Gender gender) {
        return this.gender == gender ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withEmail(@NonNull String email) {
        return this.email == email ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withPhone(@NonNull String phone) {
        return this.phone == phone ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public Student withAddress(@NonNull Address address) {
        return this.address == address ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    public Student withResidencyStatus(@NonNull Residency residencyStatus) {
        return this.residencyStatus == residencyStatus ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    public Student withCourses(@NonNull ImmutableList<StudentCourseInfo> courses) {
        return this.courses == courses ? this : new Student(id, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus, courses);
    }

    @Override
    public UserType getType() {
        return getTypeStatic();
    }

    public static UserType getTypeStatic() {
        return TYPE;
    }
}
