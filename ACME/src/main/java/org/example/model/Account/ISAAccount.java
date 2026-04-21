package org.example.model.Account;

public class ISAAccount extends Account {
    //Fields
    private double annualInterestRate;
    private double annualAverageBalance;
    private Overdraft overdraft;

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
    @Override
    public String toString() {
        return super.toString() + String.format(
                """
                Account Type   : ISA
                Interest Rate  : %.2f%%
                ========================================
                """,
                annualInterestRate * 100
        );
    }

}
