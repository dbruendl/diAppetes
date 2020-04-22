package com.example.diappetes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSLite extends SQLiteOpenHelper {
    public static final String USER_TABLE = "User_Table";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_ID = "ID";

    public DatabaseSLite(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + "" +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMAIL + " VARCHAR(255), " + COLUMN_PASSWORD + " VARCHAR(255))";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(RegisterModel rm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, rm.getEmail());
        cv.put(COLUMN_PASSWORD, rm.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);

        if(insert == 1){
            return false;
        }else{
            return true;
        }
    }

    public boolean find(String select){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

    }
}
