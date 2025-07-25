package com.example.assessment_1.backend;

import com.example.assessment_1.backend.Person.Gender;
import com.example.assessment_1.backend.Person.Residency;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public interface IPerson {

    public String getId();

    public void setId(String id);

    public String getLegalFirstName();

    public void setLegalFirstName(String legalFirstName);

    public String getLegalLastName();

    public void setLegalLastName(String legalLastName);

    public LocalDate getDateOfBirth();

    public void setDateOfBirth(LocalDate dateOfBirth);

    default public long getAge() {
        return ChronoUnit.YEARS.between(this.getDateOfBirth(), LocalDate.now());
    }

    public Gender getGender();

    public void setGender(Gender gender);

    public Residency getResidencyStatus();

    public void setResidencyStatus(Residency residencyStatus);

    public String getEmail();

    public void setEmail(String email);

    public String getPhone();

    public void setPhone(String phone);

    public Person.Address getAddress();

    public void setAddress(Person.Address address);
}
