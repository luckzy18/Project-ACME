package org.example.UI;


import java.util.Scanner;

import org.example.Database.DBinterface;
import org.example.model.people.User;
import org.example.model.people.Customer;



public class MainUI {


    private static String logo= """
            ***********************************
                 ACME BANK TELLER SYSTEM
            ***********************************
            """;
    private User teller;
    private CustomerUI cUI;

    public MainUI(){
        this.teller=loginTeller();
        cUI=new CustomerUI();
    }

    public User getTeller() {
        return teller;
    }

    public void setTeller(User teller) {
        this.teller = teller;
    }

    public void start(){
        int input=getMenuChoice(this.teller);
        while(input !=5){
            input=getMenuChoice(this.teller);
            performAction();
        }
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

       void performAction() {
        //tellerFirstLoginUpdate has to be checked if name field is empty and be added to prompt teller to change password and add name
          int choice=getMenuChoice(teller);
          switch (choice){
              // for anyone working on this please check method signature in dbinterface
                // the return values should be displayed to the user for a reason if you think some return values need altering either do it or ask for help
             // at the moment I had no time to test all methods if any do not work either fix or let me know
              case 1:
                  int customerId=CustomerUI.promptCustomerID();//prompt to get id details
                  Customer cust= DBinterface.getCustomerbyID(customerId);
                  cUI.setCustomer(cust);
                  cUI.start();

                //  enterCustomerMenu(cust);
                  IO.print("Searching for customer");

                  break;
                  // getCustomerbyID information retrieved is only about the user account nothing about bank accounts
              case 2:
                  IO.print("inserting Customer ");
                //  insertCustomer gets an id back which is required for users to log in.
                  break;
              case 3:
                  IO.print("Remove Customer");
                  // not fully working we can look at it once we can manage accounts
                  // NEEDS TO BE MOVED INTO CUSTOMER MENU,
                  // PLEASE REMOVE THIS OPTION AND REFACTOR UI ACCORDINGLY TO REMOVE THIS OPTION FROM HERE
                  break;
              case 4:
                  IO.print("log out");
                  //run the start screen again and have a goodbye message
                  break;
              case 5:
                  IO.print("Exit");
                  //shut down the system with something like sys.exit(0) and a closing message
                  break;

             // Could be worth to move the admin actions into a different class cleaning tasks
              case 6:
                  IO.print("Generating new login details:");

                  String [] loginDetails=DBinterface.generateNewTeller();
                  break;
              case 7:
                  IO.print("get all tellers");
                  //getAllTellers in a readable format will fetch all tellers. if you want to change the way this is displayed go into dbinterface
              case 8:
                  IO.print("delete teller");
                  //deleteTellerbyID method deletes the teller if the id is inserted except for admin
          }
    }
    Customer getCustomer(){
        Scanner sc=new Scanner(System.in);

        IO.println("Please enter customer ID: ");
        String input=sc.nextLine();
        while(!isInt(input)){
            IO.println("Invalid input");
            IO.println("Please enter a customer ID: ");
        }
        int inp=Integer.parseInt(input);
        Customer cust=DBinterface.getCustomerbyID(inp);
        sc.close();

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
