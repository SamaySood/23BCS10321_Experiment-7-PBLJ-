package com.example.dao;

import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostConstruct
    public void init() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS student (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "first_name VARCHAR(50) NOT NULL, " +
                "last_name VARCHAR(50) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "phone VARCHAR(15), " +
                "course VARCHAR(100), " +
                "semester INT)";
        jdbcTemplate.execute(createTableSql);
    }
    
    private static final class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            student.setCourse(rs.getString("course"));
            student.setSemester(rs.getInt("semester"));
            return student;
        }
    }
    
    public int create(Student student) {
        String sql = "INSERT INTO student (first_name, last_name, email, phone, course, semester) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, 
                student.getFirstName(), 
                student.getLastName(), 
                student.getEmail(), 
                student.getPhone(), 
                student.getCourse(), 
                student.getSemester());
    }
    
    public List<Student> findAll() {
        String sql = "SELECT * FROM student ORDER BY id";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }
    
    public Student findById(Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
    }
    
    public List<Student> findByCourse(String course) {
        String sql = "SELECT * FROM student WHERE course = ?";
        return jdbcTemplate.query(sql, new StudentRowMapper(), course);
    }
    
    public int update(Student student) {
        String sql = "UPDATE student SET first_name = ?, last_name = ?, email = ?, " +
                     "phone = ?, course = ?, semester = ? WHERE id = ?";
        return jdbcTemplate.update(sql, 
                student.getFirstName(), 
                student.getLastName(), 
                student.getEmail(), 
                student.getPhone(), 
                student.getCourse(), 
                student.getSemester(), 
                student.getId());
    }
    
    public int deleteById(Long id) {
        String sql = "DELETE FROM student WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    
    public int count() {
        String sql = "SELECT COUNT(*) FROM student";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
