package org.example.model.Account;

import java.util.Date;

public class Overdraft {
    private double overdraftBalance = 0;
    private double maxOverdraft = 100;
    private Date overdraftStart;
    private static final double MONTHLY_INTEREST_RATE = 0.015; // 1.5%

    public Overdraft() {}

    public Overdraft(double maxOverdraft) {
        this.maxOverdraft = maxOverdraft;
    }

    public double getOverdraftBalance() { return overdraftBalance; }
    public double getMaxOverdraft() { return maxOverdraft; }
    public Date getOverdraftStart() { return overdraftStart; }

    public void setOverdraftBalance(double overdraftBalance) { this.overdraftBalance = overdraftBalance; }
    public void setOverdraftStart(Date overdraftStart) { this.overdraftStart = overdraftStart; }

    public boolean isInOverdraft() {
        return overdraftBalance < 0;
    }

    public boolean canWithdraw(double amount, double currentBalance) {
        return (currentBalance - amount) >= -maxOverdraft;
    }
    public double calculateMonthlyInterest() {
        if (isInOverdraft()) {
            return Math.abs(overdraftBalance) * MONTHLY_INTEREST_RATE;
        }
        return 0;
    }
}