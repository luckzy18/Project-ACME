package org.example.UI;

import org.example.Database.DBinterface;
import org.example.model.Account.AccountTypePolicy;
import org.example.model.people.Customer;

public class CustomerUI {


    // Fields


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    Customer customer;

    // Constructor

    public CustomerUI() {
    }

    public void Start(){
       int userInput= promptCustomerID();
       switch (userInput){
           case 1://create personal acc
         //      DBinterface.createPersonalAccount(new User(),cu, AccountTypePolicy.PERSONAL,200);
       }
    }

    public  int promptCustomerID(){
        //TO DO
        // make this method force user select a number that is >1 otherwise return -1
        return 0;
    }

    public static void promptActions(Customer cu) {
        IO.println("create accounts [1-3] personal,isa,bussiness,check accounts,deposit withdraw,");
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