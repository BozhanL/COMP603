package com.example.assessment.gui.manager;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import java.io.Serial;
import javax.swing.JPanel;
import lombok.NonNull;

public class ManagerMainPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    public ManagerMainPanel(@NonNull ICombinedBackend cb, @NonNull IPerson p) {
    }
}
