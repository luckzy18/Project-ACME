package org.example.UI;


import java.util.Scanner;

import org.example.Database.DBinterface;
import org.example.model.User;

import static org.example.model.Role.ADMIN;

public class MainUI {

    public User getTeller() {
        return teller;
    }

    public void setTeller(User teller) {
        this.teller = teller;
    }

    private User teller;


    User loginTeller(){//logs in teller
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccess = false;
        // Teller Login Prompt



        System.out.println("***********************************");
        System.out.println("      ACME BANK TELLER SYSTEM");
        System.out.println("***********************************");
        System.out.print("Please enter Teller ID: ");
        String enteredTellerId = scanner.nextLine();
        System.out.print("Please enter Password: ");
        String enteredPassword = scanner.nextLine();
        User user = null;
        int count=0;
        while(count <3 && loginSuccess ==false){
             user=DBinterface.tellerTryLogin(enteredTellerId,enteredPassword);;
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
                    "--- Admin Actions ---");
                                    IO.println("6. Generate one-time login code");
                                    IO.println("7. View tellers");
                                    IO.println("8. Remove teller");
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
                        System.out.println("Please enter a valid number:.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
            return choice;

    }

      void main() {
        teller=loginTeller();
          int choice=getMenuChoice(teller);
          switch (choice){
              // for anyone working on this please check method signature in dbinterface
                // the return values should be displayed to the user for a reason if you think some return values need altering either do it or ask for help
              case 1:
                  IO.print("Searching for customer");
                  break;
                  // getCustomerbyID information retrieved is only about the user account nothing about banck acocunts
              case 2:
                  IO.print("inserting Customer ");
                //  insertCustomer gets an id back which is required for users to log in.
                  break;
              case 3:
                  IO.print("Remove Customer");
                  // not fully working we can look at it once we can manage accounts
                  break;
              case 4:
                  IO.print("log out");
                  //run the start screen again and have a goodbye message
                  break;
              case 5:
                  IO.print("Exit");
                  //shut down the system with something like sys.exit(0) and a clsoing message
                  break;

             // Could be worth to move the admin actions into a different class cleaning tasks
              case 6:
                  IO.print("generate the login details for a new user");
                  //generateNewTeller
                  break;
              case 7:
                  IO.print("get all tellers");
                  //this will fetch all tellers to allow admin to have a look and find them.
              case 8:
                  IO.print("delete teller");
                  //this will delete a teller
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
    }
}