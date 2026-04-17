package org.example.model;
public abstract class Account {
    //Fields
    private String accountNumber;
    private int customerID;
    private String sortCode;
    private double balance;
    //private String startDate;
    //private String expiryDate;

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
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    protected void setBalance(double balance) { this.balance = balance; }//Allows subclass access.

    //Deposit money into the account
    public void deposit(double amount) {
        if (amount < 0) {
            IO.println("Invalid amount. Deposit amount must be greater than zero.");
            return;
        }
        setBalance(getBalance() + amount);
    }

    //Withdraw money from the account
    public void withdraw(double amount) {
        if (amount <= 0) {
            IO.println("Invalid amount. Withdraw amount must be greater than zero.");
            return;
        }
        if (amount > getBalance()) {
            IO.println("Insufficient funds for withdrawal.");
            return;
        }
        setBalance(getBalance() - amount);
    }
}
