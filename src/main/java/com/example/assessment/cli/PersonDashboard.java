package com.example.assessment.cli;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CheckReturnValue
@AllArgsConstructor
public final class PersonDashboard implements IDashboard {

    @NonNull
    private final Scanner scanner;
    @NonNull
    private final IPersonBackend personBackend;
    @NonNull
    private final PersonInputHandler personInputHandler;

    public PersonDashboard(@NonNull Scanner scanner, @NonNull IPersonBackend personBackend) {
        this(scanner, personBackend, new PersonInputHandler(scanner));
    }

    @Override
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
        String id = personInputHandler.getId();
        String password = personInputHandler.getPassword();
        String legalFirstName = personInputHandler.getLegalFirstName();
        String legalLastName = personInputHandler.getLegalLastName();
        LocalDate dateOfBirth = personInputHandler.getDateOfBirth();
        Gender gender = personInputHandler.getGender();
        String email = personInputHandler.getEmail();
        String phone = personInputHandler.getPhone();
        IAddress address = personInputHandler.getAddress();
        Residency residencyStatus = personInputHandler.getResidencyStatus();

        IStudent student = IStudent.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus);

        boolean success = true;
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
                this.personBackend.setPerson(student);
                break;
            } catch (FileAlreadyExistsException e) {
                System.out.println("Error: Student already exist! returing to menu");
                return;
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                success = false;
            }
        }

        if (!success) {
            System.out.println("Error: Unable to add student!");
        }
    }

    private void getStudent() throws StopOperationException {
        while (true) {
            String id = personInputHandler.getId();

            IStudent st;
            try {
                st = this.personBackend.getStudentById(id);
            } catch (FileNotFoundException ex) {
                System.out.println("Error: user does not exist");
                continue;
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                continue;
            } catch (DatabaseCorruptedException ex) {
                System.out.println("Error: data corrupted, try another user");
                continue;
            }

            System.out.println(st.prettyToString());
            return;
        }
    }

    private void modifyStudent() throws StopOperationException {
        while (true) {
            String id = personInputHandler.getId();

            IStudent st;
            try {
                st = this.personBackend.getStudentById(id);
            } catch (FileNotFoundException ex) {
                System.out.println("Error: user does not exist");
                continue;
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                continue;
            } catch (DatabaseCorruptedException ex) {
                System.out.println("Error: data corrupted, try another user");
                continue;
            }

            st = personInputHandler.getModifiedStudent(st);
            boolean success = true;
            for (int i = 0; i < 3; i++) {
                success = true;
                try {
                    this.personBackend.modifyPerson(st);
                    break;
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    success = false;
                }
            }

            if (!success) {
                System.out.println("Error: Unable to modify student!");
            }
            break;
        }
    }

    private void listStudent() {
        ImmutableList<IStudent> li = null;

        boolean success = true;
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
                li = this.personBackend.listStudent();
                break;
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                success = false;
            } catch (DatabaseCorruptedException ex) {
                System.out.println("Error: data corrupted, aborty");
                return;
            }
        }
        if (!success || Objects.isNull(li)) {
            System.out.println("Error: Unable to list student!");
            return;
        }

        for (IStudent s : li) {
            System.out.println(s.prettyToString());
            System.out.println("------------------------------------------------");
        }
    }

    private void deleteStudent() throws StopOperationException {
        String id = personInputHandler.getId();
        try {
            this.personBackend.deletePersonById(id);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void addManager() throws StopOperationException {
        String id = personInputHandler.getId();
        String password = personInputHandler.getPassword();
        String legalFirstName = personInputHandler.getLegalFirstName();
        String legalLastName = personInputHandler.getLegalLastName();
        LocalDate dateOfBirth = personInputHandler.getDateOfBirth();
        Gender gender = personInputHandler.getGender();
        String email = personInputHandler.getEmail();
        String phone = personInputHandler.getPhone();
        IAddress address = personInputHandler.getAddress();

        IManager ma = IManager.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);

        boolean success = true;
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
                this.personBackend.setPerson(ma);
                break;
            } catch (FileAlreadyExistsException e) {
                System.out.println("Error: Student already exist! returing to menu");
                return;
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                success = false;
            }
        }

        if (!success) {
            System.out.println("Error: Unable to add student!");
        }
    }

    private void getManager() throws StopOperationException {
        while (true) {
            String id = personInputHandler.getId();

            IManager ma;
            try {
                ma = this.personBackend.getManagerById(id);
            } catch (FileNotFoundException ex) {
                System.out.println("Error: user does not exist");
                continue;
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                continue;
            } catch (DatabaseCorruptedException ex) {
                System.out.println("Error: data corrupted, try another user");
                continue;
            }

            System.out.println(ma.prettyToString());
            return;
        }
    }

    private void modifyManager() throws StopOperationException {
        while (true) {
            String id = personInputHandler.getId();

            IManager ma;
            try {
                ma = this.personBackend.getManagerById(id);
            } catch (FileNotFoundException ex) {
                System.out.println("Error: user does not exist");
                continue;
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                continue;
            } catch (DatabaseCorruptedException ex) {
                System.out.println("Error: data corrupted, try another user");
                continue;
            }

            ma = personInputHandler.getModifiedManager(ma);
            boolean success = true;
            for (int i = 0; i < 3; i++) {
                success = true;
                try {
                    this.personBackend.modifyPerson(ma);
                    break;
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    success = false;
                }
            }

            if (!success) {
                System.out.println("Error: Unable to modify manager!");
            }
            break;
        }
    }

    private void listManager() {
        ImmutableList<IManager> li = null;

        boolean success = true;
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
                li = this.personBackend.listManager();
                break;
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
                success = false;
            } catch (DatabaseCorruptedException ex) {
                System.out.println("Error: data corrupted, aborty");
                return;
            }
        }
        if (!success || Objects.isNull(li)) {
            System.out.println("Error: Unable to list student!");
            return;
        }

        for (IManager ma : li) {
            System.out.println(ma.prettyToString());
            System.out.println("------------------------------------------------");
        }
    }

    private void deleteManager() throws StopOperationException {
        String id = personInputHandler.getId();
        try {
            this.personBackend.deletePersonById(id);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("[EDIT PERSON DASHBOARD]");
        System.out.println("1. Add Student\t6. Add Manager");
        System.out.println("2. Get Student\t7. Get Manager");
        System.out.println("3. Modify Student\t8. Modify Manager");
        System.out.println("4. List Student\t9. List Manager");
        System.out.println("5. Delete Student\t10. Delete Manager");
        System.out.println("11. Exit");
        System.out.print("Select an option(1-11): ");
    }
}
