package org.example.UI;

public class Customer {


    // Fields

    private String customerId;
    private String customerName;


    // Constructor

    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;

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