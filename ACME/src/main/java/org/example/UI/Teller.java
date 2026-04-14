package org.example.UI;
public class Teller {


    // *********************
    // Fields
    // *********************
    private String tellerId;
    private String password;

    // *********************
    // Constructor
    // *********************
    public Teller(String tellerId, String password) {
        this.tellerId = tellerId;
        this.password = password;
    }

    // **********************
    // Getters
    // **********************
    public String getTellerId() {
        return tellerId;
    }

    public String getPassword() {
        return password;
    }
}