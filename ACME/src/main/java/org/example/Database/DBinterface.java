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
    String query="INSERT INTO TELLER(teller_Password,teller_role) VALUES(??);";
    String password="temp"+ Generator.generateTemporaryPassword();
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);){
            stmt.setString(1,password);
            stmt.setString(2,"TELLER");
            stmt.executeUpdate();

            ResultSet rs=stmt.getGeneratedKeys();
            if(rs.next()){
                String[] output= {rs.getString(1),password};
                return output;
            }
            }catch(Exception e){
            e.printStackTrace();
        }
    return null;
    }
    public static boolean tellerFirstLoginUpdate(String name, String password,int id){
    String query="UPDATE TELLER set TELLER_NAME= ?,TELLER_PASSWORD = ? WHERE TELLER_ID=?;";
    try(Connection conn=connect();
    PreparedStatement stmt=conn.prepareStatement(query)){
        stmt.setString(1,name);
        stmt.setString(2,password);
        stmt.setInt(3,id);
        int rowsAffected=stmt.executeUpdate();
        return rowsAffected>0;
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

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
       String name=rs.getString("teller_Name");
       String role=rs.getString("teller_role");
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

    public static Integer  insertCustomer (String name,boolean addressVerfied,boolean identityVerified){
    String insertCustomer="Insert into Customer(customer_name,address_verified,id_verified,customer_signup_date)" +
            " Values(?,?,?,?)";
    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS);){
         stmt.setString(1,name);
         stmt.setBoolean(2,addressVerfied);
         stmt.setBoolean(3,identityVerified);
         stmt.setString(4, LocalDate.now().toString());

         stmt.executeUpdate();

         ResultSet rs=stmt.getGeneratedKeys();
         if(rs.next()){
             return rs.getInt(1);
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
    public String[] getAllTellers(){
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
//    public Account createBankAccount(User user){
//    return new BusinessAccount();
//    }
public static Account createBusinessAccount(User teller,Customer cu,AccountTypePolicy acc,double balance){
    IO.println("creating acc");
    String accountNumber=getNewAccountNumber();
    IO.println("acc_number is: "+accountNumber);
    insertBankAccount(acc,accountNumber,cu.getId(),balance);
    insertBusinessACC(accountNumber,cu.isIdVerified());
    return new BusinessAccount();
}

    private static void insertBusinessACC(String accountNumber, boolean idVerified) {
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
    /// TIME TO WORK ON ACCOUNT CREATION, GENERATIONS WITHDRAW AND DEPOST
    /// GETTER FOR ALL ACCOUNTS A CUSTOMER HAS AND STORED LOCALLY ONCE FETCHED
    /// THE ACCOUNTS SHOULD BE GENERATED FIRST WITHIN 3 METHODS ONE FOR EACH ACCOUNT TYPE AN OVERLOADED METHOD WOULD WORK NICELY
    ///  WITHDRAW AND DEPOSI CAN COME AFTER ACCOUNT CREATION

    public boolean checkCustomerID(String id){
        String query="SELECT COUNT(*) FROM Customer WHERE customer_ID=?;";
        try (Connection conn = connect();
             PreparedStatement stmt =conn.prepareStatement(query)){
            stmt.setString(1,id);
            int count=stmt.executeUpdate();
            return count<1;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
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
