package com.org.StudentAdministration.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.Optional;

import com.org.StudentAdministration.entity.LoginRequest;
import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.repository.StudentRepository;
import com.org.StudentAdministration.service.JWTService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthController authController;

    @Test
    void shouldLoginSuccessfullyWithValidCredentials() {
        String username = "murali";
        String rawPassword = "murali@123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String token = "mockJwtToken";

        Student student = new Student();
        student.setName(username);
        student.setPassword(encodedPassword);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(username);
        loginRequest.setPassword(rawPassword);

        when(studentRepository.findByName(username)).thenReturn(Optional.of(student));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(username)).thenReturn(token);

        ResponseEntity<?> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals(token, ((Map<?, ?>) response.getBody()).get("token"));
    }

    
    @Test
    void shouldReturnUnauthorizedIfUserDoesNotExist() {

        String username = "NonExistentUser";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(username);
        loginRequest.setPassword("raju@123");

        when(studentRepository.findByName(username)).thenReturn(Optional.empty());


        ResponseEntity<?> response = authController.login(loginRequest);


        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());
    }
}
