package com.example.assessment.cli;

import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.google.errorprone.annotations.CheckReturnValue;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
@CheckReturnValue
public final class PersonInputHandler {

    @NonNull
    private static final Scanner scanner = new Scanner(System.in);

    static String getId() throws StopOperationException {
        while (true) {
            System.out.print("Please enter ID(x for exit): ");
            String input = scanner.nextLine().trim().toLowerCase(Locale.getDefault());
            if (input.isBlank()) {
                System.out.println("ID must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("ID = '%s'", input);
            return input;
        }
    }

    static String getPassword() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Password(x for exit): ");
            String input = scanner.nextLine().trim().toLowerCase(Locale.getDefault());
            if (input.isBlank()) {
                System.out.println("Password must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Password = '%s'", input);
            return input;
        }
    }

    static String getLegalFirstName() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Legal First Name(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Name must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Name = '%s'", input);
            return input;
        }
    }

    static String getLegalLastName() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Legal Last Name(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Name must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Name = '%s'", input);
            return input;
        }
    }

    static LocalDate getDateOfBirth() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Date of Birth(x for exit) (e.g, 2025-08-16): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Date of Birth must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            LocalDate date;
            try {
                date = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format!");
                continue;
            }

            System.out.printf("Date of Birth = '%s'", date);
            return date;
        }
    }

    static Gender getGender() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Gender(x for exit) (One of: MALE, FEMALE, OTHER): ");
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
            if (input.isBlank()) {
                System.out.println("Gender must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            Gender g;
            try {
                g = Gender.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: input must be one of: MALE, FEMALE, OTHER!");
                continue;
            }

            System.out.printf("Gender = '%s'", g);
            return g;
        }
    }

    static String getEmail() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Email(x for exit): ");
            String input = scanner.nextLine().trim().toLowerCase(Locale.getDefault());
            if (input.isBlank()) {
                System.out.println("Email must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Email = '%s'", input);
            return input;
        }
    }

    static String getPhone() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Phone(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Phone must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Phone = '%s'", input);
            return input;
        }
    }

    static IAddress getAddress() throws StopOperationException {
        String code = getPostCode();
        String country = getCountry();
        String state = getState();
        String city = getCity();
        String suburb = getSuburb();
        String streetName = getStreetName();
        String streetNumber = getStreetNumber();
        String unit = getUnit();

        return IAddress.of(unit, streetNumber, streetName, suburb, city, state, country, code);
    }

    static Residency getResidencyStatus() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Residency Status(x for exit) (One of: DOMESTIC, INTERNATIONAL): ");
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
            if (input.isBlank()) {
                System.out.println("Status must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            Residency r;
            try {
                r = Residency.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: input must be one of: DOMESTIC, INTERNATIONAL!");
                continue;
            }

            System.out.printf("Status = '%s'", r);
            return r;
        }
    }

    private static String getPostCode() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Post Code(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Post Code must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Post Code = '%s'", input);
            return input;
        }
    }

    private static String getCountry() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Country(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Country must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Country = '%s'", input);
            return input;
        }
    }

    private static String getState() throws StopOperationException {
        while (true) {
            System.out.print("Please enter State(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("State must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("State = '%s'", input);
            return input;
        }
    }

    private static String getCity() throws StopOperationException {
        while (true) {
            System.out.print("Please enter City(x for exit): ");
            String input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("City must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("City = '%s'", input);
            return input;
        }
    }

    private static String getSuburb() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Suburb(x for exit) (optional): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Suburb = '%s'", input);
            return input;
        }
    }

    private static String getStreetName() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Street Name(x for exit) (optional): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Street Name = '%s'", input);
            return input;
        }
    }

    private static String getStreetNumber() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Street Number(x for exit) (optional): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Street Number = '%s'", input);
            return input;
        }
    }

    private static String getUnit() throws StopOperationException {
        while (true) {
            System.out.print("Please enter Unit(x for exit) (optional): ");
            String input = scanner.nextLine().trim();
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Unit = '%s'", input);
            return input;
        }
    }
}
