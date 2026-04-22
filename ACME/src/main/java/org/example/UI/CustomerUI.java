package org.example.UI;

import org.example.Database.DBinterface;
import org.example.model.Account.Account;
import org.example.model.Account.AccountTypePolicy;
import org.example.model.Transaction;
import org.example.model.people.Customer;
import org.example.model.people.User;
import org.example.model.Transaction;
import java.util.List;
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
        Customer cust;
        do{
            int customerId=promptCustomerID();
            //prompt to get id details should add a break to come out
            // a return statement would work better to exit
            IO.println("finding customer");
            cust= DBinterface.getCustomerbyID(customerId, this.teller);
        }while(cust==null);
        setCustomer(cust);
        int actionInput;
        do{
            actionInput=promptActions(this.customer);
            performAction(actionInput);
        }while(actionInput!=6);//back to main menu input

    }


    private void createACC() {

        IO.println("1 personal, 2 business, 3 ISA");
        int userInput = sc.nextInt();
        sc.nextLine();

        IO.println("Client first deposit: ");
        double startingMoney = sc.nextDouble();
        sc.nextLine();

        AccountTypePolicy acc;

        switch (userInput) {
            case 1 -> {
                IO.println("creating personal Account.");
                acc = AccountTypePolicy.PERSONAL;
                Account a = DBinterface.createPersonalAccount(this.teller, customer, acc, startingMoney);
                IO.println(a.toString());
            }
            case 2 -> {
                IO.println("business type: ");
                String businessType = sc.nextLine().strip();
                IO.println("creating Business Account.");
                acc = AccountTypePolicy.BUSINESS;
                Account a = DBinterface.createBusinessAccount(this.teller, customer, acc, startingMoney, businessType);
                IO.println(a.toString());
            }
            case 3 -> {
                IO.println("creating ISA Account.");
                acc = AccountTypePolicy.ISA;
                Account a = DBinterface.createISAAccount(this.teller, customer, acc, startingMoney);
                IO.println(a.toString());
            }
        }
    }
    private Account promptSelectAccount() {

        // 1. Fetch accounts for this customer
        List<Account> accounts = DBinterface.getBankAccounts(customer);

        if (accounts == null || accounts.isEmpty()) {
            IO.println("This customer has no accounts.");
            return null;
        }

        IO.println("\n=== Select an Account ===");

        // 2. Display accounts with index numbers
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            IO.println((i + 1) + ". " + acc.getAccountNumber() + " | Balance: " + acc.getBalance());
        }

        IO.println("0. Cancel");

        // 3. Input loop

        int choice;
        boolean invalid = true;

        while (invalid) {
            IO.println("Choose an account (0 to cancel):");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);

                if (choice == 0) {
                    IO.println("Cancelled account selection.");
                    return null;
                }

                if (choice >= 1 && choice <= accounts.size()) {
                    Account selected = accounts.get(choice - 1);
                    IO.println("Selected account: " + selected.getAccountNumber());
                    return selected;
                }

                IO.println("Invalid option. Please choose a valid account number.");

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a number.");
            }
        }

        return null; // unreachable but required
    }



    public void performAction(int actionInput) {

        switch (actionInput) {

            case 1 -> {
                // Create a new account for this customer
                IO.println("\n--- Create New Account ---");
                createACC();
            }

            case 2 -> {
                // View all accounts belonging to this customer
                IO.println("\n--- Customer Accounts ---");
                List<Account> accounts = DBinterface.getBankAccounts(customer);

                if (accounts == null || accounts.isEmpty()) {
                    IO.println("This customer has no accounts.");
                } else {
                    for (Account acc : accounts) {
                        IO.println(acc.toString());
                    }
                }
            }

            case 3 -> {
                // Deposit into an account
                IO.println("\n--- Deposit ---");
                Account acc = promptSelectAccount();
                if (acc != null) {
                    double depositAmount=getDepositAmount();
                    if(depositAmount <=0){
                        IO.println("Deposit cancelled.");
                        break;}
                    Transaction t=new Transaction(Transaction.TransactionType.DEPOSIT, acc.getAccountNumber(), depositAmount,acc.getBalance());
                    t.performTransaction(acc, teller.getTellerId());
                    IO.println("Deposit successful. New balance: " + acc.getBalance());
                }
            }

            case 4 -> {
                // Withdraw from an account
                IO.println("\n--- Withdraw ---");
                Account acc = promptSelectAccount();
                if (acc != null) {
                    IO.println(acc.toString());
                    double amount=getWithdrawAmount(acc);
                    boolean ok = acc.withdraw(amount);
                    if (ok) {
                        IO.println("Withdrawal successful. New balance: " + acc.getBalance());
                    } else {
                        IO.println("Withdrawal failed. Check funds or account rules.");
                    }
                }
            }


            case 5 -> {
                // Delete an account
                IO.println("\n--- Delete Account ---");
                Account acc = promptSelectAccount();
                if (acc != null) {
               //     boolean deleted = DBinterface.deleteAccount(acc.getAccountNumber());
                    boolean deleted=false;
                    if (deleted) {
                        IO.println("Account deleted successfully.");
                    } else {
                        IO.println("Unable to delete account. It may still be active or restricted.");
                    }
                }
            }

            case 6 -> {
                IO.println("\n=== Payment Options ===");
                IO.println("1. Create Transfer");
                IO.println("2. Create Direct Debit");
                IO.println("3. Create Standing Order");
                IO.println("0. Return to previous menu");

                IO.print("Choose an option: ");
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1" -> createTransferUI(customer);
                    case "2" -> createDirectDebitUI(customer);
                    case "3" -> createStandingOrderUI(customer);
                    case "4" -> cancelStandingOrderUI();
                    case "5" -> cancelDirectDebitUI();
                    case "0" -> IO.println("Returning to previous menu.");
                    default -> IO.println("Invalid option.");
                }
            }
            case 7 -> {
                Account acc = promptSelectAccount();
                if (acc != null) {
                    showTransactionsUI(acc);
                }
            }case 8 -> {
                // Back to previous menu
                IO.println("Returning to previous menu.");
            }

            default -> {
                IO.println("Invalid action. Please choose a valid option.");
            }
        }
    }

    private void createStandingOrderUI(Customer customer) {
        IO.println("\n=== Create Standing Order ===");

        // Select FROM account
        Account fromAcc = promptSelectAccount();
        if (fromAcc == null) {
            IO.println("Standing order creation cancelled.");
            return;
        }

        String from = fromAcc.getAccountNumber();

        // Enter TO account
        IO.print("To account number: ");
        String to = sc.nextLine().trim();

        // Amount (reuse withdraw-limit logic)
        IO.println("Enter standing order amount:");
        double amount = getWithdrawAmount(fromAcc);
        if (amount <= 0) {
            IO.println("Standing order cancelled.");
            return;
        }

        // Frequency
        IO.print("Frequency (monthly/weekly/yearly): ");
        String freq = sc.nextLine().trim();

        // First payment date
        IO.print("First payment date (YYYY-MM-DD): ");
        String date = sc.nextLine().trim();

        // Reference
        IO.print("Reference: ");
        String ref = sc.nextLine().trim();

        boolean ok = DBinterface.insertStandingOrder(from, to, amount, freq, date, ref);

        if (ok) IO.println("Standing order created successfully.");
        else IO.println("Failed to create standing order.");
    }

    private void createDirectDebitUI(Customer customer) {
        IO.println("\n=== Create Direct Debit ===");

        // Select account to charge
        Account acc = promptSelectAccount();
        if (acc == null) {
            IO.println("Direct debit creation cancelled.");
            return;
        }

        String accountNumber = acc.getAccountNumber();

        // Merchant
        IO.print("Merchant name: ");
        String merchant = sc.nextLine().trim();

        // Amount (reuse withdraw-limit logic)
        IO.println("Enter direct debit amount:");
        double amount = getWithdrawAmount(acc);
        if (amount <= 0) {
            IO.println("Direct debit cancelled.");
            return;
        }

        // Frequency
        IO.print("Frequency (monthly/weekly/yearly): ");
        String freq = sc.nextLine().trim();

        // First payment date
        IO.print("First payment date (YYYY-MM-DD): ");
        String date = sc.nextLine().trim();

        boolean ok = DBinterface.insertDirectDebit(accountNumber, merchant, amount, freq, date);

        if (ok) IO.println("Direct debit created successfully.");
        else IO.println("Failed to create direct debit.");
    }

    private void showTransactionsUI(Account acc) {
        IO.println("\n=== Transaction History for Account " + acc.getAccountNumber() + " ===");

        List<String> tx = DBinterface.getTransactionsForAccount(acc.getAccountNumber());

        if (tx.isEmpty()) {
            IO.println("No transactions found for this account.");
            return;
        }

        for (String line : tx) {
            IO.println(line);
        }
    }



    private double getWithdrawAmount(Account acc) {
        double balance = acc.getBalance();
        double maxOverdraft = acc.getOverdraft().getMaxOverdraft();
        double overdraftUsed = acc.getOverdraft().getOverdraftBalance();

        double availableOverdraft = maxOverdraft - overdraftUsed;
        double limit = balance + availableOverdraft;

        IO.println("\n===  Amount ===");
        IO.println("Current balance: £" + balance);
        IO.println("Available overdraft: £" + availableOverdraft);
        IO.println("Funds limit: £" + limit);
        IO.println("Enter 0 to cancel withdrawal.");

        Scanner sc = new Scanner(System.in);
        double amount;

        while (true) {
            IO.print("Enter amount: ");
            String input = sc.nextLine().trim();

            // Exit option
            if (input.equals("0")) {
                IO.println(" cancelled.");
                return 0;   // Caller can check for 0 to detect cancellation
            }

            try {
                amount = Double.parseDouble(input);

                if (amount <= 0) {
                    IO.println("Amount must be greater than zero, or enter 0 to cancel.");
                } else if (amount > limit) {
                    IO.println("Amount exceeds your  limit of £" + limit);
                } else {
                    return amount;
                }

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid number.");
            }
        }
    }




    public int promptCustomerID() {
        IO.println("\n=== 🔍 Customer Lookup ===");
        IO.println("Enter a customer ID to continue.");
        IO.println("Or enter 0 to go back.");


        int id = -1;
        boolean invalid = true;

        while (invalid) {
            IO.println("Customer ID (0 to exit):");
            String input = sc.nextLine();

            try {
                id = Integer.parseInt(input);

                if (id == 0) {
                    IO.println("Returning to previous menu.");
                    return 0;
                }

                if (id > 0) {
                    IO.println("You entered customer ID: " + id);
                    invalid = false;
                } else {
                    IO.println("ID must be a positive number. Try again.");
                }

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid number.");
            }
        }

        return id;
    }
    private double getDepositAmount() {
        IO.println("Enter 0 or negative value to cancel.");
        IO.println("Please enter Deposit amount: £");
        while (true) {
            String input = sc.nextLine().trim();
            if (!input.matches("^\\d+$") &&               // whole number
                    !input.matches("^\\d+\\.\\d{2}$")) {      // decimal with exactly 2 digits
                IO.println("Invalid format. Use whole numbers or decimals with exactly 2 digits (e.g., 10.50). Try again:");
                continue;
            }
            try {
                 double amount=Double.parseDouble(input);
                 return amount;

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid number:");
            }
        }
    }




    private int promptActions(Customer cu) {
        String header = "\n=== 🧾 Customer Account Actions ===\nCustomer: " + cu.getName();
        String menu = """
            --- Available Actions ---
            1. Create new account
            2. View customer's accounts
            3. Deposit into an account
            4. Withdraw from an account
            5. Delete an account
            6. Transfers/standing orders/Direct debits
            7.View account
            8.Back to main menu
            """;

        IO.println(header);
        IO.println(menu);

        int choice = -1;
        boolean invalid = true;

        while (invalid) {
            IO.println("Select an option (1-8):");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= 8) {
                    IO.println("You chose: " + choice);
                    invalid = false;
                } else {
                    IO.println("Invalid option. Please choose between 1 and 8.");
                }

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a number.");
            }
        }

        return choice;
    }

