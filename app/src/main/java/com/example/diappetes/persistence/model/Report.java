package com.example.diappetes.persistence.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.OffsetDateTime;

@Entity
public class Report {
    @PrimaryKey
    public Integer id;
    public final OffsetDateTime created;
    public final Integer steps;
    public String uid;

    public Report(@NonNull OffsetDateTime created,@NonNull Integer steps) {
        this.created = created;
        this.steps = steps;
    }
}
