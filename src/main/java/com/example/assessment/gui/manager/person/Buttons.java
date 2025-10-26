package com.example.assessment.gui.manager.person;

import com.google.errorprone.annotations.CheckReturnValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

// Buttons for list person panel
@CheckReturnValue
@AllArgsConstructor
public enum Buttons {
    ADD_MANAGER("Add Manager"), ADD_STUDENT("Add Student"), MODIFY("Modify Person"), DELETE("Delete Person");

    @Getter
    @NonNull
    private final String name;
}
