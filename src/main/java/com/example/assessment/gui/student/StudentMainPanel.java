package com.example.assessment.gui.student;

import com.example.assessment.backend.generic.ICombinedBackend;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.Serial;
import javax.swing.JPanel;
import lombok.NonNull;

@CheckReturnValue
public class StudentMainPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    public StudentMainPanel(@NonNull ICombinedBackend cb, @NonNull IPerson p) {
    }
}
