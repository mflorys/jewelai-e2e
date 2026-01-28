package com.jewelai.e2e.db;

import com.jewelai.e2e.utils.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class JdbcDbCleaner implements DbCleaner {

    private static final List<String> TABLES_TO_CLEAN = List.of(
            "projects",
            "users"
    );

    @Override
    public void cleanup(UUID testRunId) {
        if (testRunId == null) {
            throw new IllegalArgumentException("testRunId cannot be null");
        }

        String dbUrl = Config.getProperty("dbUrl");
        if (!dbUrl.contains("localhost") && !dbUrl.contains("127.0.0.1")) {
            throw new RuntimeException("Safety check failed: Cleanup allowed only on local DB.");
        }

        try (Connection connection = DriverManager.getConnection(
                dbUrl,
                Config.getProperty("dbUser"),
                Config.getProperty("dbPassword"))) {

            connection.setAutoCommit(false);

            try {
                for (String table : TABLES_TO_CLEAN) {
                    deleteFromTable(connection, table, testRunId);
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }

    private void deleteFromTable(Connection connection, String tableName, UUID testRunId) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE test_run_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, testRunId);
            statement.executeUpdate();
        } catch (SQLException e) {
            String state = e.getSQLState();
            if ("42P01".equals(state) || "42703".equals(state)) {
                return; // skip missing table/column
            }
            throw e; // keep original SQLSTATE
        }
    }
}