package com.example.assessment.gui;

import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AssessmentTwo {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception in " + thread.getName());
            throwable.printStackTrace();
            restart();
        });

        SwingUtilities.invokeLater(MainFrame::new);
    }

    public void restart() {
        SwingUtilities.invokeLater(() -> {
            for (Frame frame : Frame.getFrames()) {
                frame.dispose();
            }

            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "The application encountered an unexpected error.\nRestart now?",
                    "Application Error",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                new MainFrame();
            }
        });
    }
}
