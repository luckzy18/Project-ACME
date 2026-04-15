package org.example.model;

public class Customer {
    private String name;
    private String id;
    private String dateJoined;
    private boolean addressVerified;
    private boolean idVerified;

    public String getName() {
        return name;
    }

    public Customer(String name, String id, String dateJoined, boolean addressVerified, boolean idVerified) {
        this.name = name;
        this.id = id;
        this.dateJoined = dateJoined;
        this.addressVerified = addressVerified;
        this.idVerified = idVerified;
    }
}
