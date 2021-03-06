package com.example.diappetes.persistence.model;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserReports {
    @Embedded public User user;
    @Relation(
            parentColumn = "uid",
            entityColumn = "userId")
    public List<Report> reports;

    public Report getReportForToday() {
        for (Report report : reports) {
            if(report.isFromToday()) {
                return report;
            }
        }
        return null;
    }
}
