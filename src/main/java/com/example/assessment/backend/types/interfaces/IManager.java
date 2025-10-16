package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.classes.Manager;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.cli.IMainDashboard;
import com.example.assessment.cli.ManagerDashboard;
import com.example.assessment.gui.manager.ManagerMainPanel;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.time.LocalDate;
import java.util.Scanner;
import javax.swing.JPanel;
import lombok.NonNull;

// This is the interface for manager of the system
@Immutable
@CheckReturnValue
public interface IManager extends IPerson {

//    Static construtor to create IManager
    public static IManager of(
            @NonNull String id,
            @NonNull String password,
            @NonNull String legalFirstName,
            @NonNull String legalLastName,
            @NonNull LocalDate dateOfBirth,
            @NonNull Gender gender,
            @NonNull String email,
            @NonNull String phone,
            @NonNull IAddress address
    ) {
        return Manager.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);
    }

//    Static construtor to create a default IManager
    public static IManager defaultManager() {
        return IManager.of(
                "admin",
                "admin",
                "admin",
                "admin",
                LocalDate.EPOCH,
                Gender.OTHER,
                "",
                "",
                IAddress.of("", "", "", "", "", "", "", "")
        );
    }

//    Create a new IManager object with new password
    @Override
    public abstract IManager withPassword(@NonNull String password);

//    Create a new IManager object with new legalFirstName
    @Override
    public abstract IManager withLegalFirstName(@NonNull String legalFirstName);

//    Create a new IManager object with new legalLastName
    @Override
    public abstract IManager withLegalLastName(@NonNull String legalLastName);

//    Create a new IManager object with new dateOfBirth
    @Override
    public abstract IManager withDateOfBirth(@NonNull LocalDate dateOfBirth);

//    Create a new IManager object with new gender
    @Override
    public abstract IManager withGender(@NonNull Gender gender);

//    Create a new IManager object with new email
    @Override
    public abstract IManager withEmail(@NonNull String email);

//    Create a new IManager object with new phone
    @Override
    public abstract IManager withPhone(@NonNull String phone);

//    Create a new IManager object with new address
    @Override
    public abstract IManager withAddress(@NonNull IAddress address);

//    Return a dashboard to display IManager
    @Override
    public default IMainDashboard getDashboard(@NonNull Scanner sc, @NonNull ICombinedBackend cb) {
        return new ManagerDashboard(sc, cb);
    }

    @Override
    public default JPanel getPanel(@NonNull ICombinedBackend cb) {
        return new ManagerMainPanel(cb, this);
    }
}
