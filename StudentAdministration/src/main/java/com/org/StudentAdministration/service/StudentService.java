package com.org.StudentAdministration.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.repository.StudentRepository;


@Service
public class StudentService {
	
private StudentRepository studentRepository;



       @Autowired
       public StudentService(StudentRepository studentRepository) {
          this.studentRepository = studentRepository;
       }

       public void saveStudent(Student student) {
          studentRepository.save(student);
       }
       
       public List<Student> getStudents(){
    	   return studentRepository.findAll();
       }
       
       public Optional<Student> getStudentById(int id) {
    	   return studentRepository.findById(id);
       }
       
       public Student updateStudent(int id, Student student) {
    	   if(studentRepository.existsById(id)) {
    		   student.setId(id);
    		   return studentRepository.save(student);
    	   }
    	   return null;
       }
       public void deleteStudent(int id) {
           studentRepository.deleteById(id);
       }
       

       public Student updateStudentByFields(int id,Map<String,Object>field) {
    	   
    	   Optional<Student> ep=studentRepository.findById(id);
      		if(ep.isPresent()) {
      			field.forEach((key,value)->{;
      			Field f=ReflectionUtils.findRequiredField(Student.class, key);
      			 f.setAccessible(true);
      			 ReflectionUtils.setField(f, ep.get(), value);
      			});
      		return studentRepository.save(ep.get());
      		}

		return null;
    	   
    	   
       }

}
