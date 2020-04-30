package com.example.diappetes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseSLite2 extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "ID";
    public static final String USER_DATA = "User_Data";
    public static final String COLUMN_STEPS = "Steps";
    public static final String COLUMN_USERNAME = "Username";


    public DatabaseSLite2(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    public DatabaseSLite2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_DATA + "" +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STEPS + " VARCHAR(255), " + COLUMN_USERNAME + " VARCHAR(255))";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




}
