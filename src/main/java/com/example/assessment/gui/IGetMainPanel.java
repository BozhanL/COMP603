package com.example.assessment.gui;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.google.errorprone.annotations.CheckReturnValue;
import javax.swing.JPanel;
import lombok.NonNull;

// Interface to get the main panel for a person
@CheckReturnValue
public interface IGetMainPanel {

    public abstract JPanel getPanel(@NonNull ICombinedBackend cb);
}
