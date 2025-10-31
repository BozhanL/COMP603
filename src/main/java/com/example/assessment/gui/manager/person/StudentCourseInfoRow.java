package com.example.assessment.gui.manager.person;

import com.example.assessment.backend.types.enums.Grade;
import com.example.assessment.backend.types.interfaces.IStudentCourseInfo;
import com.example.assessment.gui.Helpers;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Getter;
import lombok.NonNull;

// This class represent a StudentCourseInfo in row format
@CheckReturnValue
public final class StudentCourseInfoRow extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private int index;

    @NonNull
    private final JTextField courseCode = new JTextField(7);
    @NonNull
    private final JComboBox<Grade> grade = new JComboBox<>(Grade.values());
    @NonNull
    private final JTextField starts = new JTextField(10);
    @NonNull
    private final JTextField location = new JTextField(5);
    @NonNull
    private final JButton deleteCourseButton = new JButton("Delete Course");

    public StudentCourseInfoRow(int index, @NonNull ActionListener deleteThis) {
        this(index, IStudentCourseInfo.of("", Grade.NA, LocalDate.now(ZoneId.systemDefault()), ""), deleteThis);
    }

    public StudentCourseInfoRow(int index, @NonNull IStudentCourseInfo course, @NonNull ActionListener deleteThis) {
        this.index = index;
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

        c.gridy = 0;
        c.gridx = 0;
        this.add(new JLabel("Course Code*:"), c);
        c.gridx++;
        this.courseCode.setText(course.getCourseCode());
        this.add(this.courseCode, c);
        c.gridx++;
        this.add(new JLabel("Grade:"), c);
        c.gridx++;
        this.grade.setSelectedItem(course.getGrade());
        this.add(this.grade, c);

        c.gridy++;
        c.gridx = 0;
        this.add(new JLabel("Starts at*:"), c);
        c.gridx++;
        this.starts.setText(course.getStarts().toString());
        this.add(this.starts, c);
        c.gridx++;
        this.add(new JLabel("Location:"), c);
        c.gridx++;
        this.location.setText(course.getLocation());
        this.add(this.location, c);

        c.gridy = 0;
        c.gridx = 4;
        c.gridheight = 2;
        this.deleteCourseButton.addActionListener(deleteThis);
//        Set action command to index
        this.deleteCourseButton.setActionCommand(Integer.toString(this.index));
        this.add(this.deleteCourseButton, c);
    }

//    Update the index
    public void setIndex(int index) {
        this.index = index;
        this.deleteCourseButton.setActionCommand(Integer.toString(this.index));
    }

//    Convert the data into IStudentCourseInfo
    public IStudentCourseInfo getStudentCourseInfo() {
//        Get the course code
        String code = this.courseCode.getText().trim();
//        Code must not be blank
        if (code.isBlank()) {
            Helpers.showErrorMessage("Error: Course Code must not be blank! (index: %d)", this.index);
            return null;
        }

//        Parse the date
        LocalDate date;
        try {
            date = LocalDate.parse(this.starts.getText().trim());
        } catch (DateTimeParseException e) {
            Helpers.showErrorMessage("Error: Invalid starts at format! (index: %d)", this.index);
            return null;
        }

//        Construct the object
        return IStudentCourseInfo.of(
                code,
                this.grade.getItemAt(this.grade.getSelectedIndex()),
                date,
                this.location.getText().trim()
        );
    }
}
