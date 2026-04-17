package org.example.model.Account;
public class BusinessAccount extends Account {
    //Fields
    public static final double ANNUAL_FEE = 120.0;
    private boolean chequeBook; //I'll change this later
    private boolean overdraft;
    private double overdraftAmount;
    private boolean loanRequest;
    private String businessType;
    private boolean internationalTrading;//I'll also change this later


    public BusinessAccount(String accountNumber, int customerID, String sortCode, double balance) {
        super(accountNumber, customerID, sortCode, balance);
        this.businessType = businessType;
        this.overdraftAmount = 0.0; //Subject to change
        this.overdraft = false;
        this.chequeBook = false;
        this.loanRequest = false;
        this.internationalTrading = false;
    }

    //Getters
    public boolean hasChequeBook() {
        return chequeBook;
    }

    public boolean hasOverdraft() {
        return overdraft;
    }

    public double getOverdraftAmount() {
        return overdraftAmount;
    }

    public boolean hasLoanRequest() {
        return loanRequest;
    }

    public String getBusinessType() {
        return businessType;
    }

    public boolean isInternationalTrading() {
        return internationalTrading;
    }

    //Methods

}
