package com.example.assessment.gui;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.example.assessment.gui.welcome.WelcomePanel;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lombok.NonNull;

public final class MainFrame {

    static final Dimension FRAME_SIZE = new Dimension(800, 600);

    @NonNull
    private final JFrame frame = new JFrame("Student Information Management System");
    @NonNull
    private final CardLayout cardLayout = new CardLayout();
    @NonNull
    private final JPanel cardPanel = new JPanel(this.cardLayout);

    @NonNull
    private final WelcomePanel welcomePanel;

    private transient ICombinedBackend cb;
    private transient IPerson p;
    private JPanel personPanel;

    public MainFrame() {
        this.welcomePanel = new WelcomePanel(() -> this.onLoginSuccess());

        this.frame.setSize(FRAME_SIZE);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);

        this.addPanel(this.welcomePanel);

        this.frame.add(cardPanel);
        this.frame.setVisible(true);
    }

    public void addPanel(@NonNull Component comp) {
        Helpers.addPanel(this.cardPanel, comp);
    }

    public void onLoginSuccess() {
        System.out.println("Login success");
        this.cb = this.welcomePanel.getCombinedBackend();
        this.p = this.welcomePanel.getPerson();

        this.personPanel = this.p.getPanel(cb);
        this.addPanel(personPanel);
        this.cardLayout.show(this.cardPanel, Helpers.getObjectName(personPanel));
    }
}
