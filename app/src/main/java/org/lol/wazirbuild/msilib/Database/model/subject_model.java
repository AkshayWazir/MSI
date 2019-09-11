package org.lol.wazirbuild.msilib.Database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Subjects")
public class subject_model {

    @PrimaryKey
    @NonNull
    private String subject;

    @Ignore
    public subject_model() {
    }

    public subject_model(@NonNull String subject) {
        this.subject = subject;
    }

    @NonNull
    public String getSubject() {
        return subject;
    }

    public void setSubject(@NonNull String subject) {
        this.subject = subject;
    }
}
