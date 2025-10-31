package com.example.assessment.gui.manager;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.gui.Helpers;
import com.example.assessment.gui.manager.course.ManageCoursePanel;
import com.example.assessment.gui.manager.person.ManagePersonControlPanel;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.CardLayout;
import java.awt.Component;
import java.io.Serial;
import javax.swing.JPanel;
import lombok.NonNull;

// Main panel for manager
@CheckReturnValue
public final class ManagerMainControlPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final CardLayout cardLayout = new CardLayout();
    @NonNull
    private final ManagerMainSelectPanel msp;
    @NonNull
    private final ManageCoursePanel mcp;
    @NonNull
    private final ManagePersonControlPanel mpp;

    public ManagerMainControlPanel(@NonNull ICombinedBackend cb, @NonNull IManager p) {
//        Create sub panels
        this.msp = new ManagerMainSelectPanel((e) -> this.manageCourseAction(), (e) -> this.managePersonAction());
        this.mcp = new ManageCoursePanel(cb, (e) -> this.switchBack());
        this.mpp = new ManagePersonControlPanel(cb, (e) -> this.switchBack());

//        Add them to card layout
        this.setLayout(this.cardLayout);
        this.addPanel(msp);
        this.addPanel(mcp);
        this.addPanel(mpp);
    }

    public void addPanel(@NonNull Component comp) {
        Helpers.addPanel(this, comp);
    }

//    Switch to manage course panel
    public void manageCourseAction() {
        this.mcp.setup();
        this.cardLayout.show(this, Helpers.getObjectName(this.mcp));
    }

//    Switch to manage person panel
    public void managePersonAction() {
        this.mpp.setup();
        this.cardLayout.show(this, Helpers.getObjectName(this.mpp));
    }

//    Switch to select panel
    public void switchBack() {
        this.cardLayout.show(this, Helpers.getObjectName(this.msp));
    }
}
