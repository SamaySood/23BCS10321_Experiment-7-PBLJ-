package com.example.controller;

import com.example.model.Student;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");
    }
    
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    @GetMapping("/course/{course}")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String course) {
        List<Student> students = studentService.getStudentsByCourse(course);
        return ResponseEntity.ok(students);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        studentService.updateStudent(student);
        return ResponseEntity.ok("Student updated successfully");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalStudents() {
        int count = studentService.getTotalStudents();
        return ResponseEntity.ok(count);
    }
}
