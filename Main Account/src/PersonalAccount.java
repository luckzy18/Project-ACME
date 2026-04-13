import java.util.ArrayList;

public class PersonalAccount extends Account {

    // ***************************
    // Fields
    // ***************************
    private ArrayList<DirectDebit> directDebits;
    private boolean overdraftEnabled;
    private double overdraftLimit;

    // ***************************
    // Constructor
    // ***************************
    public PersonalAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, "60-60-60", balance, customerId);
        this.directDebits = new ArrayList<>();
        this.overdraftEnabled = false;
        this.overdraftLimit = 0.0;
    }

    // ***************************
    // Methods
    // ***************************
    public void enableOverdraft(double limit) {
        if (limit > 0) {
            overdraftEnabled = true;
            overdraftLimit = limit;
            addTransaction("Overdraft enabled with limit: £" + limit);
            System.out.println("Overdraft enabled successfully.");
        } else {
            System.out.println("Invalid overdraft limit.");
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }

        if (overdraftEnabled) {
            if (amount > balance + overdraftLimit) {
                System.out.println("Amount exceeds overdraft limit.");
                return false;
            }
        } else {
            if (amount > balance) {
                System.out.println("Not enough money in the account.");
                return false;
            }
        }

        balance -= amount;
        addTransaction("Withdrew: £" + amount + " | New Balance: £" + balance);
        System.out.println("Withdrawal successful.");
        return true;
    }

    public void addDirectDebit(DirectDebit directDebit) {
        directDebits.add(directDebit);
        addTransaction("Direct Debit added: " + directDebit.getCompanyName() + " | Amount: £" + directDebit.getAmount());
        System.out.println("Direct Debit added successfully.");
    }

    public void viewDirectDebits() {
        if (directDebits.isEmpty()) {
            System.out.println("No direct debits found.");
            return;
        }

        for (DirectDebit directDebit : directDebits) {
            directDebit.displayDirectDebit();
            System.out.println("--------------------");
        }
    }

    public void cancelDirectDebit(String reference) {
        for (DirectDebit directDebit : directDebits) {
            if (directDebit.getReference().equalsIgnoreCase(reference)) {
                directDebit.cancelDirectDebit();
                addTransaction("Direct Debit cancelled: " + reference);
                System.out.println("Direct Debit cancelled successfully.");
                return;
            }
        }

        System.out.println("Direct Debit not found.");
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Personal Account");
        showBasicAccountInfo();
        System.out.println("Overdraft Enabled: " + overdraftEnabled);
        System.out.println("Overdraft Limit: £" + overdraftLimit);
    }
}