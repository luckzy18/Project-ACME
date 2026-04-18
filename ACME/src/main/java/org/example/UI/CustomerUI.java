package org.example.UI;

import org.example.Database.DBinterface;
import org.example.model.Account.Account;
import org.example.model.Account.AccountTypePolicy;
import org.example.model.people.Customer;
import org.example.model.people.User;

import java.util.Scanner;

public class CustomerUI {


    // Fields
    private User teller;
    private Customer customer;
    private Scanner sc=new Scanner(System.in);

    // Constructor
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTeller(User teller) {
        this.teller=teller;
    }

    public void start(){
        Customer cust=null;
        do{
            int customerId=promptCustomerID();
            //prompt to get id details should add a break to come out
            // a return statement would work better to exit
            IO.println("finding customer");
            cust= DBinterface.getCustomerbyID(1);
        }while(cust==null);
        setCustomer(cust);
        int actionInput=-1;
        do{
            actionInput=promptActions(this.customer);
            performAction(actionInput);
        }while(actionInput!=-1);//exit input

    }


    private void createACC(){
        Scanner sc=new Scanner(System.in);
        IO.println("1 personal, 2 business, 3 ISA");
        int userInput=sc.nextInt();// make this choose between the types of account that can be created.
        sc.nextLine();
        IO.println("Client first deposit: ");
        double startingMoney=sc.nextDouble();
        sc.nextLine();

        AccountTypePolicy acc;


        switch (userInput){
            case 1->{
                IO.println("creating personal Account.");
                acc=AccountTypePolicy.PERSONAL;
                Account a=DBinterface.createPersonalAccount(this.teller,customer,acc,startingMoney);//
                IO.println(a.toString());
            }//create personal
            case 2->{
                IO.println("business type: ");
                String businessType=sc.nextLine();
                businessType=businessType.strip();
                IO.println("creating Business Account.");
                acc=AccountTypePolicy.BUSINESS;
                Account a=DBinterface.createBusinessAccount(this.teller,customer,acc,startingMoney,businessType);//
                IO.println(a.toString());
            }
            case 3->{
                IO.println("creating Business Account.");
                acc=AccountTypePolicy.ISA;
                Account a=DBinterface.createPersonalAccount(this.teller,customer,acc,10);//
                IO.println(a);
            }
        }

    }

public void performAction(int actionInput){
        // this method performs different actions based on promptaction chosen
        switch(actionInput){
            case 1 -> {
                createACC();
                IO.println("account created");
            }
            case 2 ->IO.println("do smt new");
        }
}

public  int promptCustomerID(){
    //TO DO
    // make this method force user select a number that is >0 otherwise return -1
    return 1;
}

private int  promptActions(Customer cu) {
    IO.println("create accounts [1-3] personal,isa,bussiness,check accounts,deposit withdraw,");
    Scanner sc=new Scanner(System.in);
    IO.println("1 for personal acc, -1 exit");
    int input=sc.nextInt();
    //this method will make the teller select from a number of actions
    //create account
    //delete account
    //getBankAccounts
    //once inside a bank account prompt have a few options of seeing standing orders and direct debits`
    //create and delete these standing orders
    // and a lot more that i cannot think of at the moment

    return input;
}
private Customer searchForCustomer(){
    Scanner sc=new Scanner(System.in);

    IO.println("Please enter customer ID: ");
    String input=sc.nextLine();
    while(!isInt(input)){
        IO.println("Invalid input");
        IO.println("Please enter a customer ID: ");
    }
    int inp=Integer.parseInt(input);
    Customer cust=DBinterface.getCustomerbyID(inp);


    return cust;
}
private static boolean isInt(String input) {
    try {
        Integer.parseInt(input);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}
}



// Getters

//    public String getCustomerId() {
//        return customerId;
//    }
//
//    public String getCustomerName() {
//        return customerName;
//    }
//
//
//    // Display Menu
//
//    public void displayCustomerDetails() {
//        System.out.println("Customer ID: " + customerId);
//        System.out.println("Customer Name: " + customerName);
//    }
