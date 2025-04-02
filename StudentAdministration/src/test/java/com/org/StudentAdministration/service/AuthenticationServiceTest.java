package com.org.StudentAdministration.service;

import com.org.StudentAdministration.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setName("testuser");
        student.setPassword("testuser123");
        student.setEmail("testuser@gmail.com");
        student.setMobileNumber("87656789");
        student.setRole("ROLE_USER");

    }

    @Test
    void shouldReturnFailWhenAuthenticationFails() {
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        String result = authenticationService.verify(student);
        assertEquals("fail", result);
    }
}
