package com.example.assessment.gui.manager;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.gui.Helpers;
import com.example.assessment.gui.manager.course.ManageCoursePanel;
import com.example.assessment.gui.manager.person.ManagePersonPanel;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.CardLayout;
import java.awt.Component;
import java.io.Serial;
import javax.swing.JPanel;
import lombok.NonNull;

@CheckReturnValue
public final class ManagerMainPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final CardLayout cardLayout = new CardLayout();
    @NonNull
    private final ManagerMainSelectPanel msp;
    @NonNull
    private final ManageCoursePanel mcp;
    @NonNull
    private final ManagePersonPanel mpp;

    public ManagerMainPanel(@NonNull ICombinedBackend cb, @NonNull IPerson p) {
        this.msp = new ManagerMainSelectPanel((e) -> this.manageCourseAction(), (e) -> this.managePersonAction());
        this.mcp = new ManageCoursePanel(cb, (e) -> this.switchBack());
        this.mpp = new ManagePersonPanel(cb, (e) -> this.switchBack());

        this.setLayout(this.cardLayout);
        this.addPanel(msp);
        this.addPanel(mcp);
        this.addPanel(mpp);
    }

    public void addPanel(@NonNull Component comp) {
        Helpers.addPanel(this, comp);
    }

    public void manageCourseAction() {
        this.mcp.setup();
        this.cardLayout.show(this, Helpers.getObjectName(this.mcp));
    }

    public void managePersonAction() {
        this.mpp.setup();
        this.cardLayout.show(this, Helpers.getObjectName(this.mpp));
    }

    public void switchBack() {
        this.cardLayout.show(this, Helpers.getObjectName(this.msp));
    }
}
