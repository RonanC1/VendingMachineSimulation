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
        if(balance < amount){
            throw new VendingException("Not enough credit in balance.");
        }
        balance -= amount;
    }


    @Override
    public String toString(){
        return super.toString() + "," + this.balance;
    }
}
