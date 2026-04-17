package org.example.model;
public class PersonalAccount extends Account {
    //Fields
    private String directDebits;
    private String standingOrders; //Both strings until I confirm how we want to handle this.
    //I'm aware that these are duplicate fields but given that they can't go in Account
    //this was the safer option for now.
    private boolean overdraft;
    private double overdraftAmount;
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
    //Behaves differently because of the overdraft
    public void withdraw(double amount) {
        if (amount <= 0) {
            IO.println("Invalid amount. Withdraw amount must be greater than zero.");
            return;
        }
        //variable = (condition) ? expressionIfTrue : expressionIfFalse;
        //Limit = (hasOverdraft) ? Balance + Overdraft : Balance
        double limit = overdraft ? getBalance() + overdraftAmount : getBalance();
        if (amount > limit) {
            System.out.println("Error: Insufficient funds.");
            return;
        }
        setBalance(getBalance() - amount);
    }


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
