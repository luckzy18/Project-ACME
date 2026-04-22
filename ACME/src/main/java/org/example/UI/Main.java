package org.example.UI;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HelpMenu helpMenu = new HelpMenu();

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

        // Start Menu
        System.out.println("***********************************");
        System.out.println("      ACME BANK TELLER SYSTEM");
        System.out.println("***********************************");
        System.out.println("1. Continue to Login");
        System.out.println("2. Help");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");

        int startChoice = scanner.nextInt();
        scanner.nextLine(); // clears the leftover newline

        if (startChoice == 2) {
            helpMenu.showHelpMenu();

            System.out.println();
            System.out.println("Returning to login...");
        } else if (startChoice == 3) {
            System.out.println("Exiting system.");
            scanner.close();
            return;
        } else if (startChoice != 1) {
            System.out.println("Invalid choice. Exiting system.");
            scanner.close();
            return;
        }

        // Teller Login Prompt
        System.out.println();
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
            System.out.println("Login failed. Invalid Teller ID or Password. Please try again.");
            scanner.close();
            return;
        }

        System.out.println();
        System.out.println("Login successful.");
        System.out.println("Welcome to ACME Bank.");

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
            System.out.println("Customer record found successfully.");
            System.out.println("Welcome to ACME Bank, Customer ID: " + foundCustomer.getCustomerId());
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