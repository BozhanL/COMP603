package com.example.assessment.gui;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Helpers {

    public void showErrorMessage(Exception e) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void showErrorMessage(String e) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void addPanel(@NonNull JPanel cardPanel, @NonNull Component comp) {
        cardPanel.add(comp, getObjectName(comp));
    }

    public String getObjectName(Object o) {
        return o.getClass().getSimpleName();
    }
}
