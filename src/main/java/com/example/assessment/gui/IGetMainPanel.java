package com.example.assessment.gui;

import com.example.assessment.backend.generic.ICombinedBackend;
import javax.swing.JPanel;
import lombok.NonNull;

public interface IGetMainPanel {

    public abstract JPanel getPanel(@NonNull ICombinedBackend cb);
}
