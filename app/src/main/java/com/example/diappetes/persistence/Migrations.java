package com.example.diappetes.persistence;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {
    public static final Migration[] MIGRATIONS = new Migration[]{
            new Migration(1, 2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {

                }
            }
    };
}
