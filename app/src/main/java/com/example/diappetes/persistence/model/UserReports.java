package com.example.diappetes.persistence.model;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserReports {
    @Embedded
    private User user;
    @Relation(parentColumn = "uid",entityColumn = "uid")
    private List<Report> reports;
}
