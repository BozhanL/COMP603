package com.example.assessment.gui;

import com.google.errorprone.annotations.CheckReturnValue;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

// Helper class for GUI
@UtilityClass
@CheckReturnValue
public class Helpers {

//    Show error message
    public void showErrorMessage(@NonNull Exception e) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

//    Show error message
    public void showErrorMessage(@NonNull String e) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

//    Show error message
    @FormatMethod
    public void showErrorMessage(@NonNull @FormatString String e, @NonNull Object... args) {
        SwingUtilities.invokeLater(() -> {
            String s = String.format(e, args);
            JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }

//    Show normal message
    public void showMessage(@NonNull String title, @NonNull String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        });
    }

//    Add a panel to card panel
    public void addPanel(@NonNull JPanel cardPanel, @NonNull Component comp) {
        cardPanel.add(comp, getObjectName(comp));
    }

//    Get the class name based on the object
    public String getObjectName(@NonNull Object o) {
        return o.getClass().getSimpleName();
    }

//    Get the class name based on the class object
    public String getObjectName(@NonNull Class<?> o) {
        return o.getSimpleName();
    }
}
