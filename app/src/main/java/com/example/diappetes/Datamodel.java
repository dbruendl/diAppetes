package com.example.diappetes;

public class Datamodel {
    private int userID;
    private int steps;
    private String username;

    public Datamodel(Integer userID, Integer steps, String username) {
        this.userID = userID;
        this.steps = steps;
        this.username = username;
    }

    @Override
    public String toString() {
        return "Datamodel{" +
                "userID=" + userID +
                ", steps=" + steps +
                ", username='" + username + '\'' +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
