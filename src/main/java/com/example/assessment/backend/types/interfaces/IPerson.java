package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.derby.IEntity;
import com.example.assessment.backend.derby.IToHibernateEntity;
import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.UserType;
import com.example.assessment.cli.IMainDashboard;
import com.example.assessment.gui.IGetMainPanel;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;
import lombok.NonNull;

// This interface contains all basic methods that needs to implements for a
// Person in the system.
@Immutable
@CheckReturnValue
public interface IPerson extends IAuthentication, Serializable, IPrettyPrint, IToHibernateEntity<IEntity<?>>, IGetMainPanel {

//    Get the type of the person
    public abstract UserType getType();

//    Get the age of the person
    public abstract int getAge();

//    Create a new person object with new password
    public abstract IPerson withPassword(@NonNull String password);

//    Create a new person object with new Legal first name
    public abstract IPerson withLegalFirstName(@NonNull String legalFirstName);

//    Create a new person object with new Legal last name
    public abstract IPerson withLegalLastName(@NonNull String legalLastName);

//    Create a new person object with new Date of birth
    public abstract IPerson withDateOfBirth(@NonNull LocalDate dateOfBirth);

//    Create a new person object with new Gender
    public abstract IPerson withGender(@NonNull Gender gender);

//    Create a new person object with new Email address
    public abstract IPerson withEmail(@NonNull String email);

//    Create a new person object with new Phone number
    public abstract IPerson withPhone(@NonNull String phone);

//    Create a new person object with new Address
    public abstract IPerson withAddress(@NonNull IAddress address);

//    Get the ID of the person
    public abstract String getId();

//    Get the Password of the person
    public abstract String getPassword();

//    Fix spell by Copilot
//    Get the Legal first name
    public abstract String getLegalFirstName();

//    Fix spell by Copilot
//    Get the Legal last name
    public abstract String getLegalLastName();

//    Fix spell by Copilot
//    Get the Date of birth
    public abstract LocalDate getDateOfBirth();

//    Fix spell by Copilot
//    Get the Gender
    public abstract Gender getGender();

//    Fix spell by Copilot
//    Get the Email address
    public abstract String getEmail();

//    Fix spell by Copilot
//    Get the Phone number
    public abstract String getPhone();

//    Fix spell by Copilot
//    Get the Address
    public abstract IAddress getAddress();

//    Get the dashboard for the person
    public abstract IMainDashboard getDashboard(@NonNull Scanner sc, @NonNull ICombinedBackend cb);
}
