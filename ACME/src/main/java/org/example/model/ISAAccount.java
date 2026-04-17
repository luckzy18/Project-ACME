package org.example.model;
public class ISAAccount extends Account {
    //Fields
    private double annualInterestRate;
    private double annualAverageBalance;

    //Constructor matching super
    public ISAAccount(String accountNumber, int customerID, String sortCode, double balance) {
        super(accountNumber, customerID, sortCode, balance);
        this.annualInterestRate = 0.0275; //Will be changed when I better learn how ISA's work.
        this.annualAverageBalance = balance;
    }

    //Getters
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public double getAnnualAverageBalance() {
        return annualAverageBalance;
    }

    //Methods
    /*
    public double calculateInterest() {
        return annualAverageBalance * annualInterestRate
    }

    //Update the stored average balance to the current balance
    public void updateAverageBalance()

     */

}
