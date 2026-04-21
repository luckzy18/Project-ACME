package org.example.Database;

import java.time.LocalDate;


import org.example.model.Account.*;
import org.example.model.people.Customer;
import org.example.model.people.Role;
import org.example.model.people.User;
import org.example.utils.Generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBinterface {
    private static final String URL = "jdbc:sqlite:acmebank.db";
    private static LocalDate date=LocalDate.now();

private static Connection connect() throws Exception {
    return DriverManager.getConnection(URL);
}


    public static String[] generateNewTeller(){
    String query="INSERT INTO TELLER(teller_Password,teller_role) VALUES(?,?);";
    String password="temp"+ Generator.generateTemporaryPassword();
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1,password);
            stmt.setString(2,Role.TEMPORARY.toString());
            stmt.executeUpdate();

            ResultSet rs=stmt.getGeneratedKeys();
            if(rs.next()){
                String[] output= {String.valueOf(rs.getInt(1)),password};
                return output;
            }
            }catch(Exception e){
            e.printStackTrace();
        }
    return null;
    }


    public static User tellerTryLogin(String id, String password) {
    String query= "SELECT * FROM TELLER WHERE teller_ID=? and teller_password=?";
    User us=null;
    try (Connection conn = connect();
    PreparedStatement stmt =conn.prepareStatement(query)){
            stmt.setString(1,id);
            stmt.setString(2,password);

    ResultSet rs=stmt.executeQuery();
    if (rs.next()){
        String role=rs.getString("teller_role");
       String name=rs.getString("teller_Name");

       Role rl=Role.valueOf(role);
       us=new User(Integer.parseInt(id),name,password,rl);
       return us;
    }else{
       return us;
    }

    } catch (Exception e) {
        throw new RuntimeException(e);
    }


}

    public static Customer  insertCustomer (String name,boolean addressVerfied,boolean identityVerified){
    String insertCustomer="Insert into Customer(customer_name,address_verified,id_verified,customer_signup_date)" +
            " Values(?,?,?,?)";
    String date=LocalDate.now().toString();
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS);){
         stmt.setString(1,name);
         stmt.setBoolean(2,addressVerfied);
         stmt.setBoolean(3,identityVerified);
         stmt.setString(4, date);

         stmt.executeUpdate();

         ResultSet rs=stmt.getGeneratedKeys();
         if(rs.next()){
             return new Customer(name,rs.getInt(1),date,true,true);

         }
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
    }

    public static Customer getCustomerbyID(Integer id){
        String query= "SELECT * FROM Customer WHERE customer_ID=? ;";
        Customer us=null;

        try (Connection conn = connect();
             PreparedStatement stmt =conn.prepareStatement(query)){
            stmt.setInt(1,id);

            ResultSet rs=stmt.executeQuery();

            if (rs.next()){
                String name=rs.getString("customer_name");
                boolean idVerified=rs.getBoolean("id_verified");
                boolean addressVerified=rs.getBoolean("address_verified");
                String date=rs.getString("customer_signup_date");
                IO.println("customer found: "+name);
                us=new Customer(name,id,date,addressVerified,idVerified);
                return us;
            }else {
                IO.println("No customer found with ID: " + id);
            }
            return us;


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public static boolean deleteCustomerByID(String id){
        String checkAccounts = "SELECT COUNT(*) FROM Account WHERE customer_ID = ? AND account_status = 'open'";
        String deleteCustomer = "DELETE FROM Customer WHERE customer_ID = ?";
        try(Connection conn = connect();
        PreparedStatement stmt=conn.prepareStatement(checkAccounts);
        PreparedStatement dstmt=conn.prepareStatement(deleteCustomer);){
        stmt.setString(1,id);
        ResultSet rs=stmt.executeQuery();

        if(rs.next() && rs.getInt(1) == 0){
            dstmt.setString(1,id);
            int rowsAffected =dstmt.executeUpdate();
            return rowsAffected > 0;
        }else{
            return false;
        }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public static String[] getAllTellers(){
    String query="SELECT teller_ID,teller_name,teller_role FROM TELLER;";
        try(Connection conn=connect();
            PreparedStatement dstmt=conn.prepareStatement(query)){
            ResultSet rs=dstmt.executeQuery();
            List<String> tellers = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("teller_ID");
                String name = rs.getString("teller_name");
                String role = rs.getString("teller_role");

                String tellerInfo = "ID: " + id + ", Name: " + name + ", Role: " + role;
                tellers.add(tellerInfo);
            }
            return tellers.toArray(new String[0]);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean deleteAccountIfClosed(String accountNumber) {

        String checkQuery = """
        SELECT is_active
        FROM Account
        WHERE account_number = ?;
        """;

        String deleteQuery = """
        DELETE FROM Account
        WHERE account_number = ?;
        """;

        try (Connection conn = connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, accountNumber);
            ResultSet rs = checkStmt.executeQuery();

            // Account does not exist
            if (!rs.next()) {
                return false;
            }

            String active = rs.getString("is_active");

            // Account must be closed
            if (active.equalsIgnoreCase("true") || active.equalsIgnoreCase("open")) {
                return false;
            }

            // Delete account
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, accountNumber);
                int rows = deleteStmt.executeUpdate();
                return rows > 0;
            }

        } catch (Exception e) {
            throw new RuntimeException("Error deleting account: " + e.getMessage(), e);
        }
    }


    public static boolean deleteTellerByID(Integer id){// feel free to make 2 different statements for failed deletion
    String fetch="SELECT TELLER_ROLE FROM TELLER WHERE TELLER_ID=?;";
    String deleteQuery="Delete from Teller where teller_id=?";
    try(Connection conn=connect();
        PreparedStatement stmt=conn.prepareStatement(fetch);
        PreparedStatement dstmt=conn.prepareStatement(deleteQuery)){
            dstmt.setInt(1,id);
            int rowsAffected=dstmt.executeUpdate();
            return rowsAffected>0;// returns the rows affected and been used to turn into true
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }
    public static Account createISAAccount(User teller, Customer cu, AccountTypePolicy acc, double balance) {
        IO.println("Creating ISA Account.");

        String accountNumber = getNewAccountNumber();
        IO.println("acc_number is: " + accountNumber);

        insertBankAccount(acc, accountNumber, cu.getId(), balance);
        insertISAACC(accountNumber);

        return new ISAAccount(accountNumber, cu.getId(), acc.getSortCode(), balance);
    }
    private static boolean insertISAACC(String accountNumber) {
        String query = """
        INSERT INTO ISAACC (account_Number, last_interest_date)
        VALUES (?, ?);
        """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            stmt.setString(2, LocalDate.now().toString());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            throw new RuntimeException("Error creating ISA account: " + e.getMessage(), e);
        }
    }

    public static Account createBusinessAccount(User teller,Customer cu,AccountTypePolicy acc,double balance,String businessType){
    IO.println("creating business Account.");
    String accountNumber=getNewAccountNumber();
    IO.println("acc_number is: "+accountNumber);
    insertBankAccount(acc,accountNumber,cu.getId(),balance);
    insertBusinessACC(accountNumber,businessType);
    return new BusinessAccount(accountNumber,cu.getId(),acc.getSortCode(),balance,businessType);
}

    private static boolean insertBusinessACC(String accountNumber,String businessType) {
    String query= """ 
            INSERT INTO BUSINESSACC (account_number, chequeBook, overdraft_amount, overdraft, loan_request, bussiness_type, international_trading)
            VALUES (?,?,?,?,?,?,?);
            """;
    boolean chequeBook=false;
    int overdraftAmount=100;
    boolean overdraft=false;
    boolean loanRequest=false;
    boolean internationalTrading=false;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            stmt.setBoolean(2, chequeBook);        // boolean
            stmt.setInt(3, overdraftAmount);
            stmt.setBoolean(4, overdraft);         // boolean
            stmt.setBoolean(5, loanRequest);       // boolean
            stmt.setString(6, businessType);
            stmt.setBoolean(7, internationalTrading); // boolean

            int rowsAffected=stmt.executeUpdate();
            return rowsAffected>0;
        } catch (Exception e) {
            throw new RuntimeException("Error creating business account: " + e.getMessage(), e);
        }
    }

    public static Account createPersonalAccount(User teller,Customer cu,AccountTypePolicy acc,double balance){
    IO.println("creating acc");
    String accountNumber=getNewAccountNumber();
    IO.println("acc_number is: "+accountNumber);
    insertBankAccount(acc,accountNumber,cu.getId(),balance);
    insertPersonalACC(accountNumber,cu.isIdVerified());
    return new PersonalAccount(accountNumber,cu.getId(),acc.getSortCode(),balance);
    }

    private static boolean insertPersonalACC(String accountNumber, boolean photoVerified) {
        String query = """
        INSERT INTO PersonalACC (account_Number, photo_ID_Verified)
        VALUES (?, ?);
        """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            stmt.setBoolean(2, photoVerified);

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error creating personal account: " + e.getMessage(), e);
        }
}

    private static String getNewAccountNumber(){
        String accountNumber;
       do{
            accountNumber=Generator.generateAccountNumber();

        }while(!isACCunique(accountNumber));
        return accountNumber;
    }

    private static boolean insertBankAccount(AccountTypePolicy acc, String account_Number, int customerId, double balance) {
    IO.println("account inserted");
    String queryACC = """
            INSERT INTO Account (account_Number, customer_ID, sort_code, balance, is_active, account_type)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(queryACC)) {

            stmt.setString(1, account_Number);
            stmt.setInt(2, customerId);
            stmt.setString(3, acc.getSortCode());  // sort code from AccountTypePolicy
            stmt.setDouble(4, balance);
            stmt.setString(5, "true");
            stmt.setString(6, acc.name());
            stmt.executeUpdate();

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error creating account: " + e.getMessage(), e);

        }
    }
    public static List<Account> getBankAccounts(Customer cu) {
        String query = "SELECT * FROM Account WHERE customer_ID = ?;";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, cu.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String accNum = rs.getString("account_number");
                String sortCode = rs.getString("sort_code");
                double balance = rs.getDouble("balance");
                String type = rs.getString("account_type");

                Account acc = null;

                switch (type) {

                    case "PERSONAL" -> {
                        acc = new PersonalAccount(accNum, cu.getId(), sortCode, balance);
                    }

                    case "BUSINESS" -> {
                        // Fetch business-specific fields
                        String bQuery = "SELECT bussiness_type FROM BUSINESSACC WHERE account_number = ?";
                        try (PreparedStatement bStmt = conn.prepareStatement(bQuery)) {
                            bStmt.setString(1, accNum);
                            ResultSet brs = bStmt.executeQuery();
                            String businessType = brs.next() ? brs.getString("bussiness_type") : "Unknown";
                            acc = new BusinessAccount(accNum, cu.getId(), sortCode, balance, businessType);
                        }
                    }

                    case "ISA" -> {
                        // Fetch ISA-specific fields
                        String iQuery = "SELECT last_interest_date FROM ISAACC WHERE account_number = ?";
                        try (PreparedStatement iStmt = conn.prepareStatement(iQuery)) {
                            iStmt.setString(1, accNum);
                            ResultSet irs = iStmt.executeQuery();
                            // You may want to store last_interest_date later
                            acc = new ISAAccount(accNum, cu.getId(), sortCode, balance);
                        }
                    }

                    default -> {
                        IO.println("Unknown account type: " + type);
                    }
                }

                if (acc != null) {
                    accounts.add(acc);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error fetching accounts: " + e.getMessage(), e);
        }

        return accounts;
    }

    private static double getOverdraftBalance(String accountNumber) {
        String query = "SELECT overdraft_balance FROM Overdraft WHERE account_number = ?;";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("overdraft_balance");
            }

            return 0;

        } catch (Exception e) {
            throw new RuntimeException("Error checking overdraft: " + e.getMessage(), e);
        }
    }



    public static boolean isACCunique(String acountNumber){
        String query="SELECT COUNT(*) FROM account WHERE account_number=?;";
        try (Connection conn = connect();
             PreparedStatement stmt =conn.prepareStatement(query)){
            stmt.setString(1,acountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0; // true if no account with that number exists
            }
            return false;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
}

    public static User getTeller(int id) {
        String sql = """
        SELECT teller_ID,
               teller_Name,
               teller_role
        FROM Teller
        WHERE teller_ID = ?;
        """;
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int tellerID = rs.getInt("teller_id");
                    String name = rs.getString("teller_Name");
                    String role = rs.getString("teller_role");

                    return new User(tellerID, name, "", Role.valueOf(role));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Teller not found or error
    }

    public static boolean updateTellerNameANDRole(User user, String name) {
        String sql = """
        UPDATE Teller
        SET teller_Name = ?, 
            teller_role = ?
        WHERE teller_ID = ?;
        """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, Role.TELLER.toString());   // promote to TELLER
            stmt.setInt(3, user.getTellerId());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                // Update in-memory object too
                user.setName(name);
                user.setRole(Role.TELLER);
                return true;
            }

            return false;

        } catch (Exception e) {
            throw new RuntimeException("Error updating teller name: " + e.getMessage(), e);
        }
    }

    public static void deposit(double amount, Account account) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        String sql = """
        UPDATE Account
        SET balance = balance + ?
        WHERE account_number = ?;
        """;

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setString(2, account.getAccountNumber());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                // Update in-memory object
                account.setBalance(account.getBalance() + amount);
            } else {
                throw new RuntimeException("Deposit failed: account not found.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error during deposit: " + e.getMessage(), e);
        }
    }

    public static boolean withdraw(double amount, Account account) {

        if (amount <= 0) {
            return false;
        }

        double balance = account.getBalance();
        double maxOverdraft = account.getOverdraft().getMaxOverdraft();
        double overdraftUsed = account.getOverdraft().getOverdraftBalance();
        double availableOverdraft = maxOverdraft - overdraftUsed;

        double limit = balance + availableOverdraft;

        if (amount > limit) {
            throw new RuntimeException("Withdrawal exceeds available funds including overdraft.");
        }

        String updateAccountSql = """
        UPDATE Account
        SET balance = balance - ?
        WHERE account_number = ?;
        """;

        String updateOverdraftSql = """
        UPDATE Overdraft
        SET overdraft_balance = overdraft_balance + ?
        WHERE account_number = ?;
        """;

        try (Connection conn = connect()) {

            conn.setAutoCommit(false); // Start transaction

            // 1. Update account balance
            try (PreparedStatement stmt = conn.prepareStatement(updateAccountSql)) {
                stmt.setDouble(1, amount);
                stmt.setString(2, account.getAccountNumber());
                int rows = stmt.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    throw new RuntimeException("Withdrawal failed: account not found.");
                }
            }

            // 2. If overdraft is needed, update overdraft table
            if (amount > balance) {
                double overdraftPart = amount - balance;

                try (PreparedStatement stmt = conn.prepareStatement(updateOverdraftSql)) {
                    stmt.setDouble(1, overdraftPart);
                    stmt.setString(2, account.getAccountNumber());
                    stmt.executeUpdate();
                }
            }

            conn.commit();

            // 3. Update in-memory object
            account.setBalance(balance - amount);

            if (amount > balance) {
                double overdraftPart = amount - balance;
                account.getOverdraft().setOverdraftBalance(overdraftUsed + overdraftPart);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error during withdrawal: " + e.getMessage(), e);
        }
        return true;
    }

    /// TIME TO WORK ON ACCOUNT CREATION, GENERATIONS WITHDRAW AND DEPOST
    /// GETTER FOR ALL ACCOUNTS A CUSTOMER HAS AND STORED LOCALLY ONCE FETCHED
    /// THE ACCOUNTS SHOULD BE GENERATED FIRST WITHIN 3 METHODS ONE FOR EACH ACCOUNT TYPE AN OVERLOADED METHOD WOULD WORK NICELY
    ///  WITHDRAW AND DEPOSI CAN COME AFTER ACCOUNT CREATION

    public boolean checkCustomerID(String id) {
        return false;
    }
    public boolean checkTellerID(String id) {
        String query = "SELECT COUNT(*) FROM Teller WHERE teller_id=?;";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            int count = stmt.executeUpdate();
            return count < 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
