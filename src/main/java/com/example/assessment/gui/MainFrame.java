package com.example.assessment.gui;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.gui.welcome.WelcomePanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.io.Serial;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lombok.NonNull;

// The main and only frame for GUI
public final class MainFrame extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

//    Default size
    private static final Dimension FRAME_SIZE = new Dimension(800, 600);

    @NonNull
    private final CardLayout cardLayout = new CardLayout();
    @NonNull
    private final JPanel cardPanel = new JPanel(this.cardLayout);

    @NonNull
    private final WelcomePanel welcomePanel;

    private transient ICombinedBackend cb;
    private IPerson p;
    private JPanel personPanel;

    public MainFrame() {
//        Create WelcomePanel
        this.welcomePanel = new WelcomePanel(this::onLoginSuccess);

//        Init default settings
        this.setTitle("Student Information Management System");
        this.setSize(FRAME_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

//        Add WelcomePanel to card panel
        this.addPanel(this.welcomePanel);

//        Add card panel to frame
        this.add(cardPanel);

//        Show frame
        this.setVisible(true);
    }

//    Add a Component to card panel
    public void addPanel(@NonNull Component comp) {
        Helpers.addPanel(this.cardPanel, comp);
    }

//    Action when login success
    public void onLoginSuccess() {
        System.out.println("Login success");

//        Get backend and logged in person
        this.cb = this.welcomePanel.getCombinedBackend();
        this.p = this.welcomePanel.getPerson();

//        Get the person panel
        this.personPanel = this.p.getPanel(cb);

//        Add to card panel, and show it
        this.addPanel(personPanel);
        this.cardLayout.show(this.cardPanel, Helpers.getObjectName(personPanel));
    }
}
