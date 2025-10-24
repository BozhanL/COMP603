package com.example.assessment.gui;

import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Helpers {

    public void showErrorMessage(@NonNull Exception e) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void showErrorMessage(@NonNull String e) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

    @FormatMethod
    public void showErrorMessage(@NonNull @FormatString String e, @NonNull Object... args) {
        SwingUtilities.invokeLater(() -> {
            String s = String.format(e, args);
            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

    public void showMessage(@NonNull String title, @NonNull String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void addPanel(@NonNull JPanel cardPanel, @NonNull Component comp) {
        cardPanel.add(comp, getObjectName(comp));
    }

    public String getObjectName(@NonNull Object o) {
        return o.getClass().getSimpleName();
    }

    public String getObjectName(@NonNull Class<?> o) {
        return o.getSimpleName();
    }
}
