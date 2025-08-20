package com.example.assessment.cli;

import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.errorprone.annotations.CheckReturnValue;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CheckReturnValue
@AllArgsConstructor
public final class PersonInputHandler {

    @NonNull
    private final Scanner scanner;

    String getId() throws StopOperationException {
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

    String getPassword() throws StopOperationException {
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

    String getLegalFirstName() throws StopOperationException {
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

    String getLegalLastName() throws StopOperationException {
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

    LocalDate getDateOfBirth() throws StopOperationException {
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

    Gender getGender() throws StopOperationException {
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

    String getEmail() throws StopOperationException {
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

    String getPhone() throws StopOperationException {
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

    IAddress getAddress() throws StopOperationException {
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

    Residency getResidencyStatus() throws StopOperationException {
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

    IStudent getModifiedStudent(IStudent ori) throws StopOperationException {
        while (true) {
            System.out.println("1. Change password\t6. Change Email");
            System.out.println("2. Change Legal First Name\t7. Change Phone");
            System.out.println("3. Change Legal Last Name\t8. Change Address");
            System.out.println("4. Change Date of Birth\t9. Change Residency Status");
            System.out.println("5. Change Gender\t10. Change Courses");
            System.out.println();
            System.out.println("11. Save\t12. Exit");
            System.out.print("Select an option(1-12): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" ->
                    ori = ori.withPassword(this.getPassword());
                case "2" ->
                    ori = ori.withLegalFirstName(this.getLegalFirstName());
                case "3" ->
                    ori = ori.withLegalLastName(this.getLegalLastName());
                case "4" ->
                    ori = ori.withDateOfBirth(this.getDateOfBirth());
                case "5" ->
                    ori = ori.withGender(this.getGender());
                case "6" ->
                    ori = ori.withEmail(this.getEmail());
                case "7" ->
                    ori = ori.withPhone(this.getPhone());
                case "8" ->
                    ori = ori.withAddress(this.getAddress());
                case "9" ->
                    ori = ori.withResidencyStatus(this.getResidencyStatus());
                case "10" ->
                    ori = ori.withCourses(
                            new StudentCourseInputHandler(scanner)
                                    .changeCourse(ori.getCourses())
                    );
                case "11" -> {
                    return ori;
                }
                case "12" ->
                    throw new StopOperationException();
                default ->
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    IManager getModifiedManager(IManager ori) throws StopOperationException {
        while (true) {
            System.out.println("1. Change password\t6. Change Email");
            System.out.println("2. Change Legal First Name\t7. Change Phone");
            System.out.println("3. Change Legal Last Name\t8. Change Address");
            System.out.println("4. Change Date of Birth");
            System.out.println("5. Change Gender");
            System.out.println();
            System.out.println("9. Save\t10. Exit");
            System.out.print("Select an option(1-10): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" ->
                    ori = ori.withPassword(this.getPassword());
                case "2" ->
                    ori = ori.withLegalFirstName(this.getLegalFirstName());
                case "3" ->
                    ori = ori.withLegalLastName(this.getLegalLastName());
                case "4" ->
                    ori = ori.withDateOfBirth(this.getDateOfBirth());
                case "5" ->
                    ori = ori.withGender(this.getGender());
                case "6" ->
                    ori = ori.withEmail(this.getEmail());
                case "7" ->
                    ori = ori.withPhone(this.getPhone());
                case "8" ->
                    ori = ori.withAddress(this.getAddress());
                case "9" -> {
                    return ori;
                }
                case "10" ->
                    throw new StopOperationException();
                default ->
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private String getPostCode() throws StopOperationException {
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

    private String getCountry() throws StopOperationException {
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

    private String getState() throws StopOperationException {
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

    private String getCity() throws StopOperationException {
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

    private String getSuburb() throws StopOperationException {
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

    private String getStreetName() throws StopOperationException {
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

    private String getStreetNumber() throws StopOperationException {
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

    private String getUnit() throws StopOperationException {
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
