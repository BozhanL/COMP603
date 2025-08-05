package com.example.assessment.cli;

import com.example.assessment.backend.IAuthenticatable;
import com.example.assessment.backend.IPersonBackend;
import com.example.assessment.backend.Person;
import com.example.assessment.backend.PersonFileBackend;
import java.util.Scanner;

public class AssessmentOne {

    public static void main(String[] args) {
        System.out.println("Welcome"); // use ascii art

        // user inputs database location, or chooses default
        IPersonBackend pb = new PersonFileBackend();

        // user login
        String id = "";
        Person p = pb.getPersonById(id);
        if (p instanceof IAuthenticatable auth) {
            auth.safeCheckPassword("pass");
        }

    }
}
