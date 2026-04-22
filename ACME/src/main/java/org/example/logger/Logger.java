package org.example.logger;

public class Logger {
    private final LogType type;
    private final String message;
    private final String source;
    private final int tellerId;
    private final Integer customerId; // use the object instead so it can be null
    private final String accountNumber;
    public Logger(LogType type, String message, String source, int tellerId, Integer customerId, String accountNumber) {
        this.type = type;
        this.message = message;
        this.source = source;
        this.tellerId = tellerId;
        this.customerId = customerId;
        this.accountNumber = accountNumber;
    }
    public LogType getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }
    public String getSource() {
        return source;
    }
    public int getTellerId() {
        return tellerId;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
}
