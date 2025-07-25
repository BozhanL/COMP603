package com.example.assessment_1.backend;

public interface IStudent extends IPerson {

    public Residency getResidencyStatus();

    public void setResidencyStatus(Residency residencyStatus);
}
