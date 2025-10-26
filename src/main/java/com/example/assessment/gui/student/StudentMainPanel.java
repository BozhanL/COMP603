package com.example.assessment.gui.student;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.enums.Gender;
import com.example.assessment.backend.types.enums.Residency;
import com.example.assessment.backend.types.interfaces.IAddress;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.example.assessment.gui.Helpers;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.Serial;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import lombok.NonNull;

// This is the main panel for student
@CheckReturnValue
public final class StudentMainPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final transient ICombinedBackend cb;

//    Course row
//    --------------------------------------------------------------------
    @NonNull
    private final ArrayList<StudentCourseInfoImmutableRow> studentCourseInfoRow = new ArrayList<>();
    @NonNull
    private final JPanel scip = new JPanel();

//    Basic information
//    --------------------------------------------------------------------
    @NonNull
    private final JLabel idLabel = new JLabel("ID:");
    @NonNull
    private final JTextField idField = new JTextField(10);

    @NonNull
    private final JLabel passwordLabel = new JLabel("Password*:");
    @NonNull
    private final JPasswordField passwordField = new JPasswordField(30);

    @NonNull
    private final JLabel legalFirstNameLabel = new JLabel("Legal First Name:");
    @NonNull
    private final JTextField legalFirstNameField = new JTextField(10);

    @NonNull
    private final JLabel legalLastNameLabel = new JLabel("Legal Last Name:");
    @NonNull
    private final JTextField legalLastNameField = new JTextField(10);

    @NonNull
    private final JLabel dateOfBirthLabel = new JLabel("Date Of Birth:");
    @NonNull
    private final JTextField dateOfBirthField = new JTextField(10);

    @NonNull
    private final JLabel genderLabel = new JLabel("Gender:");
    @NonNull
    private final JComboBox<Gender> genderField = new JComboBox<>(Gender.values());

    @NonNull
    private final JLabel emailLabel = new JLabel("Email:");
    @NonNull
    private final JTextField emailField = new JTextField(20);

    @NonNull
    private final JLabel phoneLabel = new JLabel("Phone:");
    @NonNull
    private final JTextField phoneField = new JTextField(10);

    @NonNull
    private final JLabel residencyLabel = new JLabel("Residency:");
    @NonNull
    private final JComboBox<Residency> residencyField = new JComboBox<>(Residency.values());

//    Address information
//    --------------------------------------------------------------------
    @NonNull
    private final JLabel unitLabel = new JLabel("Unit:");
    @NonNull
    private final JTextField unitField = new JTextField(5);

    @NonNull
    private final JLabel streetNumberLabel = new JLabel("Street Number:");
    @NonNull
    private final JTextField streetNumberField = new JTextField(5);

    @NonNull
    private final JLabel streetNameLabel = new JLabel("Street Name:");
    @NonNull
    private final JTextField streetNameField = new JTextField(5);

    @NonNull
    private final JLabel suburbLabel = new JLabel("Suburb:");
    @NonNull
    private final JTextField suburbField = new JTextField(5);

    @NonNull
    private final JLabel cityLabel = new JLabel("City:");
    @NonNull
    private final JTextField cityField = new JTextField(5);

    @NonNull
    private final JLabel stateLabel = new JLabel("State:");
    @NonNull
    private final JTextField stateField = new JTextField(5);

    @NonNull
    private final JLabel countryLabel = new JLabel("Country:");
    @NonNull
    private final JTextField countryField = new JTextField(5);

    @NonNull
    private final JLabel postCodeLabel = new JLabel("PostCode:");
    @NonNull
    private final JTextField postCodeField = new JTextField(5);

    public StudentMainPanel(
            @NonNull ICombinedBackend cb,
            @NonNull IStudent p
    ) {
        this.cb = cb;

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

        c.gridy = 0;
        c.gridx = 0;
        this.add(this.idLabel, c);
        c.gridx++;
        this.add(this.idField, c);
        c.gridx++;
        this.add(this.passwordLabel, c);
        c.gridx++;
        this.add(this.passwordField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.legalFirstNameLabel, c);
        c.gridx++;
        this.add(this.legalFirstNameField, c);
        c.gridx++;
        this.add(this.legalLastNameLabel, c);
        c.gridx++;
        this.add(this.legalLastNameField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.dateOfBirthLabel, c);
        c.gridx++;
        this.add(this.dateOfBirthField, c);
        c.gridx++;
        this.add(this.genderLabel, c);
        c.gridx++;
        this.add(this.genderField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.emailLabel, c);
        c.gridx++;
        this.add(this.emailField, c);
        c.gridx++;
        this.add(this.phoneLabel, c);
        c.gridx++;
        this.add(this.phoneField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.residencyLabel, c);
        c.gridx++;
        this.add(this.residencyField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.unitLabel, c);
        c.gridx++;
        this.add(this.unitField, c);
        c.gridx++;
        this.add(this.streetNumberLabel, c);
        c.gridx++;
        this.add(this.streetNumberField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.streetNameLabel, c);
        c.gridx++;
        this.add(this.streetNameField, c);
        c.gridx++;
        this.add(this.suburbLabel, c);
        c.gridx++;
        this.add(this.suburbField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.cityLabel, c);
        c.gridx++;
        this.add(this.cityField, c);
        c.gridx++;
        this.add(this.stateLabel, c);
        c.gridx++;
        this.add(this.stateField, c);

        c.gridy++;
        c.gridx = 0;
        this.add(this.countryLabel, c);
        c.gridx++;
        this.add(this.countryField, c);
        c.gridx++;
        this.add(this.postCodeLabel, c);
        c.gridx++;
        this.add(this.postCodeField, c);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 4;
        c.weighty = 1;
        this.scip.setLayout(new GridBagLayout());
        JScrollPane scipScrollPane = new JScrollPane(this.scip);
        scipScrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scroll
        this.add(scipScrollPane, c);
        c.weighty = 0;
        c.gridwidth = 0;

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((e) -> this.saveStudent());
        buttonPanel.add(saveButton, c);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 4;
        this.add(buttonPanel, c);

        this.setup(p);
    }

