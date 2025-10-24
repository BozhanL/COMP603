package com.example.assessment.gui.manager.person;

import com.example.assessment.backend.generic.IPersonBackend;
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
import java.awt.event.ActionListener;
import java.io.Serial;
import java.time.LocalDate;
import java.time.ZoneId;
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

@CheckReturnValue
public final class EditStudentPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final transient IPersonBackend personBackend;

//    --------------------------------------------------------------------
    @NonNull
    private final ArrayList<StudentCourseInfoRow> studentCourseInfoRow = new ArrayList<>();
    @NonNull
    private final JPanel scip = new JPanel();

//    --------------------------------------------------------------------
    @NonNull
    private final JLabel idLabel = new JLabel("ID*:");
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
    private final JLabel dateOfBirthLabel = new JLabel("Date Of Birth*:");
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

//    --------------------------------------------------------------------
    public EditStudentPanel(
            @NonNull IPersonBackend personBackend,
            @NonNull ActionListener goBackAction
    ) {
        this.personBackend = personBackend;

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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener((e) -> this.addCourse());
        buttonPanel.add(addCourseButton, c);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((e) -> this.savePerson());
        buttonPanel.add(saveButton, c);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(goBackAction);
        returnButton.addActionListener((e) -> this.cleanup());
        buttonPanel.add(returnButton, c);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 4;
        this.add(buttonPanel, c);
    }

    public void addCourse() {
        int index = this.studentCourseInfoRow.size();
        StudentCourseInfoRow row = new StudentCourseInfoRow(index, (e) -> this.deleteCourse(index));
        this.studentCourseInfoRow.add(row);
        this.refreshCourse();
    }

    public void deleteCourse(int index) {
        try {
            this.studentCourseInfoRow.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }

        this.refreshCourse();
    }

    public void refreshCourse() {
        this.scip.removeAll();

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        for (StudentCourseInfoRow r : this.studentCourseInfoRow) {
            this.scip.add(r, c);
            c.gridy++;
        }

        c.weighty = 1;
        this.scip.add(Box.createVerticalGlue(), c);

        this.scip.revalidate();
        this.scip.repaint();
    }

    public void setup(IStudent p) {
        this.cleanup();

        if (p != null) {
            this.idField.setText(p.getId());
            this.idField.setEditable(false);

            this.passwordField.setText(p.getPassword());
            this.legalFirstNameField.setText(p.getLegalFirstName());
            this.legalLastNameField.setText(p.getLegalLastName());
            this.dateOfBirthField.setText(p.getDateOfBirth().toString());
            this.genderField.setSelectedItem(p.getGender());
            this.emailField.setText(p.getEmail());
            this.phoneField.setText(p.getPhone());
            this.residencyField.setSelectedItem(p.getResidencyStatus());

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
                int index = this.studentCourseInfoRow.size();
                StudentCourseInfoRow row = new StudentCourseInfoRow(index, c, (e) -> this.deleteCourse(index));
                this.studentCourseInfoRow.add(row);
            }
            this.refreshCourse();
        }
    }

    private void savePerson() {
        String id = this.idField.getText().trim();
        String password = String.copyValueOf(this.passwordField.getPassword());

        if (id.isBlank()) {
            Helpers.showErrorMessage("Error: ID must not be blank!");
            return;
        } else if (password.isBlank()) {
            Helpers.showErrorMessage("Error: Password must not be blank!");
            return;
        }

        LocalDate dob;
        try {
            dob = LocalDate.parse(this.dateOfBirthField.getText().trim());
        } catch (DateTimeParseException e) {
            Helpers.showErrorMessage("Error: Invalid date of birth format!");
            return;
        }

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

        HashMap<String, IStudentCourseInfo> courses = new HashMap<>();
        for (StudentCourseInfoRow row : this.studentCourseInfoRow) {
            IStudentCourseInfo info = row.getStudentCourseInfo();
            if (info == null) {
                return;
            }

            courses.put(info.getCourseCode(), info);
        }

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

        this.personBackend.modifyPerson(p);

        Helpers.showMessage("Save", "Save success");
    }

    public void cleanup() {
        this.idField.setText("");
        this.idField.setEditable(true);

        this.passwordField.setText("");
        this.legalFirstNameField.setText("");
        this.legalLastNameField.setText("");
        this.dateOfBirthField.setText(LocalDate.now(ZoneId.systemDefault()).toString());
        this.genderField.setSelectedIndex(0);
        this.emailField.setText("");
        this.phoneField.setText("");
        this.residencyField.setSelectedIndex(0);

//            Set address to empty
        this.unitField.setText("");
        this.streetNumberField.setText("");
        this.streetNameField.setText("");
        this.suburbField.setText("");
        this.cityField.setText("");
        this.stateField.setText("");
        this.countryField.setText("");
        this.postCodeField.setText("");

        this.studentCourseInfoRow.clear();
        this.refreshCourse();
    }
}
