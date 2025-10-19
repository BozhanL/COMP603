package com.example.assessment.gui.manager;

import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JPanel;
import lombok.NonNull;

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
        this.manageCourseButton.addActionListener(manageCourseAction);
        this.managePersonButton.addActionListener(managePersonAction);
        this.setLayout(this.gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 30;
        c.ipady = 20;
        c.gridx = 0;
        c.gridy = 0;
        this.add(this.manageCourseButton, c);
        c.gridy = 1;
        this.add(this.managePersonButton, c);
    }
}
