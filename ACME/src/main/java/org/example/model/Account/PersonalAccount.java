package org.example.model.Account;

public class PersonalAccount extends Account {
    //Fields
    private Overdraft overdraft;
    private String directDebits;
    private String standingOrders; //Both strings until I confirm how we want to handle this.

    //Constructor matching super
    public PersonalAccount(String accountNumber, int customerID, String sortCode, double balance) {
        super(accountNumber, customerID, sortCode, balance);
    }

    //Getters
    public String getDirectDebits() {
        return directDebits;
    }

    public String getStandingOrders() {
        return standingOrders;
    }

    //Setters
    public void setDirectDebits(String directDebits) {
        this.directDebits = directDebits;
    }

    public void setStandingOrders(String standingOrders) {
        this.standingOrders = standingOrders;
    }

    //Methods
    @Override
    public String toString() {
        String overdraftInfo = (overdraft != null)
                ? String.format("Yes (£%.2f)", overdraft.getMaxOverdraft())
                : "No";
        return super.toString() + String.format(
                """
                Account Type   : Personal
                Overdraft      : %s
                ========================================
                """,
                overdraftInfo
        );
    }

    //Withdraw — overrides Account.withdraw()
    @Override
    public boolean withdraw(double amount) {
        return false;
    }

    //Add a direct debit to this account
    public void addDirectDebit(int payment) { }

    //Remove a direct debit by its ID
    public void removeDirectDebit(int paymentId) { }

    //Add a standing order to this account
    public void addStandingOrder(int payment) { }

    //Remove a standing order by its ID
    public void removeStandingOrder(int paymentId) { }

}
