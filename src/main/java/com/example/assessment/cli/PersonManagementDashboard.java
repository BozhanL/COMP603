package com.example.assessment.cli;

import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDate;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CheckReturnValue
@AllArgsConstructor
public final class PersonManagementDashboard {

    @NonNull
    private static final Scanner scanner = new Scanner(System.in);
    @NonNull
    private final IPersonBackend personBackend;

    public void displayMenu() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" ->
                        addStudent();
                    case "2" ->
                        getStudent();
                    case "3" ->
                        modifyStudent();
                    case "4" ->
                        listStudent();
                    case "5" ->
                        deleteStudent();
                    case "6" ->
                        addManager();
                    case "7" ->
                        getManager();
                    case "8" ->
                        modifyManager();
                    case "9" ->
                        listManager();
                    case "10" ->
                        deleteManager();
                    case "11" -> {
                        return; // exit to previous menu
                    }
                    default ->
                        System.out.println("Invalid option. Try again.");
                }
            } catch (StopOperationException e) {
                System.out.println("Operation canceled!");
            }
        }
    }

    private void addStudent() throws StopOperationException {
        String id = PersonInputHandler.getId();
        String password = PersonInputHandler.getPassword();
        String legalFirstName = PersonInputHandler.getLegalFirstName();
        String legalLastName = PersonInputHandler.getLegalLastName();
        LocalDate dateOfBirth = PersonInputHandler.getDateOfBirth();
        Gender gender = PersonInputHandler.getGender();
        String email = PersonInputHandler.getEmail();
        String phone = PersonInputHandler.getPhone();
        IAddress address = PersonInputHandler.getAddress();
        Residency residencyStatus = PersonInputHandler.getResidencyStatus();

        IStudent student = IStudent.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus);

        boolean success = true;
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
                this.personBackend.setPerson(student);
            } catch (FileAlreadyExistsException e) {
                System.out.println("Error: Student already exist! returing to menu");
                return;
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                success = false;
                continue;
            }
            break;
        }

        if (!success) {
            System.out.println("Error: Unable to add student!");
        }
    }

    private void getStudent() throws StopOperationException {
    }

    private void modifyStudent() throws StopOperationException {
    }

    private void listStudent() throws StopOperationException {
    }

    private void deleteStudent() throws StopOperationException {
    }

    private void addManager() throws StopOperationException {
    }

    private void getManager() throws StopOperationException {
    }

    private void modifyManager() throws StopOperationException {
    }

    private void listManager() throws StopOperationException {
    }

    private void deleteManager() throws StopOperationException {
    }

    private static void printMenu() {
        System.out.println("[MANAGER DASHBOARD]");
        System.out.println("1. Add Student\t6. Add Manager");
        System.out.println("2. Get Student\t7. Get Manager");
        System.out.println("3. Modify Student\t8. Modify Manager");
        System.out.println("4. List Student\t9. List Manager");
        System.out.println("5. Delete Student\t10. Delete Manager");
        System.out.println("11. Exit");
        System.out.print("Select an option(1-11): ");
    }
}
