package com.example.assessment_1.backend;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
@Setter
public class Student extends Person implements IStudent {

    @NonNull
    protected Residency residencyStatus;
}
