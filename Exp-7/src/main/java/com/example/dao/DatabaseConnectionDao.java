package com.example.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class DatabaseConnectionDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private DataSource dataSource;
    
    public String testConnection() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            return "Connected to: " + metaData.getDatabaseProductName() + 
                   " " + metaData.getDatabaseProductVersion();
        } catch (SQLException e) {
            return "Connection failed: " + e.getMessage();
        }
    }
    
    public List<Map<String, Object>> fetchAllData(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.queryForList(sql);
    }
    
    public int getRowCount(String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
    public List<String> getTableNames() {
        String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
        return jdbcTemplate.queryForList(sql, String.class);
    }
}
