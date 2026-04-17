package org.example.model.people;

public class Customer {
    private String name;
    private final int id;
    private final String dateJoined;
    private boolean addressVerified;
    private boolean idVerified;

    public String getName() {
        return name;
    }

    public Customer(String name, int id, String dateJoined, boolean addressVerified, boolean idVerified) {
        this.name = name;
        this.id = id;
        this.dateJoined = dateJoined;
        this.addressVerified = addressVerified;
        this.idVerified = idVerified;
    }
}
