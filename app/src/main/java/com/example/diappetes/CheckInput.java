package com.example.diappetes;

import android.text.Editable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInput {
    String email;
    String password;
    Matcher matcher;    //Checking if email input is correct

    public CheckInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CheckInput() {
    }

    public boolean checkEmail(){
        //SELECT * FROM USER_TABLE WHERE COLUMN_EMAIL = this.email; If this comes back with one row it is true
        switch (email){
            case "olafwarzocha1998@gmail.com":
            case "sample0@gmail.com":
            case "sample1@gmail.com":
                return true;
            default: return false;
        }
    }

    public boolean checkPassword(){
        //SELECT COLUMN_PASSWORD FROM USER_TABLE WHERE email = email;
        switch (password){
            case "1234":
            case "sample0":
            case "sample1":
                return true;
            default: return false;
        }
    }

    public boolean checkPassword(Editable password, Editable password2) {
        if (password == password2){
            return true;
        }
        return false;
    }

    public boolean checkEmail(Editable email) {
        if (email != null) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
