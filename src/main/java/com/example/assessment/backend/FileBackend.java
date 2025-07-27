package com.example.assessment.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import lombok.Cleanup;
import lombok.NonNull;

public abstract class FileBackend {

    protected static final Path EMPTY = Path.of("");

    protected Path db;

    protected FileBackend(String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    protected FileBackend(Path p) throws IOException, IllegalArgumentException {
        if (EMPTY.equals(p)) {
            throw new IllegalArgumentException("Path must not be empty!");
        }

        this.db = p;
        if (Files.notExists(this.db)) {
            Files.createDirectories(this.db);
        }
    }

    protected Object getObjectByPath(@NonNull Path fName) throws IOException, ClassNotFoundException, IllegalArgumentException {
        if (EMPTY.equals(fName)) {
            throw new IllegalArgumentException("fName must not be empty!");
        }

        Path p = this.db.resolve(fName);
        if (Files.notExists(p) || !Files.isRegularFile(p) || !Files.isReadable(p)) {
            return null;
        }

        @Cleanup
        InputStream inpStream = Files.newInputStream(p);
        @Cleanup
        ObjectInputStream objectInputStream = new ObjectInputStream(inpStream);
        var o = objectInputStream.readObject();

        return o;
    }

    protected void setObjectWithName(@NonNull ISelfSerializable o) throws IOException {
        Path path = this.db.resolve(o.getPath());

        @Cleanup
        OutputStream outStream = Files.newOutputStream(path);
        @Cleanup
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);

        objectOutputStream.writeObject(o);
    }
}
