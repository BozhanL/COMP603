package com.example.assessment.gui.welcome;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.gui.Helpers;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.CardLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.NonNull;

// Main panel to control select database and login flow
@CheckReturnValue
public final class WelcomeControlPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    private final CardLayout cardLayout = new CardLayout();
    @NonNull
    private final LoginPanel lp;
    @NonNull
    private final DatabaseSelectionPanel dsp;

    @Getter
    private transient ICombinedBackend combinedBackend;
    @Getter
    private IPerson person;

    @NonNull
    private final transient Runnable onLoginSuccess;

    public WelcomeControlPanel(@NonNull Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
        this.dsp = new DatabaseSelectionPanel((e) -> this.openDatabase());
        this.lp = new LoginPanel((e) -> this.login());

        this.setLayout(this.cardLayout);
        this.addPanel(dsp);
        this.addPanel(lp);
    }

//    Open database action
    public void openDatabase() {
//        Get the location
        File f = this.dsp.getFile();

//        Try to open the database, show error message when error
        try {
            this.combinedBackend = ICombinedBackend.of(f.toPath());
        } catch (IOException ex) {
            Helpers.showErrorMessage("IO error, please select another empty folder");
            return;
        } catch (DatabaseCorruptedException ex) {
            Helpers.showErrorMessage("Database corrupted, please select another database");
            return;
        } catch (Exception ex) {
            Helpers.showErrorMessage(ex);
            return;
        }
        if (this.combinedBackend == null) {
            Helpers.showErrorMessage("Unknown Error, unable to open database");
            return;
        }

//        If no error, show login page
        this.cardLayout.show(this, Helpers.getObjectName(this.lp));
    }

//    login action
    public void login() {
//        Get the entered username and password
        String username = this.lp.getUsername();
        String password = this.lp.getPassword();

//        Get the person based on username
        this.person = this.combinedBackend.getPersonById(username);

//        If it is null or password is wrong, show error message and return
        if (this.person == null || !this.person.safeCheckPassword(password)) {
            Helpers.showErrorMessage("Username or Password incorrect");
            return;
        }

//        Run the login success callback
        this.onLoginSuccess.run();
    }

    public void addPanel(@NonNull Component comp) {
        Helpers.addPanel(this, comp);
    }
}
