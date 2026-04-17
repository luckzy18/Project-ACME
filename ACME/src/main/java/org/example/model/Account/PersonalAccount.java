package org.example.model.Account;

public class PersonalAccount extends Account {
    //Fields
    private String directDebits;
    private String standingOrders; //Both strings until I confirm how we want to handle this.

    //Constructor matching super
    public PersonalAccount(int accountNumber, int customerID, String sortCode, double balance) {
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
    //Withdraw — overrides Account.withdraw()
    @Override
    public void withdraw(double amount) { }

    //Add a direct debit to this account
    public void addDirectDebit(int payment) { }

    //Remove a direct debit by its ID
    public void removeDirectDebit(int paymentId) { }

    //Add a standing order to this account
    public void addStandingOrder(int payment) { }

    //Remove a standing order by its ID
    public void removeStandingOrder(int paymentId) { }

    /*
    +Overdraft features and printing debit/order details.
     */
}
