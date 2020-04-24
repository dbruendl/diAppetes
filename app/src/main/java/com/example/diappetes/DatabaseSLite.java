package com.example.diappetes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSLite extends SQLiteOpenHelper {
    public static final String USER_TABLE = "User_Table";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_ID = "ID";


    public DatabaseSLite(@Nullable Context context) {
        super(context, "user.db", null, 1);
    }

    public DatabaseSLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
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

    public boolean addOne(RegisterModel rm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, rm.getEmail());
        cv.put(COLUMN_PASSWORD, rm.getPassword());

        long insert = db.insert(USER_TABLE, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean find(String select) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        return true;
    }

    public List<RegisterModel> getEveryone() {
        List<RegisterModel> returnList = new ArrayList<>();

        //get data from database
        String queryString = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(0);
                String userEmail = cursor.getString(1);
                String userPassword = cursor.getString(2);

                RegisterModel newUser = new RegisterModel(userID, userEmail, userPassword);
                returnList.add(newUser);

            } while (cursor.moveToNext());

        } else //failure nothing happens

            //closing
            cursor.close();
        db.close();
        return returnList;
    }

    public boolean loginCheck() {
        SQLiteDatabase db = this.getReadableDatabase();
        CheckInput checkInput = new CheckInput();

        String inputEmail = checkInput.email;      // EMAIL = NULL for some reason

        String queryString = "SELECT * FROM " + USER_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if (inputEmail == null) {
            return false;
        }
        /*
        if (cursor.moveToFirst()) {
                int userID = cursor.getInt(0);
                String dbEmail = cursor.getString(1);
                boolean match = inputEmail.equals(dbEmail);

                while (cursor.moveToNext() == true) {
                    if (inputEmail.equals(dbEmail))
                        break;
                }
        }
        */
        return true;
    }
}
