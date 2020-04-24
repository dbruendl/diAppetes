package com.example.diappetes;

import android.content.Context;
import android.text.Editable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckInput {
    String email;
    String password;
    Matcher matcher; //Checking if email input is correct
    DatabaseSLite db;


    public CheckInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CheckInput() {
    }

    public boolean logincheckEmail(Context c){
        // How to access logincheck method from DatabaseSlite?  !!!!

        db = new DatabaseSLite(c);
        if (db.loginCheck()){
            return true;
        }else {
            return false;
        }

    }

    public boolean logincheckPassword(){
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
        String pass1 = password.toString();
        String pass2 = password2.toString();
        if (pass1.equals(pass2)){
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
