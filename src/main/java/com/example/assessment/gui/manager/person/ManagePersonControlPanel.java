package com.example.assessment.gui.manager.person;

import com.example.assessment.backend.generic.IPersonBackend;
import com.example.assessment.backend.types.interfaces.IManager;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.backend.types.interfaces.IStudent;
import com.example.assessment.gui.Helpers;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.JPanel;
import lombok.NonNull;

//  Main panel for manage person feature
@CheckReturnValue
public final class ManagePersonControlPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final CardLayout cardLayout = new CardLayout();

    @NonNull
    private final ListPersonPanel listPerson;
    @NonNull
    private final EditManagerPanel editManagerPanel;
    @NonNull
    private final EditStudentPanel editStudentPanel;

    @NonNull
    private final transient IPersonBackend personBackend;

    public ManagePersonControlPanel(
            @NonNull IPersonBackend personBackend,
            @NonNull ActionListener goBackAction
    ) {
//        Init variables
        this.personBackend = personBackend;

        this.listPerson = new ListPersonPanel(personBackend, (e) -> this.selectButtonAction(e), goBackAction);
        this.editManagerPanel = new EditManagerPanel(personBackend, (e) -> this.switchBack());
        this.editStudentPanel = new EditStudentPanel(personBackend, (e) -> this.switchBack());

//        Set layout and add panels
        this.setLayout(this.cardLayout);
        this.addPanel(this.listPerson);
        this.addPanel(this.editManagerPanel);
        this.addPanel(this.editStudentPanel);
    }

//    Button press action
    public void selectButtonAction(@NonNull ActionEvent e) {
//        Get pressed button enum
        String act = e.getActionCommand();
        Buttons b = Buttons.valueOf(act);

        switch (b) {
            case ADD_MANAGER -> {
//                Switch to edit manager panel with null as manager
                this.editManagerPanel.setup(null);
                this.cardLayout.show(this, Helpers.getObjectName(this.editManagerPanel));
            }

            case ADD_STUDENT -> {
//                Switch to edit manager panel with null as manager
                this.editStudentPanel.setup(null);
                this.cardLayout.show(this, Helpers.getObjectName(this.editStudentPanel));
            }

            case MODIFY -> {
//                Get the selected person
                IPerson p = this.listPerson.getSelectedPerson();
//                Do nothing if no person being selected
                if (p == null) {
                    return;
                }

                switch (p.getType()) {
                    case MANAGER -> {
//                          Switch to edit manager panel with selected value as manager
                        this.editManagerPanel.setup((IManager) p);
                        this.cardLayout.show(this, Helpers.getObjectName(this.editManagerPanel));
                    }
                    case STUDENT -> {
//                          Switch to edit student panel with selected value as manager
                        this.editStudentPanel.setup((IStudent) p);
                        this.cardLayout.show(this, Helpers.getObjectName(this.editStudentPanel));
                    }
                }
            }

            case DELETE -> {
//                Get the selected person
                IPerson p = this.listPerson.getSelectedPerson();
//                Do nothing if no person being selected
                if (p == null) {
                    return;
                }

//                Delete the person
                this.personBackend.deletePersonById(p.getId());
//                Refresh the list
                this.listPerson.setup();
            }
        }
    }

//    Switch back to list person panel
    public void switchBack() {
        this.listPerson.setup();
        this.cardLayout.show(this, Helpers.getObjectName(this.listPerson));
    }

//    Set up list person panel
    public void setup() {
        this.listPerson.setup();
    }

    public void addPanel(@NonNull Component comp) {
        Helpers.addPanel(this, comp);
    }
}
