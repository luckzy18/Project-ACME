public class Main {

    public static void main(String[] args) {
        // ***************************
        // Create account objects
        // ***************************

        PersonalAccount personalAccount = new PersonalAccount("12345678", 500.0, "C001");
        IsaAccount isaAccount = new IsaAccount("23456789", 1000.0, "C002");
        BusinessAccount businessAccount = new BusinessAccount("34567890", 2000.0, "C003", "Sole Trader");

        // ***************************
        // PERSONAL ACCOUNT TEST
        // ***************************

        System.out.println("******************************");
        System.out.println("=== PERSONAL ACCOUNT TEST ===");
        System.out.println("******************************");

        personalAccount.displayAccountDetails();
        personalAccount.deposit(200);
        personalAccount.withdraw(100);
        personalAccount.enableOverdraft(300);

        DirectDebit dd1 = new DirectDebit("DD1001", "Netflix", 12.99, 15);
        DirectDebit dd2 = new DirectDebit("DD1002", "Spotify", 9.99, 20);

        personalAccount.addDirectDebit(dd1);
        personalAccount.addDirectDebit(dd2);

        System.out.println("\nDirect Debits:");
        personalAccount.viewDirectDebits();

        personalAccount.cancelDirectDebit("DD1001");

        System.out.println("\nTransaction History:");
        personalAccount.showTransactionHistory();

        System.out.println("\n=============================\n");

        // ***************************
        // ISA ACCOUNT TEST
        // ***************************

        System.out.println("******************************");
        System.out.println("=== ISA ACCOUNT TEST ===");
        System.out.println("******************************");

        isaAccount.displayAccountDetails();
        isaAccount.deposit(300);
        isaAccount.withdraw(150);
        isaAccount.applyAnnualInterest();

        System.out.println("\nTransaction History:");
        isaAccount.showTransactionHistory();

        System.out.println("\n=============================\n");

        // ***************************
        // BUSINESS ACCOUNT TEST
        // ***************************

        System.out.println("******************************");
        System.out.println("=== BUSINESS ACCOUNT TEST ===");
        System.out.println("******************************");

        if (businessAccount.isValidBusinessType()) {
            businessAccount.displayAccountDetails();
            businessAccount.deposit(500);
            businessAccount.withdraw(200);
            businessAccount.issueChequeBook();
            businessAccount.enableOverdraft();
            businessAccount.enableInternationalTrading();
            businessAccount.requestLoan();
            businessAccount.applyAnnualFee();

            System.out.println("\nTransaction History:");
            businessAccount.showTransactionHistory();
        } else {
            System.out.println("This business type is not allowed.");
        }
    }
}