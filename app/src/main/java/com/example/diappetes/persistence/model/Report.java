package com.example.diappetes.persistence.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.time.OffsetDateTime;

@Entity
public class Report {
    private final OffsetDateTime created;
    private final Integer steps;
    private String uid;

    public Report(@NonNull OffsetDateTime created,@NonNull Integer steps) {
        this.created = created;
        this.steps = steps;
    }
}
