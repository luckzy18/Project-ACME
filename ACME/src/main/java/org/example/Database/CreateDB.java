package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDB {

    private static final String URL = "jdbc:sqlite:acmebank.db";

    public static Connection connect() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static void initialise() {
        String createTeller = """
                CREATE TABLE IF NOT EXISTS Teller (
                    teller_ID          TEXT PRIMARY KEY,
                    teller_Name       TEXT NOT NULL,
                    teller_Password   TEXT NOT NULL,
                    teller_role         TEXT NOT NULL
                );
                """;
        String createCustomers = """
                CREATE TABLE IF NOT EXISTS Customer (
                    customer_ID          TEXT PRIMARY KEY,
                    customer_name       TEXT NOT NULL,
                    customer_signup_date    TEXT NOT NULL,
                );
                """;
        String createAccount = """
                CREATE TABLE IF NOT EXISTS Account (
                    account_Number      VARCHAR PRIMARY KEY,
                    customer_ID     TEXT NOT NULL,
                    sort_code       TEXT NOT NULL,
                    balance         TEXT NOT NULL,
                    account_expiry_date     TEXT NOT NULL,
                    account_start_date      TEXT NOT NULL,
                    is_active TEXT NOT NULL,
                    FOREIGN KEY (customer_ID) REFERENCES CUSTOMER(customer_id)
                );
                """;
        String createPersonalAccount= """
                CREATE TABLE IF NOT EXISTS PersonalACC (
                    account_Number TEXT NOT NULL,
                    photo_ID_Verified Boolean,
                    FOREIGN KEY (account_number) REFERENCES Account(account_number)
                );""";
        String createISAAccount= """
                CREATE TABLE IF NOT EXISTS PersonalACC (
                    account_Number TEXT NOT NULL,
                    photo_ID_Verified Boolean
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createCustomers);
            System.out.println("Customers.");
            stmt.execute(createTeller);
            System.out.println("tellers.");
            stmt.execute(createAccount);
            System.out.println("account");
            stmt.execute(createPersonalAccount);
            IO.println("personal account created");
            stmt.execute(createISAAccount);
            System.out.println("Tables created successfully.");

        } catch (Exception e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}