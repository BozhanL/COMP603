package com.example.assessment.backend.generic;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

// This class is a wrapper for IPersonBackend and ICourseBackend
@Value
@CheckReturnValue
@AllArgsConstructor(staticName = "of")
public class CombinedBackend implements ICombinedBackend {

    @NonNull
    IPersonBackend pb;
    @NonNull
    ICourseBackend cb;

    private CombinedBackend() throws IOException {
        this(DEFAULT_DATA_LOCATION);
    }

    private CombinedBackend(@NonNull String p) throws IOException, IllegalArgumentException, InvalidPathException {
        this(Path.of(p));
    }

    private CombinedBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        this(IPersonBackend.of(p), ICourseBackend.of(p));
    }

    public static ICombinedBackend of() throws IOException {
        return new CombinedBackend();
    }

    public static ICombinedBackend of(@NonNull String p) throws IOException, IllegalArgumentException {
        return new CombinedBackend(p);
    }

    public static ICombinedBackend of(@NonNull Path p) throws IOException, IllegalArgumentException {
        return new CombinedBackend(p);
    }

    @Override
    public void setPerson(IPerson p) throws IOException, FileAlreadyExistsException {
        this.pb.setPerson(p);
    }

    @Override
    public void modifyPerson(IPerson p) throws IOException {
        this.pb.modifyPerson(p);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean deletePersonById(String id) throws IOException {
        return this.pb.deletePersonById(id);
    }

    @Override
    public IPerson getPersonById(String id) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        return this.pb.getPersonById(id);
    }

    @Override
    public ImmutableList<IPerson> listPerson() throws IOException, DatabaseCorruptedException {
        return this.pb.listPerson();
    }

    @Override
    public ICourse getCourseByCode(String code) throws IOException, DatabaseCorruptedException, FileNotFoundException {
        return this.cb.getCourseByCode(code);
    }

    @Override
    public void setCourse(ICourse c) throws IOException, FileAlreadyExistsException {
        this.cb.setCourse(c);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean deleteCourseByCode(String code) throws IOException {
        return this.cb.deleteCourseByCode(code);
    }

    @Override
    public void modifyCourse(ICourse c) throws IOException {
        this.cb.modifyCourse(c);
    }

    @Override
    public ImmutableList<ICourse> listCourse() throws IOException, DatabaseCorruptedException {
        return this.cb.listCourse();
    }
}
