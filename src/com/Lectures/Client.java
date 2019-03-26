package com.Lectures;

public class Client extends User {
    private double balance;

    public Client(String username, String password, double balance){
        super(username, password);
        this.balance = balance;
    }

    protected double getBalance(){
        return this.balance;
    }

    protected void updateBalance(double amount){
        balance -= amount;
    }
}
