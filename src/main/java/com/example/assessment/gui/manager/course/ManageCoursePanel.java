package com.example.assessment.gui.manager.course;

import com.example.assessment.backend.generic.ICourseBackend;
import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.ICourseCode;
import com.example.assessment.gui.Helpers;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import lombok.NonNull;

// This is the panel for manage course
@CheckReturnValue
public final class ManageCoursePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final transient ICourseBackend courseBackend;

    @NonNull
    private final ArrayList<CourseRow> courseRow = new ArrayList<>();
    @NonNull
    private final JPanel coursePanel = new JPanel();

    public ManageCoursePanel(@NonNull ICourseBackend courseBackend, @NonNull ActionListener goBackAction) {
//        Init courseBackend
        this.courseBackend = courseBackend;

//        Set layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

//        Add fields
        c.gridy = 0;
        c.gridx = 0;
        c.weighty = 1;
        this.coursePanel.setLayout(new GridBagLayout());
        JScrollPane scipScrollPane = new JScrollPane(this.coursePanel);
        scipScrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scroll
        this.add(scipScrollPane, c);
        c.weighty = 0;
        c.gridwidth = 0;

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener((e) -> this.addCourse());
        buttonPanel.add(addCourseButton, c);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((e) -> this.saveCourses());
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

//    Refresh course list
    public void refreshCourse() {
//        Empty course panel
        this.coursePanel.removeAll();

//        Add courses back
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        int index = 0;
        for (CourseRow r : this.courseRow) {
//            Update index
            r.setIndex(index);
            this.coursePanel.add(r, c);
            c.gridy++;
            index++;
        }

//        Add placeholder
        c.weighty = 1;
        this.coursePanel.add(Box.createVerticalGlue(), c);

//        Refresh
        this.coursePanel.revalidate();
        this.coursePanel.repaint();
    }

//    Add an empty course
    public void addCourse() {
//        Get index
        int index = this.courseRow.size();

//        Create new row
        CourseRow cr = new CourseRow(index, this::deleteCourse);

//        Add to list
        this.courseRow.add(cr);

//        Refresh
        this.refreshCourse();
    }

//    Save the courses to backend
    public void saveCourses() {
        HashMap<ICourseCode, ICourse> courses = new HashMap<>();

//        Add course to Map
        for (CourseRow cr : this.courseRow) {
//            Get course
            ICourse c = cr.getCourse();
//            Stop if encounter any error
            if (c == null) {
                return;
            }

//            Add to Map
            ICourse prev = courses.put(c.getCode(), c);
//            Show error message and cancel if duplicate course code found
            if (prev != null) {
                Helpers.showErrorMessage("Error: Duplicate Course Code found! (index: %d)", cr.getIndex());
                return;
            }
        }

//        Add courses to backend
        courses.values().stream().forEach(this.courseBackend::modifyCourse);

//        Remove courses that is not in the Map
//        Ensure backend only contains what user can see
        this.courseBackend.listCourse().stream()
                .map(ICourse::getCode)
                .filter((c) -> !courses.containsKey(c))
                .forEach(this.courseBackend::deleteCourseByCode);

//        Show success message
        Helpers.showMessage("Save", "Save success");
    }

//    Delete a course action
    public void deleteCourse(@NonNull ActionEvent e) {
        try {
//            Get the index
            int index = Integer.parseInt(e.getActionCommand());
//            Remove it from list
            this.courseRow.remove(index);
        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
            return;
        }

//        Refresh
        this.refreshCourse();
    }

//    Set up content(course), update course
    public void setup() {
//        List all course
        ImmutableList<ICourse> courses = this.courseBackend.listCourse();

//        Add to course row
        for (ICourse c : courses) {
            int index = this.courseRow.size();
            CourseRow cr = new CourseRow(index, c, this::deleteCourse);
            this.courseRow.add(cr);
        }

//        Refresh
        this.refreshCourse();
    }

//    Empty course list
    public void cleanup() {
        this.courseRow.clear();
        this.refreshCourse();
    }
}
