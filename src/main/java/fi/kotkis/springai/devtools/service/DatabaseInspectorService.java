package fi.kotkis.springai.devtools.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseInspectorService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInspectorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getSchemaDetails() {
        try {
            // Query H2's internal metadata for the PUBLIC schema (where your entities live)
            String sql = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_SCHEMA = 'PUBLIC' " +
                    "ORDER BY TABLE_NAME, ORDINAL_POSITION";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

            if (rows.isEmpty()) {
                return "The PUBLIC schema is currently empty. No tables or columns found.";
            }

            StringBuilder sb = new StringBuilder("### Current H2 Database Schema\n");
            String currentTable = "";

            for (Map<String, Object> row : rows) {
                String tableName = (String) row.get("TABLE_NAME");
                String columnName = (String) row.get("COLUMN_NAME");
                String dataType = (String) row.get("DATA_TYPE");

                // Print the table name header when it changes
                if (!tableName.equals(currentTable)) {
                    sb.append("\n**Table: ").append(tableName).append("**\n");
                    currentTable = tableName;
                }
                // Print the column details
                sb.append(String.format("- %s (`%s`)\n", columnName, dataType));
            }

            return sb.toString();
        } catch (Exception e) {
            return "Failed to inspect database schema: " + e.getMessage();
        }
    }
}
