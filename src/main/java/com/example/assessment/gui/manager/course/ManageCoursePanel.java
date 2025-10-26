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
import java.util.HashSet;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import lombok.NonNull;

@CheckReturnValue
public final class ManageCoursePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final transient ICourseBackend courseBackend;

    @NonNull
    private final HashSet<ICourseCode> deletedCourse = new HashSet<>();

    @NonNull
    private final ArrayList<CourseRow> courseRow = new ArrayList<>();
    @NonNull
    private final JPanel coursePanel = new JPanel();

    public ManageCoursePanel(@NonNull ICourseBackend courseBackend, @NonNull ActionListener goBackAction) {
        this.courseBackend = courseBackend;

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;

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

    public void refreshCourse() {
        this.coursePanel.removeAll();

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        int index = 0;
        for (CourseRow r : this.courseRow) {
            r.setIndex(index);
            this.coursePanel.add(r, c);
            c.gridy++;
            index++;
        }

        c.weighty = 1;
        this.coursePanel.add(Box.createVerticalGlue(), c);

        this.coursePanel.revalidate();
        this.coursePanel.repaint();
    }

    public void addCourse() {
        int index = this.courseRow.size();
        CourseRow cr = new CourseRow(index, this::deleteCourse);
        this.courseRow.add(cr);
        this.refreshCourse();
    }

    public void saveCourses() {
        HashMap<ICourseCode, ICourse> courses = new HashMap<>();

        for (CourseRow cr : this.courseRow) {
            ICourse c = cr.getCourse();
            if (c == null) {
                return;
            }

            ICourse prev = courses.put(c.getCode(), c);
            if (prev != null) {
                Helpers.showErrorMessage("Error: Duplicate Course Code found! (index: %d)", cr.getIndex());
                return;
            }
        }

        this.deletedCourse.stream().forEach((c) -> {
            this.courseBackend.deleteCourseByCode(c);
        });
        courses.values().stream().forEach(this.courseBackend::modifyCourse);
        Helpers.showMessage("Save", "Save success");
    }

    public void deleteCourse(ActionEvent e) {
        try {
            int index = Integer.parseInt(e.getActionCommand());
            CourseRow cr = this.courseRow.remove(index);
            ICourse c = cr.getCourse();
            this.deletedCourse.add(c.getCode());
        } catch (IndexOutOfBoundsException | NumberFormatException ex) {
            return;
        }

        this.refreshCourse();
    }

    public void setup() {

        ImmutableList<ICourse> courses = this.courseBackend.listCourse();
        for (ICourse c : courses) {
            int index = this.courseRow.size();
            CourseRow cr = new CourseRow(index, c, this::deleteCourse);
            this.courseRow.add(cr);
        }
        this.refreshCourse();
    }

    public void cleanup() {
        this.courseRow.clear();
        this.refreshCourse();
    }
}
