package com.gigmoneytransferapi.spring.money_transfer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    private long accountID;
    private String name;
    private double balance;

    public Account() {
    }

    public Account(long accountID, String name, double balance) {
        this.accountID = accountID;
        this.name = name;
        this.balance = balance;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID='" + accountID + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}