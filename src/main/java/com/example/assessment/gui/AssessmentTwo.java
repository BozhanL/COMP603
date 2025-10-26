package com.example.assessment.gui;

import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import lombok.experimental.UtilityClass;

// Main class
@UtilityClass
@CheckReturnValue
public class AssessmentTwo {

    public static void main(String[] args) {
//        Create a handler for uncaught exception
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception in " + thread.getName());
            throwable.printStackTrace();
            restart();
        });

//        Create the main frame
        SwingUtilities.invokeLater(MainFrame::new);
    }

//    Restart the programe
    public void restart() {
        SwingUtilities.invokeLater(() -> {
//            Close all frame
            for (Frame frame : Frame.getFrames()) {
                frame.dispose();
            }

//            Ask for choice
            int choice = JOptionPane.showConfirmDialog(
                    null,
                    "The application encountered an unexpected error.\nRestart now?",
                    "Application Error",
                    JOptionPane.YES_NO_OPTION
            );
//            Restart if yes
            if (choice == JOptionPane.YES_OPTION) {
                new MainFrame();
            }
        });
    }
}
