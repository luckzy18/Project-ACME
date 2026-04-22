package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

public class CreateDB {

    private static final String URL = "jdbc:sqlite:acmebank.db";

    public static Connection connect() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static void initialise() {
        String createTeller = """
                CREATE TABLE IF NOT EXISTS Teller (
                    teller_ID          INTEGER PRIMARY KEY AUTOINCREMENT,
                    teller_Name       TEXT,
                    teller_Password   TEXT NOT NULL,
                    teller_role         TEXT NOT NULL
                );
                """;
        String createCustomers = """
                CREATE TABLE IF NOT EXISTS Customer (
                    customer_ID          INTEGER PRIMARY KEY AUTOINCREMENT,
                    customer_name       TEXT NOT NULL,
                    address_verified    INTEGER NOT NULL DEFAULT 0,
                    id_verified         INTEGER NOT NULL DEFAULT 0,
                    customer_signup_date    DATE NOT NULL
                );
                """;
        String createAccount = """
                CREATE TABLE IF NOT EXISTS Account (
                    account_Number      TEXT PRIMARY KEY,
                    customer_ID     INTEGER NOT NULL,
                    sort_code       TEXT NOT NULL,
                    balance         REAL NOT NULL,
                    is_active TEXT NOT NULL,
                    account_type TEXT NOT NULL,
                    FOREIGN KEY (customer_ID) REFERENCES CUSTOMER(customer_ID)
                );
                """;
        String createOverdraft = """
                CREATE TABLE IF NOT EXISTS Overdraft (
                    account_number TEXT PRIMARY KEY,
                    overdraft_balance REAL NOT NULL,
                    max_overdraft REAL NOT NULL DEFAULT 100,
                    overdraft_start TEXT,
                    FOREIGN KEY (account_number) REFERENCES Account(account_number)
    );
""";



        String createPersonalAccount= """
                CREATE TABLE IF NOT EXISTS PersonalACC (
                    account_Number TEXT PRIMARY KEY NOT NULL,
                    photo_ID_Verified Boolean,
                    FOREIGN KEY (account_number) REFERENCES Account(account_number)
                );""";
        String createISAAccount= """
                CREATE TABLE IF NOT EXISTS ISAACC (
                    account_Number  TEXT PRIMARY KEY NOT NULL,
                     last_interest_date   TEXT NOT NULL,
                   FOREIGN KEY (account_number) REFERENCES Account(account_number)
                );
                """;
        String createBusinessAccount= """
                CREATE TABLE IF NOT EXISTS BUSINESSACC (
                account_number TEXT PRIMARY KEY,
                chequeBook BOOLEAN NOT NULL,
                overdraft_amount INTEGER NOT NULL,
                overdraft boolean not null,
                loan_request boolean not null,
                business_type TEXT NOT NULL,
                international_trading boolean NOT NULL,
                FOREIGN KEY (account_number) REFERENCES Account(account_number)
                 )
               """;
        String createTransfer = """
        CREATE TABLE IF NOT EXISTS Transfer (
            transfer_id       INTEGER PRIMARY KEY AUTOINCREMENT,
            from_account      TEXT NOT NULL,
            to_account        TEXT NOT NULL,
            amount            REAL NOT NULL,
            transfer_date     TEXT NOT NULL,
            reference         TEXT,
            status            TEXT NOT NULL DEFAULT 'COMPLETED',
            FOREIGN KEY (from_account) REFERENCES Account(account_number),
            FOREIGN KEY (to_account) REFERENCES Account(account_number)
        );
        """;

        String createDirectDebit = """
        CREATE TABLE IF NOT EXISTS DirectDebit (
            dd_id             INTEGER PRIMARY KEY AUTOINCREMENT,
            account_number    TEXT NOT NULL,
            merchant_name     TEXT NOT NULL,
            amount            REAL NOT NULL,
            frequency         TEXT NOT NULL,
            next_payment_date TEXT NOT NULL,
            active            INTEGER NOT NULL DEFAULT 1,
            FOREIGN KEY (account_number) REFERENCES Account(account_number)
        );
        """;

        String createStandingOrder = """
        CREATE TABLE IF NOT EXISTS StandingOrder (
            so_id             INTEGER PRIMARY KEY AUTOINCREMENT,
            from_account      TEXT NOT NULL,
            to_account        TEXT NOT NULL,
            amount            REAL NOT NULL,
            frequency         TEXT NOT NULL,
            next_payment_date TEXT NOT NULL,
            active            INTEGER NOT NULL DEFAULT 1,
            reference         TEXT,
            FOREIGN KEY (from_account) REFERENCES Account(account_number),
            FOREIGN KEY (to_account) REFERENCES Account(account_number)
        );
        """;

        String createPaymentHistory = """
        CREATE TABLE IF NOT EXISTS PaymentHistory (
            payment_id        INTEGER PRIMARY KEY AUTOINCREMENT,
            account_number    TEXT NOT NULL,
            type              TEXT NOT NULL,   -- TRANSFER, DIRECT_DEBIT, STANDING_ORDER
            amount            REAL NOT NULL,
            date              TEXT NOT NULL,
            reference         TEXT,
            FOREIGN KEY (account_number) REFERENCES Account(account_number)
        );
        """;





        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createCustomers);
            System.out.println("Customers created");

            stmt.execute(createTeller);
            System.out.println("tellers created");

            stmt.execute(createAccount);
            System.out.println("account created");

            stmt.execute(createPersonalAccount);
            IO.println("personal account created");

            stmt.execute(createISAAccount);
            IO.println("ISA account created");

            stmt.execute(createBusinessAccount);
            IO.println("business account created");

            stmt.execute(createOverdraft);
            System.out.println("Tables created successfully.");

            stmt.execute(createTransfer);
            IO.println("transfer table created");

            stmt.execute(createDirectDebit);
            IO.println("direct debit table created");

            stmt.execute(createStandingOrder);
            IO.println("standing order table created");

            stmt.execute(createPaymentHistory);
            IO.println("payment history table created");

            initialiseMainTeller();
        } catch (Exception e) {
            System.out.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private  static void initialiseMainTeller(){
        String insertMainTeller="INSERT INTO Teller(teller_name,teller_Password,teller_role)" +
                "VALUES(?,?,?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(insertMainTeller)){

             stmt.setString(1,"ADMIN");
            stmt.setString(2,"1234");
            stmt.setString(3,"ADMIN");
            stmt.executeUpdate();
            IO.println("Main teller added TO01 Admin");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}