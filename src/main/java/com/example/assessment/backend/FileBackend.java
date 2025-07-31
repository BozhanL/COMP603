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

public abstract class FileBackend implements IBackend {

    protected static final Path EMPTY = Path.of("");
    public static final Path DEFAULT_DATA_LOCATION = Path.of(System.getProperty("user.home"), ".student/filedb");

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

    protected Object getObjectByPath(@NonNull String fName) throws IOException, ClassNotFoundException, IllegalArgumentException {
        if (fName.isBlank()) {
            throw new IllegalArgumentException("fName must not be blank!");
        }

        @Cleanup
        DirectoryStream<Path> dirStream = Files.newDirectoryStream(this.db, (f) -> f.getFileName().toString().contains(fName));
        Iterator<Path> iter = dirStream.iterator();
        if (!iter.hasNext()) {
            throw new IOException("No file found matching the name: " + fName);
        }

        Path p = iter.next();
        if (!Files.isRegularFile(p) || !Files.isReadable(p)) {
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

    @Override
    public Path getDefaultDataLocation() {
        return DEFAULT_DATA_LOCATION;
    }
}
