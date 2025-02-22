package com.org.StudentAdministration.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.service.StudentService;


@RestController
public class StudentController {

	@Autowired
	public  StudentService studentService;
	
	@PostMapping("/saved")
	public String createStudent(@RequestBody Student student)
	{
		studentService.saveStudent(student);
		
		return "student saved";
	}
	
	@GetMapping("/getallstudents")
	public List<Student> getStudents(){
		return studentService.getStudents();
	}
	
	 @GetMapping("/getonestudent/{id}")
	    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
	        Optional<Student> student = studentService.getStudentById(id);
	        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	 
	@DeleteMapping("/delete/{id}")
	public String deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
		return "deleted";
		
	}
	
	@PutMapping("/update/{id}")
	public String updateStudent(@PathVariable int id, @RequestBody Student student) {
		studentService.updateStudent(id, student);
		return "updated";
	}
	@PatchMapping("/updatebyfields/{id}")
	public String updatefarmerByfields(@PathVariable int id,@RequestBody  Map<String,Object>field) {
		studentService.updateStudentByFields(id, field);
		return "fields updated";
	}
	
}
