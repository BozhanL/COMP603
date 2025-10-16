package com.example.assessment.gui.welcome;

import com.example.assessment.backend.generic.IBackend;
import com.google.errorprone.annotations.CheckReturnValue;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serial;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Getter;
import lombok.NonNull;

@CheckReturnValue
public final class DatabaseSelectionPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    @NonNull
    private File file = IBackend.getDefaultDataLocationStatic().toFile();

    @NonNull
    private final BorderLayout borderLayout = new BorderLayout();
    @NonNull
    private final JFileChooser chooser = new JFileChooser(file);

    @NonNull
    private final JLabel locationLabel = new JLabel(formatLocationLabel(file), JLabel.CENTER);

    @NonNull
    private final JPanel bottomButtonPanel = new JPanel();
    @NonNull
    private final JButton selectLocation = new JButton("Select Database");
    @NonNull
    private final JButton openDatabase = new JButton("Open Database");

    public DatabaseSelectionPanel(@NonNull ActionListener openDatabase) {
        this.chooser.setDialogTitle("Select database folder");
        this.chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        this.chooser.setAcceptAllFileFilterUsed(false);

        this.selectLocation.addActionListener((e) -> {
            int returnVal = this.chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                this.updateLocation(this.chooser.getSelectedFile());
            }
        });

        this.openDatabase.addActionListener(openDatabase);

        this.bottomButtonPanel.add(this.selectLocation);
        this.bottomButtonPanel.add(this.openDatabase);

        this.setLayout(borderLayout);
        this.add(this.locationLabel, BorderLayout.CENTER);
        this.add(this.bottomButtonPanel, BorderLayout.SOUTH);
    }

    public static String formatLocationLabel(@NonNull File p) {
        p = p.getAbsoluteFile();

        return String.format("Database Location: %s", p);
    }

    public void updateLocation(@NonNull File newLocation) {
        this.file = newLocation;
        this.locationLabel.setText(formatLocationLabel(this.file));
    }
}
