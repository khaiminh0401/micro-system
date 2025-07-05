package com.vnpt.system.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JPA Entity Generator from PostgreSQL Database
 * This class connects to database and generates JPA entities
 */
public class SimpleEntityGenerator {
    
    private static final String DB_URL = "jdbc:postgresql://aws-0-ap-northeast-1.pooler.supabase.com:6543/postgres";
    private static final String DB_USER = "postgres.gomnsfhwgtgkgqwumedv";
    private static final String DB_PASSWORD = "123456";
    private static final String OUTPUT_DIR = "src/main/java/com/vnpt/system/entity/";
    
    public static void main(String[] args) {
        System.out.println("Starting Simple Entity Generation from Database...");
        
        try {
            // Load PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            
            // Connect to database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database successfully!");
            
            // Get all tables
            List<String> tables = getTables(connection);
            System.out.println("Found " + tables.size() + " tables");
            
            // Generate entity for each table
            for (String tableName : tables) {
                generateEntityForTable(connection, tableName);
            }
            
            connection.close();
            System.out.println("Entity generation completed!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static List<String> getTables(Connection connection) throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData metaData = connection.getMetaData();
        
        // Get tables from public schema
        ResultSet rs = metaData.getTables(null, "public", null, new String[]{"TABLE"});
        
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            tables.add(tableName);
            System.out.println("Found table: " + tableName);
        }
        
        return tables;
    }
    
    private static void generateEntityForTable(Connection connection, String tableName) throws SQLException, IOException {
        System.out.println("Generating entity for table: " + tableName);
        
        // Get table columns
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, "public", tableName, null);
        
        // Get primary keys
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, "public", tableName);
        List<String> pkColumns = new ArrayList<>();
        while (primaryKeys.next()) {
            pkColumns.add(primaryKeys.getString("COLUMN_NAME"));
        }
        
        // Generate entity class
        StringBuilder entityClass = new StringBuilder();
        String className = toCamelCase(tableName, true);
        
        // Package and imports
        entityClass.append("package com.vnpt.system.entity;\n\n");
        entityClass.append("import jakarta.persistence.*;\n");
        entityClass.append("import lombok.Data;\n");
        entityClass.append("import lombok.NoArgsConstructor;\n");
        entityClass.append("import lombok.AllArgsConstructor;\n");
        entityClass.append("import java.time.LocalDateTime;\n");
        entityClass.append("import java.time.LocalDate;\n");
        entityClass.append("import java.math.BigDecimal;\n\n");
        
        // Class declaration
        entityClass.append("@Entity\n");
        entityClass.append("@Table(name = \"").append(tableName).append("\")\n");
        entityClass.append("@Data\n");
        entityClass.append("@NoArgsConstructor\n");
        entityClass.append("@AllArgsConstructor\n");
        entityClass.append("public class ").append(className).append(" {\n\n");
        
        // Generate fields
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            int dataType = columns.getInt("DATA_TYPE");
            String typeName = columns.getString("TYPE_NAME");
            boolean nullable = columns.getBoolean("NULLABLE");
            
            // Generate field
            if (pkColumns.contains(columnName)) {
                entityClass.append("    @Id\n");
                if (isAutoIncrement(connection, tableName, columnName)) {
                    entityClass.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                }
            }
            
            entityClass.append("    @Column(name = \"").append(columnName).append("\"");
            if (!nullable) {
                entityClass.append(", nullable = false");
            }
            entityClass.append(")\n");
            
            String javaType = getJavaType(dataType, typeName);
            String fieldName = toCamelCase(columnName, false);
            
            entityClass.append("    private ").append(javaType).append(" ").append(fieldName).append(";\n\n");
        }
        
        entityClass.append("}\n");
        
        // Write to file
        String fileName = OUTPUT_DIR + className + ".java";
        FileWriter writer = new FileWriter(fileName);
        writer.write(entityClass.toString());
        writer.close();
        
        System.out.println("Generated: " + fileName);
    }
    
    private static String getJavaType(int sqlType, String typeName) {
        return switch (sqlType) {
            case Types.INTEGER -> "Integer";
            case Types.BIGINT -> "Long";
            case Types.SMALLINT -> "Short";
            case Types.TINYINT -> "Byte";
            case Types.DECIMAL, Types.NUMERIC -> "BigDecimal";
            case Types.REAL -> "Float";
            case Types.DOUBLE -> "Double";
            case Types.VARCHAR, Types.CHAR, Types.LONGVARCHAR -> "String";
            case Types.BOOLEAN -> "Boolean";
            case Types.DATE -> "LocalDate";
            case Types.TIME -> "LocalTime";
            case Types.TIMESTAMP -> "LocalDateTime";
            case Types.BLOB, Types.LONGVARBINARY -> "byte[]";
            default -> "String";
        };
    }
    
    private static boolean isAutoIncrement(Connection connection, String tableName, String columnName) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT column_default FROM information_schema.columns WHERE table_name = ? AND column_name = ?"
            );
            stmt.setString(1, tableName);
            stmt.setString(2, columnName);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String defaultValue = rs.getString("column_default");
                return defaultValue != null && defaultValue.contains("nextval");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private static String toCamelCase(String str, boolean capitalizeFirst) {
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = capitalizeFirst;
        
        for (char c : str.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        
        return result.toString();
    }
}
