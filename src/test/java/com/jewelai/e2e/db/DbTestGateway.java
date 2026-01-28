package com.jewelai.e2e.db;

import com.jewelai.e2e.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DbTestGateway {

    public boolean userExists(String email, UUID testRunId) {
        String sql = "SELECT count(*) FROM users WHERE email = ? AND test_run_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setObject(2, testRunId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check user existence", e);
        }
        return false;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Config.getProperty("dbUrl"),
                Config.getProperty("dbUser"),
                Config.getProperty("dbPassword"));
    }
}
