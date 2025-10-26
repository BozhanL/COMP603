package com.example.assessment.gui;

import com.example.assessment.backend.generic.ICombinedBackend;
import javax.swing.JPanel;
import lombok.NonNull;

// Interface to get the main panel for a person
public interface IGetMainPanel {

    public abstract JPanel getPanel(@NonNull ICombinedBackend cb);
}
