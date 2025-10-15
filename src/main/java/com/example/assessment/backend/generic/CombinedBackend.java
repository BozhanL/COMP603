package com.example.assessment.backend.generic;

import com.example.assessment.backend.types.interfaces.ICourse;
import com.example.assessment.backend.types.interfaces.IPerson;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
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

    private CombinedBackend() throws IOException, DatabaseCorruptedException {
        this(DEFAULT_DATA_LOCATION);
    }

    private CombinedBackend(@NonNull String p) throws IOException, DatabaseCorruptedException {
        this(Path.of(p));
    }

    private CombinedBackend(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        this(IPersonBackend.of(p), ICourseBackend.of(p));
    }

    public static ICombinedBackend of() throws IOException, DatabaseCorruptedException {
        return new CombinedBackend();
    }

    public static ICombinedBackend of(@NonNull String p) throws IOException, DatabaseCorruptedException {
        return new CombinedBackend(p);
    }

    public static ICombinedBackend of(@NonNull Path p) throws IOException, DatabaseCorruptedException {
        return new CombinedBackend(p);
    }

    @Override
    public void setPerson(@NonNull IPerson p) {
        this.pb.setPerson(p);
    }

    @Override
    public void modifyPerson(@NonNull IPerson p) {
        this.pb.modifyPerson(p);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean deletePersonById(@NonNull String id) {
        return this.pb.deletePersonById(id);
    }

    @Override
    public IPerson getPersonById(@NonNull String id) {
        return this.pb.getPersonById(id);
    }

    @Override
    public ImmutableList<IPerson> listPerson() {
        return this.pb.listPerson();
    }

    @Override
    public ICourse getCourseByCode(@NonNull String code) throws ParseException {
        return this.cb.getCourseByCode(code);
    }

    @Override
    public void setCourse(@NonNull ICourse c) {
        this.cb.setCourse(c);
    }

    @Override
    @CanIgnoreReturnValue
    public boolean deleteCourseByCode(@NonNull String code) throws ParseException {
        return this.cb.deleteCourseByCode(code);
    }

    @Override
    public void modifyCourse(@NonNull ICourse c) {
        this.cb.modifyCourse(c);
    }

    @Override
    public ImmutableList<ICourse> listCourse() {
        return this.cb.listCourse();
    }

    @Override
    public String getDb() {
        return this.pb.getDb();
    }
}
