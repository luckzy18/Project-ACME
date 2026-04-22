package org.example.UI;

import java.util.Scanner;

public class HelpMenu {

    private HelpSystem helpSystem;

    // Constructor
    public HelpMenu() {
        this.helpSystem = new HelpSystem();
    }

    // Help Menu
    public void showHelpMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("====================================");
            System.out.println("             ACME HELP MENU");
            System.out.println("====================================");
            System.out.println("1. General Help");
            System.out.println("2. Login Help");
            System.out.println("3. Customer Search Help");
            System.out.println("4. Personal Account Help");
            System.out.println("5. ISA Account Help");
            System.out.println("6. Business Account Help");
            System.out.println("7. Exit Help Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    helpSystem.showGeneralHelp();
                    break;
                case 2:
                    helpSystem.showLoginHelp();
                    break;
                case 3:
                    helpSystem.showCustomerSearchHelp();
                    break;
                case 4:
                    helpSystem.showPersonalAccountHelp();
                    break;
                case 5:
                    helpSystem.showIsaHelp();
                    break;
                case 6:
                    helpSystem.showBusinessHelp();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting help menu.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

