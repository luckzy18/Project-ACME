public class IsaAccount extends Account {

    // ***************************
    // Constant
    // ***************************
    private static final double INTEREST_RATE = 0.0275;

    // ***************************
    // Constructor
    // ***************************

    public IsaAccount(String accountNumber, double balance, String customerId) {
        super(accountNumber, "60-60-70", balance, customerId);
    }

    // ***************************
    // Methods
    // ***************************

    public double calculateAnnualInterest() {
        return balance * INTEREST_RATE;
    }

    public void applyAnnualInterest() {
        double interest = calculateAnnualInterest();
        balance += interest;
        addTransaction("Annual ISA interest added: £" + interest + " | New Balance: £" + balance);
        System.out.println("Annual interest applied successfully.");
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("ISA Account");
        showBasicAccountInfo();
        System.out.println("Interest Rate: 2.75%");
    }
}