private Customer searchForCustomer(){


    IO.println("Please enter customer ID: ");
    String input=sc.nextLine();
    while(!isInt(input)){
        IO.println("Invalid input");
        IO.println("Please enter a customer ID: ");
    }
    int inp=Integer.parseInt(input);
    Customer cust=DBinterface.getCustomerbyID(inp, this.teller);


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

    public Customer createNewCustomer() {
        IO.println("Please enter customer full name: ");
        String name=sc.next();
        sc.nextLine();
        IO.println("!IMPORTANT");
        IO.println("Please verify the customer's address and a valid goverment ID.");
        IO.println("Has the customer brought both documents?[y/n]: ");
        String input=sc.next();
        sc.nextLine();
        if(input.equalsIgnoreCase("y")){
           return DBinterface.insertCustomer(name,true,true);
        }
        return null;
    }
    private void createTransferUI(Customer cu) {
        IO.println("\n=== Create Transfer ===");

        IO.print("From account number: ");

        Account acc = promptSelectAccount();
        if(acc==null){
            IO.println("transfer cancelled.");
            return;
        }
        String from=acc.getAccountNumber();

        IO.print("To account number: ");// need to make sure it's the right lenght
        String to = sc.nextLine().trim();

        IO.print("Amount: ");
        double amount = getWithdrawAmount(acc);

        IO.print("Reference: ");
        String ref = sc.nextLine().trim();

        boolean ok = DBinterface.insertTransfer(from, to, amount, ref);

        if (ok) IO.println("Transfer created successfully.");
        else IO.println("Failed to create transfer.");
    }
    private void cancelStandingOrderUI() {
        IO.println("\n=== Cancel Standing Order ===");

        while (true) {
            IO.print("Enter Standing Order ID to cancel (0 to exit): ");
            String input = sc.nextLine().trim();

            // Cancel option
            if (input.equals("0")) {
                IO.println("Cancellation aborted.");
                return;
            }

            // Validate integer
            int soId;
            try {
                soId = Integer.parseInt(input);
                if (soId < 0) {
                    IO.println("ID cannot be negative. Try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid numeric ID.");
                continue;
            }

            // Attempt cancellation
            boolean ok = DBinterface.cancelStandingOrder(soId);

            if (ok) {
                IO.println("Standing order cancelled successfully.");
            } else {
                IO.println("No standing order found with that ID or cancellation failed.");
            }

            return;
        }
    }
    private void cancelDirectDebitUI() {
        IO.println("\n=== Cancel Direct Debit ===");

        while (true) {
            IO.print("Enter Direct Debit ID to cancel (0 to exit): ");
            String input = sc.nextLine().trim();

            // Cancel option
            if (input.equals("0")) {
                IO.println("Cancellation aborted.");
                return;
            }

            // Validate integer
            int ddId;
            try {
                ddId = Integer.parseInt(input);
                if (ddId < 0) {
                    IO.println("ID cannot be negative. Try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid numeric ID.");
                continue;
            }

            // Attempt cancellation
            boolean ok = DBinterface.cancelDirectDebit(ddId);

            if (ok) {
                IO.println("Direct debit cancelled successfully.");
            } else {
                IO.println("No direct debit found with that ID or cancellation failed.");
            }

            return;
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
