import java.util.ArrayList;

public abstract class Account {
    // ***************************
    // Fields
    // ***************************

    protected String accountNumber;
    protected String sortCode;
    protected double balance;
    protected String customerId;
    protected ArrayList<String> transactionHistory;

    // ***************************
    // Constructor
    // ***************************

    public Account(String accountNumber, String sortCode, double balance, String customerId) {
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.balance = balance;
        this.customerId = customerId;
        this.transactionHistory = new ArrayList<>();
    }
    // ***************************
    // Getters
    // ***************************

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getSortCode() {
        return sortCode;
    }

    public double getBalance() {
        return balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    // ***************************
    // Common Methods
    // ***************************

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        balance += amount;
        addTransaction("Deposited: £" + amount + " | New Balance: £" + balance);
        System.out.println("Deposit successful.");
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }

        if (amount > balance) {
            System.out.println("Not enough money in the account.");
            return false;
        }

        balance -= amount;
        addTransaction("Withdrew: £" + amount + " | New Balance: £" + balance);
        System.out.println("Withdrawal successful.");
        return true;
    }

    public void addTransaction(String message) {
        transactionHistory.add(message);
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("Transaction History:");
        for (int i = 0; i < transactionHistory.size(); i++) {
            System.out.println((i + 1) + ". " + transactionHistory.get(i));
        }
    }

    // ***************************
    //BASIC ACCOUNT INFORMATION
    // ***************************


    public void showBasicAccountInfo() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Sort Code: " + sortCode);
        System.out.println("Balance: £" + balance);
    }

    // Abstract Method
    public abstract void displayAccountDetails();
}