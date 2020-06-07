package com.example.diappetes;

import android.content.Context;
import android.text.Editable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInput {
    private String email;
    private String password;
    private Context c;
    private Matcher matcher; //Checking if email input is correct
    DatabaseSLite db;

    //constructor for the login
    public CheckInput(String email, String password, Context c) {
        this.email = email;
        this.password = password;
        this.c = c;
        db = new DatabaseSLite(c);
    }

    //second constructor is for the registration
    public CheckInput() {
    }

    /*this will only work if the first constructor is filled
     * sends the data to the database and checks if it is there
     */
    public boolean loginCheckEmail(){
        return db.checkEmail(this.email);
    }

    /*this will only work if the first constructor is filled
     * sends the data to the database and checks if it is there, and aligned with the email
     */
    public boolean loginCheckPassword(){
        return db.checkPassword(this.email,this.password);
    }

    /*
     * gets both passwords from registration and checks if they are the same
     */
    public boolean checkPassword(Editable password, Editable password2) {
        String pass1 = password.toString();
        String pass2 = password2.toString();
        if (pass1.equals(pass2)){
            return true;
        }
        return false;
    }
    /*
     * checks the email and if it is email standard
     */
    public boolean checkEmail(Editable email) {
        if (email != null) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }
}
