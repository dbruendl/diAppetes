package com.example.diappetes.persistence.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
public class Setting {
    @PrimaryKey
    public int id;

    @NonNull
    public String description;

    @Setter
    public boolean enabled;

    public Setting(int id, @NonNull String description) {
        this.id = id;
        this.description = description;
        this.enabled = false;
    }
}
