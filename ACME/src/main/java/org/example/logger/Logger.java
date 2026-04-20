package org.example.logger;

public class Logger {
    private final String message;
    private final String Source;
    private final int tellerId;
    private final Integer customerId; // use the object instead so it can be null
    private final String accountNumber;
    public Logger(LogType type, String message, String source, int tellerId, Integer customerId, String accountNumber) {
        this.message = message;
        this.Source = source;
        this.tellerId = tellerId;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
    }
}
