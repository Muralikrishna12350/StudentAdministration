package com.org.StudentAdministration.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.service.StudentService;




@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private final  StudentService studentService;
	
	public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
	
	@PostMapping
	public ResponseEntity<Student> createStudent(@RequestBody Student student)
	{
		Student savedStudent= studentService.saveStudent(student);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
	}
	
	@GetMapping
	public ResponseEntity<List<Student>> getStudents(){
		 List<Student> students = studentService.getStudents();
		 return ResponseEntity.ok(students);
		 
		 
	}
	
	 @GetMapping("{id}")
	    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
	        Optional<Student> student = studentService.getStudentById(id);	        
	     return   student.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	    }
	 
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
		Student updateStudent = studentService.updateStudent(id, student);
		return ResponseEntity.ok(updateStudent);
	}
	@PatchMapping("{id}")
	public ResponseEntity<Student> updatefarmerByfields(@PathVariable int id,@RequestBody  Map<String,Object>field) {
		Student updateStudentByFields = studentService.updateStudentByFields(id, field);
		return ResponseEntity.ok(updateStudentByFields);
	}
	
}
