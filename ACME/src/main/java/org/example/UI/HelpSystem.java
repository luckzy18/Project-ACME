package org.example.UI;

public class HelpSystem {


        // Login Help

        public void showLoginHelp() {
            System.out.println();
            System.out.println("=========== LOGIN HELP ===========");
            System.out.println("Enter your Teller ID and password to access the system.");
            System.out.println("If the login fails, check the Teller ID and password and try again.");
            System.out.println("==================================");
        }


        // Customer Search Help

        public void showCustomerSearchHelp() {
            System.out.println();
            System.out.println("======= CUSTOMER SEARCH HELP =======");
            System.out.println("Enter the Customer ID to search for a customer.");
            System.out.println("If the customer is found, their details will be shown.");
            System.out.println("If the customer is not found, you will see an error message.");
            System.out.println("====================================");
        }


        // Personal Account Help

        public void showPersonalAccountHelp() {
            System.out.println();
            System.out.println("====== ACME PERSONAL ACCOUNT HELP ======");
            System.out.println("Personal accounts use sort code 60-60-60.");
            System.out.println("They support deposits, withdrawals, overdraft, and direct debits.");
            System.out.println("===================================");
        }

        // ISA Account Help

        public void showIsaHelp() {
            System.out.println();
            System.out.println("========= ACME ISA ACCOUNT HELP =========");
            System.out.println("ISA accounts use sort code 60-60-70.");
            System.out.println("They earn annual interest at 2.75%.");
            System.out.println("Only one ISA account is allowed per customer.");
            System.out.println("Direct debits are not allowed in this simplified version.");
            System.out.println("====================================");
        }


        // Business Account Help

        public void showBusinessHelp() {
            System.out.println();
            System.out.println("======= ACME BUSINESS ACCOUNT HELP =======");
            System.out.println("Business accounts use sort code 60-70-70.");
            System.out.println("They have an annual fee of £120.");
            System.out.println("Excluded business types are Enterprise, PLC, Charity, and Public Sector.");
            System.out.println("Business accounts may include cheque books, overdraft, loan requests,");
            System.out.println("and international trading options.");
            System.out.println("=====================================");
        }


        // General System Help

        public void showGeneralHelp() {
            System.out.println();
            System.out.println("========== ACME GENERAL HELP ==========");
            System.out.println("This system is used by bank tellers.");
            System.out.println("First, the teller logs in.");
            System.out.println("Then, the teller searches for a customer using a Customer ID.");
            System.out.println("After that, the teller can work with the customer's accounts.");
            System.out.println("==================================");
        }
    }

