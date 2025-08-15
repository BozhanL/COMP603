package com.example.assessment.backend.generic;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

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

    private CombinedBackend(@NonNull String p) throws IOException, IllegalArgumentException {
        this(Path.of(p));
    }

    private CombinedBackend(@NonNull Path p) throws IOException, IllegalArgumentException {
        this.pb = IPersonBackend.of(p);
        this.cb = ICourseBackend.of(p);
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
    public IPerson getPersonByPartPath(String fName) throws IOException, DatabaseCorruptedException {
        return this.pb.getPersonByPartPath(fName);
    }

    @Override
    public void setPerson(IPerson p) throws IOException {
        this.pb.setPerson(p);
    }

    @Override
    public boolean deletePersonByPartPath(String fName) throws IOException {
        return this.pb.deletePersonByPartPath(fName);
    }

    @Override
    public void modifyPerson(IPerson p) throws IOException {
        this.pb.modifyPerson(p);
    }

    @Override
    public ImmutableList<IPerson> listPerson() throws IOException, DatabaseCorruptedException {
        return this.pb.listPerson();
    }

    @Override
    public ICourse getCourseByCode(String code) throws IOException, DatabaseCorruptedException {
        return this.cb.getCourseByCode(code);
    }

    @Override
    public void setCourse(ICourse c) throws IOException {
        this.cb.setCourse(c);
    }

    @Override
    public boolean deleteCourseByCode(String code) throws IOException, DatabaseCorruptedException {
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
