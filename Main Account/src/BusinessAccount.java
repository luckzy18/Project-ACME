public class BusinessAccount extends Account {
    // ***************************
    // Fields
    // ***************************
    private String businessType;
    private boolean chequeBookIssued;
    private boolean overdraftEnabled;
    private boolean internationalTradingEnabled;
    private boolean loanActive;

    // ***************************
    // Constant
    // ***************************
    private static final double ANNUAL_FEE = 120.0;

    // ***************************
    // Constructor
    // ***************************
    public BusinessAccount(String accountNumber, double balance, String customerId, String businessType) {
        super(accountNumber, "60-70-70", balance, customerId);
        this.businessType = businessType;
        this.chequeBookIssued = false;
        this.overdraftEnabled = false;
        this.internationalTradingEnabled = false;
        this.loanActive = false;
    }

    // ***************************
    // Getter
    // ***************************
    public String getBusinessType() {
        return businessType;
    }

    // ***************************
    // Methods
    // ***************************
    public boolean isValidBusinessType() {
        if (businessType.equalsIgnoreCase("Enterprise") ||
                businessType.equalsIgnoreCase("PLC") ||
                businessType.equalsIgnoreCase("Charity") ||
                businessType.equalsIgnoreCase("Public Sector")) {
            return false;
        }
        return true;
    }

    public void issueChequeBook() {
        if (chequeBookIssued) {
            System.out.println("Cheque book has already been issued.");
        } else {
            chequeBookIssued = true;
            addTransaction("Cheque book issued.");
            System.out.println("Cheque book issued successfully.");
        }
    }

    public void enableOverdraft() {
        overdraftEnabled = true;
        addTransaction("Overdraft enabled.");
        System.out.println("Overdraft enabled successfully.");
    }

    public void enableInternationalTrading() {
        internationalTradingEnabled = true;
        addTransaction("International trading enabled.");
        System.out.println("International trading enabled successfully.");
    }

    public void requestLoan() {
        loanActive = true;
        addTransaction("Loan request approved.");
        System.out.println("Loan request approved.");
    }

    public void applyAnnualFee() {
        if (balance >= ANNUAL_FEE) {
            balance -= ANNUAL_FEE;
            addTransaction("Annual fee applied: £" + ANNUAL_FEE + " | New Balance: £" + balance);
            System.out.println("Annual fee applied successfully.");
        } else {
            System.out.println("Not enough balance to apply the annual fee.");
        }
    }

    @Override
    public void displayAccountDetails() {
        System.out.println("Business Account");
        showBasicAccountInfo();
        System.out.println("Business Type: " + businessType);
        System.out.println("Cheque Book Issued: " + chequeBookIssued);
        System.out.println("Overdraft Enabled: " + overdraftEnabled);
        System.out.println("International Trading Enabled: " + internationalTradingEnabled);
        System.out.println("Loan Active: " + loanActive);
        System.out.println("Annual Fee: £" + ANNUAL_FEE);
    }
}