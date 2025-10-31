package com.example.assessment.gui.manager;

import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JPanel;
import lombok.NonNull;

// Panel for manager to select option
@CheckReturnValue
public final class ManagerMainSelectPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final GridBagLayout gridBagLayout = new GridBagLayout();
    @NonNull
    private final JButton manageCourseButton = new JButton("Manage Course");
    @NonNull
    private final JButton managePersonButton = new JButton("Manage Person");

    public ManagerMainSelectPanel(@NonNull ActionListener manageCourseAction, @NonNull ActionListener managePersonAction) {
//        Add action listener for press button event
        this.manageCourseButton.addActionListener(manageCourseAction);
        this.managePersonButton.addActionListener(managePersonAction);

//        Set layout
        this.setLayout(this.gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 30;
        c.ipady = 20;

//        Add buttons to panel
        c.gridx = 0;
        c.gridy = 0;
        this.add(this.manageCourseButton, c);
        c.gridy++;
        this.add(this.managePersonButton, c);
    }
}
