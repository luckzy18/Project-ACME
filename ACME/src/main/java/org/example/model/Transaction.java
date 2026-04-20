package org.example.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.example.Database.DBinterface;
import org.example.UI.TellerUI;
import org.example.logger.LogType;
import org.example.logger.Logger;
import org.example.model.Account.Account;

public class Transaction {
    //Enums - just safer than using strings.
    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER//Transfer is no one action
    }

    public enum TransactionStatus {
        COMPLETE,
        FAILED
    }

    //Fields
    //Final is used for values that should be unchangeable after the transaction is created.
    private final String transactionID;
    private final TransactionType type;
    private TransactionStatus status;
    private String accountNumber;
    private final double amount;
    private final double balanceBefore;
    private double balanceAfter;
    private final LocalDateTime timestamp;
    private String updates;
    private String linkedAccountNumber; //For transferring between two accounts.

    //Constructor
    public Transaction(TransactionType type, String accountNumber, double amount, double balanceBefore) {
        this.transactionID = UUID.randomUUID().toString();
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.timestamp = LocalDateTime.now();
    }

    //Core method
    public boolean performTransaction(Account account, int tellerId) {
        if (!account.getAccountNumber().equals(this.accountNumber)) {
            this.status = TransactionStatus.FAILED;
            this.updates = "Account number doesn't match.";
            DBinterface.postLogToDB(new Logger(
                    LogType.WARNING,
                    "Transaction Failed: Account Mismatch",
                    "performTransaction",
                    tellerId,
                    account.getCustomerID(),
                    this.accountNumber
            ));
            return false;
        }
        try {
            switch (type) {
                case DEPOSIT:
                    account.deposit(amount);
                    break;

                case WITHDRAW:
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
            this.updates = "Error! Transaction failed.";
            e.printStackTrace();//Changed to be consistent for now, will circle back to remove later.
            return false;
        }
    }

    //Transfer method
    public boolean performTransfer(Account senderAccount, Account recipientAccount) {
        if (senderAccount == null || recipientAccount == null) {//I don't particulary like the sender/recipient names so I'm open to suggestions.
            this.status = TransactionStatus.FAILED;
            this.updates = "Transfer invalid. Check accounts are correct.";
            return false;
        }
        try {
            senderAccount.withdraw(amount);
            recipientAccount.deposit(amount);

            this.balanceAfter = senderAccount.getBalance();
            this.linkedAccountNumber = recipientAccount.getAccountNumber();
            this.status = TransactionStatus.COMPLETE;
            this.updates = "Transfer successful.";
            return true;

        } catch (Exception e) {
            this.status = TransactionStatus.FAILED;
            this.updates = "Error! Transfer failed.";
            e.printStackTrace();
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

    public String getAccountNumber() {
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

    public String getLinkedAccountNumber() {
        return linkedAccountNumber;
    }


    //Setters for linked account + updates
    public void setUpdates(String updates) {
        this.updates = updates;
    }

    public void setLinkedAccountNumber(String linkedAccountNumber) {
        this.linkedAccountNumber = linkedAccountNumber;
    }

    //Only necessary for testing if we want to print out transactions.
    @Override
    public String toString() {
        return String.format(
                "Transaction{id='%s', account=%s, type=%s, amount=£%.2f, before=£%.2f, after=£%.2f, status=%s, time=%s}",
                transactionID, accountNumber, type, amount, balanceBefore, balanceAfter, status, timestamp
        );
    }
}
