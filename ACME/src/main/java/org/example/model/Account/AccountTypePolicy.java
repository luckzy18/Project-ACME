package org.example.model.Account;

public enum AccountTypePolicy {
    PERSONAL("60-60-60", AccountLimitPolicy.MULTIPLE),
    ISA("60-60-70", AccountLimitPolicy.SINGLE),
    BUSINESS("60-70-70",AccountLimitPolicy.MULTIPLE);
//    private static String personalACCString="60-60-60";
//    private static String ISAACCString;
//    private static String businessACCString;

    private final String sortCode;
    private final String accountLimit;

    AccountTypePolicy(String sortCode, AccountLimitPolicy accountLimitPolicy) {
        this.sortCode = sortCode;
        this.accountLimit = accountLimitPolicy.name();
    }

    public String getSortCode() {
        return sortCode;
    }

    public boolean allowsMultipleAccounts() {
        return accountLimit.equals(AccountLimitPolicy.MULTIPLE.name());
    }
}