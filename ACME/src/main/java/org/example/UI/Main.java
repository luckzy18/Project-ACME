package org.example.UI;


import java.util.ArrayList;
import java.util.Scanner;
import org.example.UI.*;
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        // Sample Teller Data

        ArrayList<Teller> tellers = new ArrayList<>();
        tellers.add(new Teller("T001", "pass123"));
        tellers.add(new Teller("T002", "bank456"));


        // Sample Customer Data

        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("C001", "Rianne"));
        customers.add(new Customer("C002", "Devon"));
        customers.add(new Customer("C003", "Sarah"));
        customers.add(new Customer("C004", "Nathan"));



        // Teller Login Prompt

        System.out.println("***********************************");
        System.out.println("      ACME BANK TELLER SYSTEM");
        System.out.println("***********************************");
        System.out.print("Please enter Teller ID: ");
        String enteredTellerId = scanner.nextLine();

        System.out.print("Please enter Password: ");
        String enteredPassword = scanner.nextLine();

        boolean loginSuccess = false;


        // Check Teller Login

        for (Teller teller : tellers) {
            if (teller.getTellerId().equalsIgnoreCase(enteredTellerId)
                    && teller.getPassword().equals(enteredPassword)) {
                loginSuccess = true;
                break;
            }
        }


        // Teller Login Result

        if (!loginSuccess) {
            System.out.println();
            System.out.println("Login failed. Invalid Teller ID or Password, Please Try Again.");
            scanner.close();
            return;
        }

        System.out.println();
        System.out.println("Login successful.");
        System.out.println("Welcome to the ACME Bank .");


        // Customer ID Input Prompt

        System.out.println();
        System.out.print("Please enter Customer ID: ");
        String enteredCustomerId = scanner.nextLine();

        Customer foundCustomer = null;


        // Check Customer ID

        for (Customer customer : customers) {
            if (customer.getCustomerId().equalsIgnoreCase(enteredCustomerId)) {
                foundCustomer = customer;
                break;
            }
        }


        // Customer Found / Not Found

        if (foundCustomer != null) {
            System.out.println();
            System.out.println("************************************");
            System.out.println("        WELCOME TO ACME BANK");
            System.out.println("************************************");
            System.out.println("Customer login successful.");
            System.out.println("Welcome to ACME Bank, Customer ID: "+foundCustomer.getCustomerId());
            System.out.println("Customer Name: " + foundCustomer.getCustomerName());
            System.out.println();
            System.out.println("Customer Details:");
            foundCustomer.displayCustomerDetails();
        } else {
            System.out.println();
            System.out.println("Customer not found. Please try again.");
        }

        scanner.close();
    }
}