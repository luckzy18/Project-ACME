package org.example.model.people;

public class User {
    private final int tellerId;

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private String password;

    public void setRole(Role role) {
        this.role = role;
    }

    private  Role role;
    public User(int tellerId, String name, String password, Role role) {
        this.tellerId = tellerId;
        this.name = name;
        this.password = password;
        this.role = role;
    }
    public int getTellerId() {
        return tellerId;
    }
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    // this overrides base inherited class Object and replaces the return with a formatted string
    @Override
    public String toString() {
        return String.format(
                """
                        -- 🏦 ACME User 🏦 --
                            Teller: %d
                            Name: %s
                            Role: %s
                """,
                tellerId, name, role
        );
    }
}
