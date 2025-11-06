package com.example.dao;

import com.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void init() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS product (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "description VARCHAR(255), " +
                "price DECIMAL(10, 2) NOT NULL, " +
                "quantity INT NOT NULL)";
        jdbcTemplate.execute(createTableSql);
    }
    
    private static final class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            product.setQuantity(rs.getInt("quantity"));
            return product;
        }
    }
    
    public int create(Product product) {
        String sql = "INSERT INTO product (name, description, price, quantity) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
                product.getName(), 
                product.getDescription(), 
                product.getPrice(), 
                product.getQuantity());
    }
    
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }
    
    public Product findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
    }
    
    public int update(Product product) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ? WHERE id = ?";
        return jdbcTemplate.update(sql, 
                product.getName(), 
                product.getDescription(), 
                product.getPrice(), 
                product.getQuantity(), 
                product.getId());
    }
    
    public int deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    
    public int deleteAll() {
        String sql = "DELETE FROM product";
        return jdbcTemplate.update(sql);
    }
}
