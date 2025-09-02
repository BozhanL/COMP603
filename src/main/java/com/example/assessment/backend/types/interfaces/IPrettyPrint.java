package com.example.assessment.backend.types.interfaces;

import com.google.errorprone.annotations.CheckReturnValue;

// This interface defined a method to format the object to one variable per line
@CheckReturnValue
public interface IPrettyPrint {

//    Return a string with format of one variable per line
    public abstract String prettyToString();
}
