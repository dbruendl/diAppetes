package com.example.diappetes.register;

public class RegisterModel {
    private int userID;
    private String email;
    private String password;


    public RegisterModel(Integer userID, String email, String password) {
        this.userID = userID;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                ", name='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer name) {
        this.userID = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
