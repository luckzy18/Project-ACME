package org.example.model;
public abstract class Account {
    //Fields
    private int accountNumber;
    private int customerID;
    private String sortCode;
    private double balance;
    //private String startDate;
    //private String expiryDate;

    //Constructor
    public Account(int accountNumber, int customerID, String sortCode, double balance) {
        this.accountNumber = accountNumber;
        this.customerID = customerID;
        this.sortCode = sortCode;
        this.balance = balance;
    }

    //Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getSortCode() {
        return sortCode;
    }

    public double getBalance() {
        return balance;
    }

    //Methods

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    //Deposit money into the account
    public void deposit(double amount) { }

    // Withdraw money from the account
    public void withdraw(double amount) { }
}
