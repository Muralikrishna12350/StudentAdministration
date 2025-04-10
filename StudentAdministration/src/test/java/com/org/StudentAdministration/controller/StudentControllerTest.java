package com.org.StudentAdministration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.service.JWTService;
import com.org.StudentAdministration.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.Mockito;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void testGetStudentById_ShouldReturnStudent() throws Exception {
        Student student = new Student();
        student.setId(200);
        student.setName("rrr");
        student.setEmail("rrr@gmail.com");
        student.setMobileNumber("65677");
        student.setPassword("rrr@123");

        Mockito.when(studentService.getStudentById(200)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(200))
                .andExpect(jsonPath("$.name").value("rrr"))
                .andExpect(jsonPath("$.email").value("rrr@gmail.com"))
                .andExpect(jsonPath("$.mobileNumber").value("65677"))
                .andExpect(jsonPath("$.password").value("rrr@123"));
    }

    @Test
    void testCreateStudent_ShouldReturnCreated() throws Exception {
        Student student = new Student();
        student.setId(110);
        student.setName("raj");
        student.setEmail("raj@gmail.com");
        student.setMobileNumber("123456");
        student.setPassword("raj@123");

        String studentJson = objectMapper.writeValueAsString(student);

        Mockito.when(studentService.saveStudent(Mockito.any())).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType("application/json")
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(110))
                .andExpect(jsonPath("$.name").value("raj"))
                .andExpect(jsonPath("$.email").value("raj@gmail.com"))
                .andExpect(jsonPath("$.mobileNumber").value("123456"))
                .andExpect(jsonPath("$.password").value("raj@123"));
    }

    @Test
    void testUpdateStudent_ShouldReturnUpdatedStudent() throws Exception {
        Student student = new Student();
        student.setId(110);
        student.setName("rajuu");
        student.setEmail("raj@gmail.com");
        student.setMobileNumber("123456");
        student.setPassword("raj@123");

        String studentJson = objectMapper.writeValueAsString(student);

        Mockito.when(studentService.updateStudent(Mockito.eq(110), Mockito.any())).thenReturn(student);

        mockMvc.perform(put("/students/110")
                        .contentType("application/json")
                        .content(studentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("rajuu"))
                .andExpect(jsonPath("$.email").value("raj@gmail.com"))
                .andExpect(jsonPath("$.mobileNumber").value("123456"))
                .andExpect(jsonPath("$.password").value("raj@123"));
    }
}
