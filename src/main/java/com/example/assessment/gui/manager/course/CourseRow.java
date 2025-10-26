package com.example.assessment.gui.manager.course;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.example.assessment.gui.Helpers;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import lombok.Getter;
import lombok.NonNull;

// This class represent a course in row format
@CheckReturnValue
public final class CourseRow extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

//    position index
    @Getter
    private int index;

//    Fields
    @NonNull
    private final JTextField courseCode = new JTextField(7);
    @NonNull
    private final JTextField courseName = new JTextField(7);
    @NonNull
    private final JTextField coursePoints = new JTextField(3);
    @NonNull
    private final JTextField courseDescription = new JTextField(15);
    @NonNull
    private final JButton deleteCourseButton = new JButton("Delete Course");

    public CourseRow(int index, @NonNull ActionListener deleteThis) {
        this(index, ICourse.of("COMP", 5, 9, "", 0, ""), deleteThis);
    }

    public CourseRow(int index, @NonNull ICourse course, @NonNull ActionListener deleteThis) {
//        Init index
        this.index = index;

//        Set layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

//        Add fields
        c.gridy = 0;
        c.gridx = 0;
        this.add(new JLabel("Course Code*:"), c);
        c.gridx++;
        this.courseCode.setText(course.getCode().toString());
        this.add(this.courseCode, c);

        c.gridx++;
        this.add(new JLabel("Name:"), c);
        c.gridx++;
        this.courseName.setText(course.getName());
        this.add(this.courseName, c);

        c.gridx++;
        this.add(new JLabel("Points*:"), c);
        c.gridx++;
        this.coursePoints.setText(Integer.toString(course.getPoints()));
        this.add(this.coursePoints, c);

        c.gridy++;
        c.gridx = 0;
        this.add(new JLabel("Description*:"), c);
        c.gridx++;
        c.gridwidth = 5;
        this.courseDescription.setText(course.getDescription());
        this.add(this.courseDescription, c);
        c.gridwidth = 1;

        c.gridy = 0;
        c.gridx = 8;
        c.gridheight = 2;
//        Set action listener for delete course button
        this.deleteCourseButton.addActionListener(deleteThis);
//        Set command to current index
        this.deleteCourseButton.setActionCommand(Integer.toString(this.index));
        this.add(this.deleteCourseButton, c);
    }

    public void setIndex(int index) {
        this.index = index;
//        Set command to current index
        this.deleteCourseButton.setActionCommand(Integer.toString(this.index));
    }

//    Get the course
//    Return null if invalid
    public ICourse getCourse() {
        String strCode = this.courseCode.getText().trim();
        ICourseCode code;
        try {
            code = ICourseCode.of(strCode);
        } catch (ParseException e) {
            Helpers.showErrorMessage("Error: Wrong Course Code! (index: %d)", this.index);
            return null;
        }

        int point;
        try {
            point = Integer.parseInt(this.coursePoints.getText());
        } catch (NumberFormatException e) {
            Helpers.showErrorMessage("Error: Points must be non-negative integer! (index: %d)", this.index);
            return null;
        }
        if (point < 0) {
            Helpers.showErrorMessage("Error: Points must be non-negative integer! (index: %d)", this.index);
            return null;
        }

        return ICourse.of(
                code,
                this.courseName.getText(),
                point,
                this.courseDescription.getText()
        );
    }
}
