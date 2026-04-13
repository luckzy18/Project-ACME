package org.example.Database;


import org.example.model.User;

import java.sql.*;

public class DBinterface {
    private static final String URL = "jdbc:sqlite:acmebank.db";


private static Connection connect() throws Exception {
    return DriverManager.getConnection(URL);
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
    public String getCustomer(String customerID){
    // get customer details based on customer id the method needs work
    return "testValue";
    }
//    public Account[] getAccounts()
}