//    Refresh course list
    public void refreshCourse() {
//        Remove all row
        this.scip.removeAll();

//        Add course back
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        for (StudentCourseInfoImmutableRow r : this.studentCourseInfoRow) {
            this.scip.add(r, c);
            c.gridy++;
        }

//        Add placeholder
        c.weighty = 1;
        this.scip.add(Box.createVerticalGlue(), c);

//        Refresh
        this.scip.revalidate();
        this.scip.repaint();
    }

//    Update display data based on IStudent p
    public void setup(@NonNull IStudent p) {
        this.idField.setText(p.getId());
        this.idField.setEditable(false);

        this.passwordField.setText(p.getPassword());

        this.legalFirstNameField.setText(p.getLegalFirstName());
        this.legalFirstNameField.setEditable(false);
        this.legalLastNameField.setText(p.getLegalLastName());
        this.legalLastNameField.setEditable(false);

        this.dateOfBirthField.setText(p.getDateOfBirth().toString());
        this.dateOfBirthField.setEditable(false);

        this.genderField.setSelectedItem(p.getGender());
        this.emailField.setText(p.getEmail());
        this.phoneField.setText(p.getPhone());
        this.residencyField.setSelectedItem(p.getResidencyStatus());
        this.residencyField.setEnabled(false);

        IAddress a = p.getAddress();
        this.unitField.setText(a.getUnit());
        this.streetNumberField.setText(a.getStreetNumber());
        this.streetNameField.setText(a.getStreetName());
        this.suburbField.setText(a.getSuburb());
        this.cityField.setText(a.getCity());
        this.stateField.setText(a.getState());
        this.countryField.setText(a.getCountry());
        this.postCodeField.setText(a.getPostCode());

        ImmutableMap<String, IStudentCourseInfo> courses = p.getCourses();
        for (IStudentCourseInfo c : courses.values()) {
            StudentCourseInfoImmutableRow row = new StudentCourseInfoImmutableRow(c);
            this.studentCourseInfoRow.add(row);
        }
        this.refreshCourse();
    }

//    Save the student to database
    private void saveStudent() {
//        Get ID and password
        String id = this.idField.getText().trim();
        String password = String.copyValueOf(this.passwordField.getPassword());

//        Check password
//        Password must not be blank
        if (password.isBlank()) {
            Helpers.showErrorMessage("Error: Password must not be blank!");
            return;
        }

//        Parse date of birth
        LocalDate dob;
        try {
            dob = LocalDate.parse(this.dateOfBirthField.getText().trim());
        } catch (DateTimeParseException e) {
            Helpers.showErrorMessage("Error: Invalid date of birth format!");
            return;
        }

//        Construct address
        IAddress address = IAddress.of(
                this.unitField.getText().trim(),
                this.streetNumberField.getText().trim(),
                this.streetNameField.getText().trim(),
                this.suburbField.getText().trim(),
                this.cityField.getText().trim(),
                this.stateField.getText().trim(),
                this.countryField.getText().trim(),
                this.postCodeField.getText().trim()
        );

//        Construct student course info
        HashMap<String, IStudentCourseInfo> courses = new HashMap<>();
        for (StudentCourseInfoImmutableRow row : this.studentCourseInfoRow) {
            IStudentCourseInfo info = row.getStudentCourseInfo();
            if (info == null) {
                return;
            }

            courses.put(info.getCourseCode(), info);
        }

//        Constuct student
        IStudent p = IStudent.of(
                id,
                password,
                this.legalFirstNameField.getText().trim(),
                this.legalLastNameField.getText().trim(),
                dob,
                this.genderField.getItemAt(this.genderField.getSelectedIndex()),
                this.emailField.getText().trim(),
                this.phoneField.getText().trim(),
                address,
                this.residencyField.getItemAt(this.residencyField.getSelectedIndex()),
                ImmutableMap.copyOf(courses)
        );

//        Save to database
        this.cb.modifyPerson(p);

//        Show success message
        Helpers.showMessage("Save", "Save success");
    }
}
