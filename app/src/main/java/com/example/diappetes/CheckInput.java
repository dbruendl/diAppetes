package com.example.diappetes;

public class CheckInput {
    String email;
    String password;

    public CheckInput(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean checkEmail(){
        switch (email){
            case "olafwarzocha1998@gmail.com":
            case "sample0@gmail.com":
            case "sample1@gmail.com":
                return true;
            default: return false;
        }
    }

    public boolean checkPassword(){
        switch (password){
            case "1234":
                return true;
            case "sample0":
                return true;
            case "sample1":
                return true;
            default: return false;
        }
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
