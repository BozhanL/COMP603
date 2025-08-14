package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IBackend;
import com.example.assessment.backend.types.ISelfSerializable;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@CheckReturnValue
public abstract class FileBackend implements IBackend {

    protected static final Path EMPTY = Path.of("");
    public static final Path DEFAULT_DATA_LOCATION = Path.of(System.getProperty("user.home"), ".student/filedb");

    @Getter
    protected Path db;

    protected FileBackend(@NonNull String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    protected FileBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        if (EMPTY.equals(p)) {
            throw new IllegalArgumentException("Path must not be empty!");
        }

        this.db = p;
        if (Files.notExists(this.db)) {
            Files.createDirectories(this.db);
        }
    }

    protected Path getPathByName(@NonNull String fName) throws IOException {
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

        if (iter.hasNext()) {
            throw new IOException("Multiple files found matching the name: " + fName);
        }

        return p.getFileName();
    }

    protected Object getObjectByPartPath(@NonNull String fName) throws IOException, DatabaseCorruptedException, IllegalArgumentException {
        Path p = this.getPathByName(fName);

        return this.getObjectByPath(p);
    }

    protected Object getObjectByPath(@NonNull Path fName) throws IOException, DatabaseCorruptedException, IllegalArgumentException {
        Path p = this.db.resolve(fName);

        @Cleanup
        InputStream inpStream = Files.newInputStream(p);
        @Cleanup
        ObjectInputStream objectInputStream = new ObjectInputStream(inpStream);

        try {
            return objectInputStream.readObject();
        } catch (ClassNotFoundException | InvalidClassException | StreamCorruptedException e) {
            throw new DatabaseCorruptedException(e);
        }
    }

    protected void setObject(@NonNull ISelfSerializable o) throws IOException, FileAlreadyExistsException {
        Path path = this.db.resolve(o.getPath());

//        Check whether file exist
        if (Files.exists(path)) {
            throw new FileAlreadyExistsException(path.toString());
        }

        @Cleanup
        OutputStream outStream = Files.newOutputStream(path);
        @Cleanup
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);

        objectOutputStream.writeObject(o);
    }

    protected boolean deleteObjectWithName(@NonNull String fName) throws IOException {
        Path path = this.getPathByName(fName);
        return this.deleteObjectWithPath(path);
    }

    @CanIgnoreReturnValue
    protected boolean deleteObjectWithPath(@NonNull Path path) throws IOException {
        return Files.deleteIfExists(this.db.resolve(path));
    }

    protected void modifyObject(@NonNull ISelfSerializable o) throws IOException {
        Path path = o.getPath();
        this.deleteObjectWithPath(path);
        this.setObject(o);
    }

    @Override
    public Path getDefaultDataLocation() {
        return DEFAULT_DATA_LOCATION;
    }
}
