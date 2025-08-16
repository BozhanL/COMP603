package com.example.assessment.backend.file;

import com.example.assessment.backend.generic.DatabaseCorruptedException;
import com.example.assessment.backend.generic.IBackend;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
@CheckReturnValue
public abstract class FileBackend implements IBackend {

    protected static final Path EMPTY = Path.of("");

    @Getter
    protected Path db;

    protected FileBackend(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
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

//    protected Path getPathByName(@NonNull String fName) throws IOException {
//        if (fName.isBlank()) {
//            throw new IllegalArgumentException("fName must not be blank!");
//        }
//
//        @Cleanup
//        DirectoryStream<Path> dirStream = Files.newDirectoryStream(this.db, (f) -> f.getFileName().toString().contains(fName));
//        Iterator<Path> iter = dirStream.iterator();
//        if (!iter.hasNext()) {
//            throw new IOException("No file found matching the name: " + fName);
//        }
//
//        Path p = iter.next();
//
//        if (iter.hasNext()) {
//            throw new IOException("Multiple files found matching the name: " + fName);
//        }
//
//        return p.getFileName();
//    }
    protected <T> T getObjectByPath(@NonNull Class<T> cl, @NonNull String fName) throws IOException, DatabaseCorruptedException, IllegalArgumentException, InvalidPathException {
        return this.getObjectByPath(cl, Path.of(fName));
    }

    protected <T> T getObjectByPath(@NonNull Class<T> cl, @NonNull Path fName) throws IOException, DatabaseCorruptedException, IllegalArgumentException {
        Path p = this.db.resolve(fName);

        File f = p.toFile();

        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) {
            Object o = ois.readObject();
            if (cl.isInstance(o)) {
                return cl.cast(o);
            }
            return null;
        } catch (ClassNotFoundException | InvalidClassException | StreamCorruptedException e) {
            throw new DatabaseCorruptedException(e);
        }
    }

    protected void setObject(@NonNull Object o, Path p) throws IOException, FileAlreadyExistsException {
        Path path = this.db.resolve(p);

//        Check whether file exist
        if (Files.exists(path)) {
            throw new FileAlreadyExistsException(path.toString());
        }

        File f = path.toFile();

        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
            oos.writeObject(o);
        }
    }

    protected boolean deleteObjectWithPath(@NonNull String fName) throws IOException, InvalidPathException {
        return this.deleteObjectWithPath(Path.of(fName));
    }

    @CanIgnoreReturnValue
    protected boolean deleteObjectWithPath(@NonNull Path path) throws IOException {
        return Files.deleteIfExists(this.db.resolve(path));
    }

    protected void modifyObject(@NonNull Object o, Path p) throws IOException {
        Path path = this.db.resolve(p);

        Files.deleteIfExists(path);

        File f = path.toFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)))) {
            oos.writeObject(o);
        }
    }

    protected <T> ImmutableList<T> listObject(@NonNull Class<T> cl) throws IOException, DatabaseCorruptedException {
        File folder = this.getDb().toFile();
        File[] files = folder.listFiles();
        if (Objects.isNull(files)) {
            files = new File[0];
        }
        ArrayList<T> out = new ArrayList<>(files.length);

        for (File f : files) {
            try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)))) {
                Object o = ois.readObject();
                if (cl.isInstance(o)) {
                    out.add(cl.cast(o));
                }
            } catch (ClassNotFoundException | InvalidClassException | StreamCorruptedException e) {
                throw new DatabaseCorruptedException(e);
            }
        }

        return ImmutableList.copyOf(out);
    }
}
