package.org.example.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    //Enums - just safer than using strings.
    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER_IN,
        TRANSFER_OUT
    }

    public enum TransactionStatus {
        COMPLETE,
        FAILED,
    }

    //Fields
    //Final is used for values that should be unchangeable after the transaction is created.
    private final String transactionID;
    private final TransactionType type;
    private TransactionStatus status;
    private final int accountNumber;
    private final double amount;
    private final double balanceBefore;
    private double balanceAfter;
    private final LocalDateTime timestamp;
    private String updates;
    private Integer linkedAccountNumber; //For transferring between two accounts.

    //Constructor
    public Transaction(String transactionID, TransactionType type, int accountNumber, double amount, double balanceBefore, LocalDateTime timestamp) {
        this.transactionID = UUID.randomUUID().toString();
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.timestamp = LocalDateTime.now();
    }

    //Core method
    public boolean performTransaction(Account account) {
        if (account.getAccountNumber() != this.accountNumber) {
            this.status = TransactionStatus.FAILED;
            this.updates = "Account number doesn't match.";
            return false;
        }
        try {
            switch (type) {
                case DEPOSIT:
                case TRANSFER_IN://Both cases share logic.
                    account.deposit(amount);
                    break;

                case WITHDRAW:
                case TRANSFER_OUT:
                    account.withdraw(amount);
                    break;

                default:
                    this.status = TransactionStatus.FAILED;
                    this.updates = "Invalid transaction type.";
                    return false;
            }
            //Update result
            this.balanceAfter = account.getBalance();
            this.status = TransactionStatus.COMPLETE;
            this.updates = "Transaction successful";
            return true;

        } catch (Exception e) {
            this.status = TransactionStatus.FAILED;
            this.updates = "Exception: " + e.getMessage();
            return false;
        }
    }

    /*
    Placeholder until I've added to the DBinterface
    Pass to DBinterface once the transactions table exists
     */

    //Getters
    public String getTransactionID() {
        return transactionID;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUpdates() {
        return updates;
    }

    public Integer getLinkedAccountNumber() {
        return linkedAccountNumber;
    }


    //Setters for linked account + updates
    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public void setLinkedAccountNumber(Integer linkedAccountNumber) {
        this.linkedAccountNumber = linkedAccountNumber;
    }

    //Only necessary for testing if we want to print out transactions.
    @Override
    public String toString() {
        return String.format(
                "Transaction{id='%s', account=%d, type=%s, amount=£%.2f, before=£%.2f, after=£%.2f, status=%s, time=%s}",
                transactionID, accountNumber, type, amount, balanceBefore, balanceAfter, status, timestamp
        );
    }
}
