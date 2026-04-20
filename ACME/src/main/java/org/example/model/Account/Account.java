package org.example.model.Account;
public abstract class Account {
    //Fields
    private String accountNumber;
    private int customerID;
    private String sortCode;
    private double balance;


    //Constructor
    public Account(String accountNumber, int customerID, String sortCode, double balance) {
        this.accountNumber = accountNumber;
        this.customerID = customerID;
        this.sortCode = sortCode;
        this.balance = balance;
    }

    //Getters
    public String getAccountNumber() {
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
    @Override
    public String toString() {
        return String.format(
                "Account Number: | Sort Code: %s | Balance: £%.2f",
                accountNumber, sortCode, balance
        );
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    //Deposit money into the account
    public void deposit(double amount) { }

    // Withdraw money from the account
    public boolean withdraw(double amount) {
        return false;
    }
}
