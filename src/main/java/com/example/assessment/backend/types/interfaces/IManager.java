package com.example.assessment.backend.types.interfaces;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.classes.Manager;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.cli.IMainDashboard;
import com.example.assessment.cli.ManagerDashboard;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.Immutable;
import java.time.LocalDate;
import java.util.Scanner;
import lombok.NonNull;

@Immutable
@CheckReturnValue
public interface IManager extends IPerson {

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

    public static IManager defaultManager() {
        return IManager.of(
                "admin",
                "admin",
                "admin",
                "admin",
                LocalDate.MIN,
                Gender.OTHER,
                "",
                "",
                IAddress.of("", "", "", "", "", "", "", "")
        );
    }

    @Override
    public default IMainDashboard getDashboard(@NonNull Scanner sc, @NonNull ICombinedBackend cb) {
        return new ManagerDashboard(sc, cb);
    }

    @Override
    public abstract IManager withPassword(@NonNull String password);

    @Override
    public abstract IManager withLegalFirstName(@NonNull String legalFirstName);

    @Override
    public abstract IManager withLegalLastName(@NonNull String legalLastName);

    @Override
    public abstract IManager withDateOfBirth(@NonNull LocalDate dateOfBirth);

    @Override
    public abstract IManager withGender(@NonNull Gender gender);

    @Override
    public abstract IManager withEmail(@NonNull String email);

    @Override
    public abstract IManager withPhone(@NonNull String phone);

    @Override
    public abstract IManager withAddress(@NonNull IAddress address);
}
