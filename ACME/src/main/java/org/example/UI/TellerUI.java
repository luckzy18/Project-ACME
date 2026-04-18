package org.example.UI;
public class TellerUI {


    // Fields

    private String tellerId;
    private String password;


    // Constructor

    public TellerUI(String tellerId, String password) {
        this.tellerId = tellerId;
        this.password = password;
    }


    // Getters

    public String getTellerId() {
        return tellerId;
    }

    public String getPassword() {
        return password;
    }
}