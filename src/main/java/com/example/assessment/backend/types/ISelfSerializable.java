package com.example.assessment.backend.types;

import java.io.Serializable;
import java.nio.file.Path;

public interface ISelfSerializable extends Serializable {

    public abstract Path getPath();
}
