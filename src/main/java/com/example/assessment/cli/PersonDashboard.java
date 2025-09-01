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
import java.util.Scanner;
import lombok.AllArgsConstructor;
import lombok.NonNull;

// This is the dashboard to manage person in the system
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
//            Print options
            printMenu();
//            Get the input
            String choice = scanner.nextLine().trim();

//            Execute
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

//    Add a new student to database
    private void addStudent() throws StopOperationException {
//        Get student information
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

//        Create a new one
        IStudent student = IStudent.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address, residencyStatus);

        boolean success = true;
//        Max retry is 3
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
//                Store the person
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

//    print a student's information by id
    private void getStudent() throws StopOperationException {
        while (true) {
//            Get the id of student
            String id = personInputHandler.getId();

            IStudent st;
            try {
//                Get it from database
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

//            print the student's information
            System.out.println(st.prettyToString());
            return;
        }
    }

//    Change a studnet's information
    private void modifyStudent() throws StopOperationException {
        while (true) {
//            Get the id of student
            String id = personInputHandler.getId();

            IStudent st;
            try {
//                Get it from database
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

//            Get the modified one
            st = personInputHandler.getModifiedStudent(st);

            boolean success = true;
//            Max retry is 3
            for (int i = 0; i < 3; i++) {
                success = true;
                try {
//                    Store it to database
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

//    List all students in the database
    private void listStudent() {
        ImmutableList<IStudent> li = null;

        boolean success = true;
//        Max retry is 3
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
//                Get the student from backend
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
        if (!success || li == null) {
            System.out.println("Error: Unable to list student!");
            return;
        }

//        print the students one by one
        for (IStudent s : li) {
            System.out.println(s.prettyToString());
            System.out.println("------------------------------------------------");
        }
    }

//    Delete a student from database
    private void deleteStudent() throws StopOperationException {
//            Get the id of student
        String id = personInputHandler.getId();
        try {
//            Delete the student
            this.personBackend.deletePersonById(id);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

//    Add a manager to the database
    private void addManager() throws StopOperationException {
//        Get manager information
        String id = personInputHandler.getId();
        String password = personInputHandler.getPassword();
        String legalFirstName = personInputHandler.getLegalFirstName();
        String legalLastName = personInputHandler.getLegalLastName();
        LocalDate dateOfBirth = personInputHandler.getDateOfBirth();
        Gender gender = personInputHandler.getGender();
        String email = personInputHandler.getEmail();
        String phone = personInputHandler.getPhone();
        IAddress address = personInputHandler.getAddress();

//        Create a new one
        IManager ma = IManager.of(id, password, legalFirstName, legalLastName, dateOfBirth, gender, email, phone, address);

        boolean success = true;
//        Max retry is 3
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
//                Store the person
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

//    print a manager's information by id
    private void getManager() throws StopOperationException {
        while (true) {
//            Get the id of manager
            String id = personInputHandler.getId();

            IManager ma;
            try {
//                Get it from database
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

//            print the manager's information
            System.out.println(ma.prettyToString());
            return;
        }
    }

//    Change a manager's information
    private void modifyManager() throws StopOperationException {
        while (true) {
//            Get the id of manager
            String id = personInputHandler.getId();

            IManager ma;
            try {
//                Get it from database
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

//            Get the modified one
            ma = personInputHandler.getModifiedManager(ma);

            boolean success = true;
//            Max retry is 3
            for (int i = 0; i < 3; i++) {
                success = true;
                try {
//                    Store it to database
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

//    List all manager in the database
    private void listManager() {
        ImmutableList<IManager> li = null;

        boolean success = true;
//        Max retry is 3
        for (int i = 0; i < 3; i++) {
            success = true;
            try {
//                Get the manager from backend
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
        if (!success || li == null) {
            System.out.println("Error: Unable to list student!");
            return;
        }

//        print the managers one by one
        for (IManager ma : li) {
            System.out.println(ma.prettyToString());
            System.out.println("------------------------------------------------");
        }
    }

//    Delete a manager from database
    private void deleteManager() throws StopOperationException {
//            Get the id of manager
        String id = personInputHandler.getId();
        try {
//            Delete the manager
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
