package com.example.service;

import com.example.dao.StudentDao;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentService {
    
    @Autowired
    private StudentDao studentDao;
    
    public void addStudent(Student student) {
        studentDao.create(student);
    }
    
    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }
    
    public Student getStudentById(Long id) {
        return studentDao.findById(id);
    }
    
    public List<Student> getStudentsByCourse(String course) {
        return studentDao.findByCourse(course);
    }
    
    public void updateStudent(Student student) {
        studentDao.update(student);
    }
    
    public void deleteStudent(Long id) {
        studentDao.deleteById(id);
    }
    
    public int getTotalStudents() {
        return studentDao.count();
    }
}
