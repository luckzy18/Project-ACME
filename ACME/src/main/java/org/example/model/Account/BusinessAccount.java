package org.example.model.Account;
public class BusinessAccount extends Account {
    //Fields
    public static final double ANNUAL_FEE = 120.0;
    private boolean chequeBook; //I'll change this later
    private Overdraft overdraft;
    private boolean loanRequest;
    private String businessType;
    private boolean internationalTrading;//I'll also change this later


    public BusinessAccount(String accountNumber, int customerID, String sortCode, double balance,String businessType) {
        super(accountNumber, customerID, sortCode, balance);
        this.businessType = businessType;
        this.chequeBook = false;
        this.loanRequest = false;
        this.internationalTrading = false;
    }

    //Getters
    public boolean hasChequeBook() {
        return chequeBook;
    }

    public boolean hasOverdraft() {
        return overdraft.isInOverdraft();
    }

    public double getOverdraftAmount() {
        return overdraft.getOverdraftBalance();
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
    @Override
    public String toString() {
        String overdraftInfo = (overdraft != null)
                ? String.format("Yes (£%.2f)", overdraft.getMaxOverdraft())
                : "No";
        return super.toString() + String.format(
                """
                Account Type   : Business
                Business Type  : %s
                Overdraft      : %s
                ========================================
                """,
                businessType, overdraftInfo
        );
    }
}
