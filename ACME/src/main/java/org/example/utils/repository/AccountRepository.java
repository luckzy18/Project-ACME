package org.example.utils.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {

    private final Connection conn;

    public AccountRepository(Connection conn) {
        this.conn = conn;
    }

    public boolean exists(String accountNumber) {
        String sql = "SELECT 1 FROM Account WHERE account_number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
