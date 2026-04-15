package org.example.Database;

import java.time.LocalDate;

import org.example.model.Customer;
import org.example.model.User;
import org.example.utils.Generator;

import java.sql.*;

public class DBinterface {
    private static final String URL = "jdbc:sqlite:acmebank.db";
    private static LocalDate date=LocalDate.now();

private static Connection connect() throws Exception {
    return DriverManager.getConnection(URL);
}

    public String[] generateNewTeller(){
    String id="1234";//can be checked with a
    String password="temp"+ Generator.generateTemporaryPassword();// make a list of simple passwords and return them
    String[] output= {id,password};
    return output;
    }

    public User tellerTryLogin(String id, String password) {
    String query= "SELECT * FROM TELLER WHERE ID=? and password=?";
    User us=null;
    try (Connection conn = connect();
    PreparedStatement stmt =conn.prepareStatement(query)){
            stmt.setString(1,id);
            stmt.setString(2,password);

    ResultSet rs=stmt.executeQuery();
    if (rs.next()){
        String firstName=rs.getString("firstName");
        String lastName=rs.getString("lastname");
        //return new User(id, firstName, lastName);
    }else{
       // return new User();
    }

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    return us;

}

    public static String  insertCustomer (String name,boolean addressVerfied,boolean identityVerified){
    String insertCustomer="Insert into Customer(customer_ID,customer_name,address_verified,id_verified,customer_signup_date)" +
            " Values(?,?,?,?,?)";
    String customerID="123";// make this unique and can be checked with a method checkCustomerID
    try (Connection conn = connect();
         PreparedStatement stmt =conn.prepareStatement(insertCustomer)){
        stmt.setString(1,customerID);
         stmt.setString(2,name);
         stmt.setBoolean(3,addressVerfied);
         stmt.setBoolean(4,identityVerified);
         stmt.setString(5, LocalDate.now().toString());

         stmt.executeUpdate();

//
    }catch(Exception e){
        e.printStackTrace();
        return null;
    }
    return customerID;
    }

    public static Customer getCustomerbyID(String id){
        String query= "SELECT * FROM Customer WHERE customer_ID=? ;";
        Customer us=null;
        try (Connection conn = connect();
             PreparedStatement stmt =conn.prepareStatement(query)){
            stmt.setString(1,id);


            ResultSet rs=stmt.executeQuery();
            if (rs.next()){
                String name=rs.getString("customer_name");
                boolean idVerified=rs.getBoolean("id_verified");
                boolean addressVerified=rs.getBoolean("address_verified");
                String date=rs.getString("customer_signup_date");
                us=new Customer(name,id,date,addressVerified,idVerified);
                return us;
            }else{
               return us;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    public static boolean deleteCustomerbyID(String id){
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
