package com.example.diappetes.persistence.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.diappetes.persistence.DateTypeConverter;

import java.util.Date;

@Entity
public class Report {
    @PrimaryKey
    public Integer id;
    public Date created;
    public Integer steps;
    public String userId;
}
