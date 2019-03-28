package com.Lectures;

public abstract class User {
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    protected String getUsername(){
        return this.username;
    }

    protected String getPassword(){
        return this.password;
    }

    @Override
    public String toString() {
        return username + "," + password;
    }
}
