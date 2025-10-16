package com.example.assessment.gui.welcome;

import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import lombok.NonNull;

@CheckReturnValue
public final class LoginPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @NonNull
    GridBagLayout gridBagLayout = new GridBagLayout();

    @NonNull
    JLabel usernameLabel = new JLabel("Username:");
    @NonNull
    JTextField usernameField = new JTextField(30);

    @NonNull
    JLabel passwordLabel = new JLabel("Password:");
    @NonNull
    JPasswordField passwordField = new JPasswordField(30);

    @NonNull
    JButton login = new JButton("Login");

    public LoginPanel(@NonNull ActionListener loginAction) {
        this.login.addActionListener(loginAction);

        this.setLayout(this.gridBagLayout);
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        this.add(this.usernameLabel, c.clone());
        c.gridx = 1;
        c.gridy = 0;
        this.add(this.usernameField, c.clone());

        c.gridx = 0;
        c.gridy = 1;
        this.add(this.passwordLabel, c.clone());

        c.gridx = 1;
        c.gridy = 1;
        this.add(this.passwordField, c.clone());

        c.gridx = 1;
        c.gridy = 3;
        this.add(this.login, c);
    }

    public String getUsername() {
        return this.usernameField.getText();
    }

    public String getPassword() {
        return String.copyValueOf(this.passwordField.getPassword());
    }
}
