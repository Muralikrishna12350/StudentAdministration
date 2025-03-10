package com.org.StudentAdministration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.mockito.Mockito;
import java.util.Optional;

@WebMvcTest(StudentController.class) // Loads only StudentController for testing
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;  //   @MockBean to inject the mock service

    @Autowired
    private ObjectMapper objectMapper;

    @Test // this for response handling test
    void testGetStudentById_ShouldReturnStudent() throws Exception {
        // Arrange: Create a mock student
        Student student = new Student();
        student.setId(200);
        student.setName("rrr");
        student.setEmail("rrr@gmail.com");
        student.setMobileNumber("65677");
        student.setPassword("rrr@123");

        Optional<Student> optionalStudent = Optional.of(student);


        Mockito.when(studentService.getStudentById(200)).thenReturn(optionalStudent);


        mockMvc.perform(get("/students/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(200))
                .andExpect(jsonPath("$.name").value("rrr"))
                .andExpect(jsonPath("$.email").value("rrr@gmail.com"))
                .andExpect(jsonPath("$.mobileNumber").value("65677"))
                .andExpect(jsonPath("$.password").value("rrr@123"));
    }


    @Test // this is for requesting handling test
    void testCreateStudent_ShouldReturnCreated() throws Exception {

        Student student = new Student();
        student.setId(110);
        student.setName("raj");
        student.setEmail("raj@gmail.com");
        student.setMobileNumber("123456");
        student.setPassword("raj@123");


        // here Converting  to JSON
        String studentJson = objectMapper.writeValueAsString(student);

        // Mocking the service response
        Mockito.when(studentService.saveStudent(Mockito.any())).thenReturn(student);


        mockMvc.perform(post("/students")
                        .contentType("application/json")
                        .content(studentJson))
                .andExpect(status().isCreated()) // 201 response
                .andExpect(jsonPath("$.id").value(110))
                .andExpect(jsonPath("$.name").value("raj"))
                .andExpect(jsonPath("$.email").value("raj@gmail.com"))
                .andExpect(jsonPath("$.mobileNumber").value("123456"))
                .andExpect(jsonPath("$.password").value("raj@123"));
    }

    @Test // this is for Validate Request Handling PUT
    void testUpdateStudent_ShouldReturnUpdatedStudent() throws Exception {
        //  Existing student
        Student student = new Student();
        student.setId(110);
        student.setName("rajuu");
        student.setEmail("raj@gmail.com");
        student.setMobileNumber("123456");
        student.setPassword("raj@123");

        // here  Converting  to JSON
        String studentJson = objectMapper.writeValueAsString(student);

        // Mocking the service response
        Mockito.when(studentService.updateStudent(Mockito.eq(110), Mockito.any())).thenReturn(student);


        mockMvc.perform(put("/students/110")
                        .contentType("application/json")
                        .content(studentJson))
                .andExpect(status().isOk()) //  200 response
                .andExpect(jsonPath("$.name").value("rajuu"))
                .andExpect(jsonPath("$.email").value("raj@gmail.com"))
                .andExpect(jsonPath("$.email").value("rajgmail.com"))
                .andExpect(jsonPath("$.mobileNumber").value("123456"))
                .andExpect(jsonPath("$.password").value("raj@123"));
    }

}
