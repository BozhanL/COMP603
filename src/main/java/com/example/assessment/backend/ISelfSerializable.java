package com.example.assessment.backend;

import java.io.Serializable;
import java.nio.file.Path;

public interface ISelfSerializable extends Serializable {

    public abstract Path getPath();
}
