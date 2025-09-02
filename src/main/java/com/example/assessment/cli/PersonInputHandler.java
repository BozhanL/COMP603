package com.example.assessment.cli;

import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This is the helper to prompt user to enter person information
@CheckReturnValue
@AllArgsConstructor
public final class PersonInputHandler {

    @NonNull
    private final Scanner scanner;
    @NonNull
    private final StudentCourseInputHandler scih;

    public PersonInputHandler(@NonNull Scanner scanner) {
        this(scanner, new StudentCourseInputHandler(scanner));
    }

//    Ask user for person ID
    public String getId() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter ID(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim().toLowerCase(Locale.getDefault());
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: ID must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("ID = '%s'\n", input);
            return input;
        }
    }

//    Ask user for Password
    public String getPassword() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Password(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim().toLowerCase(Locale.getDefault());
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Password must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Password = '%s'\n", input);
            return input;
        }
    }

//    Ask user for LegalFirstName
    public String getLegalFirstName() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Legal First Name(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Name must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Name = '%s'\n", input);
            return input;
        }
    }

//    Ask user for LegalLastName
    public String getLegalLastName() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Legal Last Name(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Name must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Name = '%s'\n", input);
            return input;
        }
    }

//    Ask user for DateOfBirth
    public LocalDate getDateOfBirth() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Date of Birth(x for exit) (e.g, 2025-08-16): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Date of Birth must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

//            Convert it to LocalDate
            LocalDate date;
            try {
                date = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format!");
                continue;
            }

            System.out.printf("Date of Birth = '%s'\n", date);
            return date;
        }
    }

//    Ask user for Gender
    public Gender getGender() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Gender(x for exit) (One of: MALE, FEMALE, OTHER): ");
//            Get the input
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Gender must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

//            Convert it to Gender
            Gender g;
            try {
                g = Gender.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: input must be one of: MALE, FEMALE, OTHER!");
                continue;
            }

            System.out.printf("Gender = '%s'\n", g);
            return g;
        }
    }

//    Ask user for Email
    public String getEmail() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Email(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim().toLowerCase(Locale.getDefault());
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Email must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Email = '%s'\n", input);
            return input;
        }
    }

//    Ask user for Phone
    public String getPhone() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Phone(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Phone must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Phone = '%s'\n", input);
            return input;
        }
    }

//    Ask user for Address
    public IAddress getAddress() throws StopOperationException {
//        Get the address
        String code = getPostCode();
        String country = getCountry();
        String state = getState();
        String city = getCity();
        String suburb = getSuburb();
        String streetName = getStreetName();
        String streetNumber = getStreetNumber();
        String unit = getUnit();

//        Create an object
        return IAddress.of(unit, streetNumber, streetName, suburb, city, state, country, code);
    }

//    Ask user for ResidencyStatus
    public Residency getResidencyStatus() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Residency Status(x for exit) (One of: DOMESTIC, INTERNATIONAL): ");
//            Get the input
            String input = scanner.nextLine().trim().toUpperCase(Locale.getDefault());
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Status must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

//            Convert it to Residency
            Residency r;
            try {
                r = Residency.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: input must be one of: DOMESTIC, INTERNATIONAL!");
                continue;
            }

            System.out.printf("Status = '%s'\n", r);
            return r;
        }
    }

//    Prompt user to change student
    public IStudent getModifiedStudent(@NonNull IStudent ori) {
        IStudent n = ori;
        while (true) {
//            print options
            System.out.println("1. Change password\t\t6. Change Email");
            System.out.println("2. Change Legal First Name\t7. Change Phone");
            System.out.println("3. Change Legal Last Name\t8. Change Address");
            System.out.println("4. Change Date of Birth\t9. Change Residency Status");
            System.out.println("5. Change Gender\t\t10. Change Courses");
            System.out.println();
            System.out.println("11. Save\t12. Exit");
            System.out.print("Select an option(1-12): ");

//            Get the input
            String choice = scanner.nextLine().trim();

//            Execute
            try {
                switch (choice) {
                    case "1" ->
                        n = n.withPassword(this.getPassword());
                    case "2" ->
                        n = n.withLegalFirstName(this.getLegalFirstName());
                    case "3" ->
                        n = n.withLegalLastName(this.getLegalLastName());
                    case "4" ->
                        n = n.withDateOfBirth(this.getDateOfBirth());
                    case "5" ->
                        n = n.withGender(this.getGender());
                    case "6" ->
                        n = n.withEmail(this.getEmail());
                    case "7" ->
                        n = n.withPhone(this.getPhone());
                    case "8" ->
                        n = n.withAddress(this.getAddress());
                    case "9" ->
                        n = n.withResidencyStatus(this.getResidencyStatus());
                    case "10" ->
                        n = n.withCourses(this.changeCourse(n.getCourses()));
                    case "11" -> {
                        return n;
                    }
                    case "12" -> {
                        return ori;
                    }
                    default ->
                        System.out.println("Invalid option. Try again.");
                }
            } catch (StopOperationException ex) {
                System.out.println("Operation canceled!");
            }
        }
    }

    public ImmutableMap<String, IStudentCourseInfo> changeCourse(
            @NonNull ImmutableMap<String, IStudentCourseInfo> courses
    ) throws StopOperationException {
        return this.scih.changeCourse(courses);
    }

//    Prompt user to change manager
    public IManager getModifiedManager(@NonNull IManager ori) throws StopOperationException {
        while (true) {
//            Print options
            System.out.println("1. Change password\t\t6. Change Email");
            System.out.println("2. Change Legal First Name\t7. Change Phone");
            System.out.println("3. Change Legal Last Name\t8. Change Address");
            System.out.println("4. Change Date of Birth");
            System.out.println("5. Change Gender");
            System.out.println();
            System.out.println("9. Save\t10. Exit");
            System.out.print("Select an option(1-10): ");

//            Get the input
            String choice = scanner.nextLine().trim();

//            Execute
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
                    System.out.println("Error: Invalid option. Try again.");
            }
        }
    }

//    Ask user for PostCode
    private String getPostCode() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Post Code(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Post Code must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Post Code = '%s'\n", input);
            return input;
        }
    }

//    Ask user for Country
    private String getCountry() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Country(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: Country must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Country = '%s'\n", input);
            return input;
        }
    }

//    Ask user for State
    private String getState() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter State(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: State must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("State = '%s'\n", input);
            return input;
        }
    }

//    Ask user for City
    private String getCity() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter City(x for exit): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether it is blank or want to stop
            if (input.isBlank()) {
                System.out.println("Error: City must not be blank!");
                continue;
            } else if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("City = '%s'\n", input);
            return input;
        }
    }

//    Ask user for Suburb
    private String getSuburb() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Suburb(x for exit) (optional): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether user want to stop
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Suburb = '%s'\n", input);
            return input;
        }
    }

//    Ask user for StreetName
    private String getStreetName() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Street Name(x for exit) (optional): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether user want to stop
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Street Name = '%s'\n", input);
            return input;
        }
    }

//    Ask user for StreetNumber
    private String getStreetNumber() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Street Number(x for exit) (optional): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether user want to stop
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Street Number = '%s'\n", input);
            return input;
        }
    }

//    Ask user for Unit
    private String getUnit() throws StopOperationException {
        while (true) {
//            Ask for input
            System.out.print("Please enter Unit(x for exit) (optional): ");
//            Get the input
            String input = scanner.nextLine().trim();
//            Check whether user want to stop
            if ("x".equalsIgnoreCase(input)) {
                throw new StopOperationException();
            }

            System.out.printf("Unit = '%s'\n", input);
            return input;
        }
    }
}
