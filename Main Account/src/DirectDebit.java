public class DirectDebit {

    // ***************************
    // Fields
    // ***************************
    private String reference;
    private String companyName;
    private double amount;
    private int paymentDay;
    private boolean active;

    // ***************************
    // Constructor
    // ***************************
    public DirectDebit(String reference, String companyName, double amount, int paymentDay) {
        this.reference = reference;
        this.companyName = companyName;
        this.amount = amount;
        this.paymentDay = paymentDay;
        this.active = true;
    }

    // ***************************
    // Getters
    // ***************************
    public String getReference() {
        return reference;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getAmount() {
        return amount;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public boolean isActive() {
        return active;
    }

    // ***************************
    // Methods
    // ***************************
    public void cancelDirectDebit() {
        active = false;
    }

    // ***************************
    // Display Method
    // ***************************
    public void displayDirectDebit() {
        System.out.println("Reference: " + reference);
        System.out.println("Company Name: " + companyName);
        System.out.println("Amount: £" + amount);
        System.out.println("Payment Day: " + paymentDay);
        System.out.println("Active: " + active);
    }
}