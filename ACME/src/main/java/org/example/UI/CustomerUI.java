package org.example.UI;

public class CustomerUI {


    // Fields

    private String customerId;
    private String customerName;


    // Constructor

    public CustomerUI(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;

    }

    public static int promptCustomerID(){
        //TO DO
        // make this method force user select a number that is >1 otherwise return -1
        return 0;
    }

    public static void promptActions() {
        //this method will make the teller select from a number of actions
        //create account
        //delete account
        //getBankAccounts
        //once inside a bank account prompt have a few options of seeing standing orders and direct debits`
        //create and delete these standing orders
        // and a lot more that i cannot think of at the moment
    }

    // Getters

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }


    // Display Menu

    public void displayCustomerDetails() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Customer Name: " + customerName);
    }
}