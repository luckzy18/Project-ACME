package org.example.UI;


import java.util.Scanner;

import org.example.Database.DBinterface;
import org.example.model.people.Role;
import org.example.model.people.User;
import org.example.model.people.Customer;



public class MainUI {


    private static final String logo= """
            ***********************************
                 ACME BANK TELLER SYSTEM
            ***********************************
            """;
    private User teller;
    private final CustomerUI cUI;
    Scanner sc=new Scanner(System.in);

    public MainUI(){
        this.teller=loginTeller();
        this.cUI=new CustomerUI();
    }

    public User getTeller() {
        return teller;
    }

    public void setTeller(User teller) {
        this.teller = teller;
    }

    public void start(){
        int input=-1;
        do{
            input=getMenuChoice(this.teller);
            performAction(input);
        }while(input!=5);
        IO.println("Shutting down");
    }





    User loginTeller(){//logs in teller
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccess = false;

        // Teller Login Prompt

        IO.println(logo);
        System.out.print("Please enter Teller ID: ");
        String enteredTellerId = scanner.nextLine();
        System.out.print("Please enter Password: ");
        String enteredPassword = scanner.nextLine();
        User user = null;
        int count=0;
        while(count <3 && !loginSuccess){
            count++;
             user=DBinterface.tellerTryLogin(enteredTellerId,enteredPassword);
            if(user != null){
                if(user.getRole()==Role.TEMPORARY){
                   if (!setUpUsername(user)) {
                        continue;
                   }
                }
                loginSuccess = true;
                break;
            }
            System.out.println("***********************************");
            IO.println("Incorrect name or password please try again.");
            System.out.print("Please enter Teller ID: ");
            enteredTellerId = scanner.nextLine();
            System.out.print("Please enter Password: ");
            enteredPassword = scanner.nextLine();
        }
        if(!loginSuccess){
            IO.println("too many bad attempts");
            System.exit(0);
        }
        return user;
    }

    private boolean setUpUsername(User user) {
        IO.println("WELCOME TO ACME BANK.");
        IO.println("What is your name?: ");
        String name=sc.nextLine();
        boolean updateSucess=DBinterface.updateTellerName(user,name);
        return updateSucess;
    }


    int getMenuChoice(User user){
            String greet="\n=== 🏦 ACME Banking System ===\nWelcome, " + user.getName();
            String customerActions= """
                                --- Customer Actions ---
                                1. Get customer
                                2. Create new customer
                                3. Remove customer
                                4. Log out
                                5. Exit
                    """;
            String adminActions= """
                                    --- Admin Actions ---
                                    6. Generate one-time login code
                                    7. View tellers
                                    8. Remove teller
                    """;
            IO.println(greet);
            IO.println(customerActions);
            if(user.isAdmin()){
                IO.println(adminActions);
            }
            boolean invalidOption=true;
            int choice=-1;
            while (invalidOption){
                IO.println("\nSelect an option:");
                if(user.isAdmin()){
                    IO.println("1-8");
                }else{
                    IO.println("1-5");
                }
                Scanner sc=new Scanner(System.in);
                String userChoice = sc.nextLine();

                try {
                    choice = Integer.parseInt(userChoice);

                    if (choice >= 1 && choice <= 5 && !user.isAdmin()) {
                        // valid input for non admin
                        System.out.println("You chose: " + choice);
                        invalidOption=false;
                    }else if (choice >= 1 && choice <= 8){// check for admin
                        System.out.println("You chose: " + choice);
                        invalidOption=false;
                    }else {
                        System.out.println(choice +" is not a valid input");
                        System.out.println("Please enter a valid input:.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            return choice;

    }

    void performAction(int choice) {
        switch (choice) {

            case 1 -> {
                // for anyone working on this please check method signature in dbinterface
                // the return values should be displayed to the user for a reason if you think some return values need altering either do it or ask for help
                // at the moment I had no time to test all methods if any do not work either fix or let me know
                cUI.start();
            }

            case 2 -> {

                // getCustomerbyID information retrieved is only about the customer account nothing about bank accounts
                IO.print("inserting Customer ");
                cUI.createNewCustomer();
                // insertCustomer gets an id back which is required for users to log in.
            }

            case 3 -> {
                IO.print("Remove Customer");
                // not fully working we can look at it once we can manage accounts
                // NEEDS TO BE MOVED INTO CUSTOMER MENU,
            }
            case 4 -> {// not tested
                IO.print("logged out");
                this.teller=loginTeller();
                // run the start screen again and have a goodbye message
            }

            case 5 -> {
                IO.print("Good bye. ");
            }

            // Could be worth to move the admin actions into a different class cleaning tasks
            case 6 -> {
                IO.print("Generating new login details:");
                String[] loginDetails = DBinterface.generateNewTeller();
                IO.println(String.format("New login details → Username: %s | Passcode: %s",
                        loginDetails[0], loginDetails[1]));

                IO.println("Please prompt the new teller to use the one time passcode and log in as quickly as possible.");
            }

            case 7 -> {
                IO.print("get all tellers");
                String[] tellers =DBinterface.getAllTellers();
                IO.println("Tellers: ");
                for (String teller: tellers){
                    IO.println(teller);
                }
                // getAllTellers in a readable format will fetch all tellers.
                // if you want to change the way this is displayed go into dbinterface
            }

            case 8 -> {
                IO.print("delete teller");
                boolean deletedTeller=deleteTeller();
                if(deletedTeller){
                    IO.println("Teller has been deleted.");
                }else{
                    IO.println("Teller has not been deleted.");
                }
                // deleteTellerbyID method deletes the teller if the id is inserted except for admin
            }

            default -> {
                IO.print("Invalid option.");
            }
        }
    }
    private boolean deleteTeller(){
        IO.println("please insert teller ID: ");
        int ID=sc.nextInt();
        User teller=DBinterface.getTeller(ID);
        IO.println(teller);
        IO.println("Are you sure you want to delete[y/n]: ");
        String input=sc.nextLine();

        if(input.strip().equalsIgnoreCase("y")){
            return DBinterface.deleteTellerByID(teller.getTellerId());
        }else{
            return false;
        }

    }
}


        // Check Teller Login




        // Teller Login Result

//        if (!loginSuccess) {
//            System.out.println();
//            System.out.println("Login failed. Invalid Teller ID or Password, Please Try Again.");
//            scanner.close();
//            return;
//        }
//
//        System.out.println();
//        System.out.println("Login successful.");
//        System.out.println("Welcome to the ACME Bank .");
//
//
//        // Customer ID Input Prompt
//
//        System.out.println();
//        System.out.print("Please enter Customer ID: ");
//        String enteredCustomerId = scanner.nextLine();
//
//        Customer foundCustomer = null;


        // Check Customer ID

//        for (Customer customer : customers) {
//            if (customer.getCustomerId().equalsIgnoreCase(enteredCustomerId)) {
//                foundCustomer = customer;
//                break;
//            }
//        }


        // Customer Found / Not Found

//        if (foundCustomer != null) {
//            System.out.println();
//            System.out.println("************************************");
//            System.out.println("        WELCOME TO ACME BANK");
//            System.out.println("************************************");
//            System.out.println("Customer login successful.");
//            System.out.println("Welcome to ACME Bank, Customer ID: "+foundCustomer.getCustomerId());
//            System.out.println("Customer Name: " + foundCustomer.getCustomerName());
//            System.out.println();
//            System.out.println("Customer Details:");
//            foundCustomer.displayCustomerDetails();
//        } else {
//            System.out.println();
//            System.out.println("Customer not found. Please try again.");
//        }
//
//        scanner.close();
