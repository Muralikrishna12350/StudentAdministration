package com.org.StudentAdministration.service;

import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentDetailServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentDetailService studentDetailService;

    private Student student;

    @BeforeEach
    void setUp(){
         student=new Student();
         student.setName("testuser");
         student.setPassword("testuser@123");
         student.setEmail("testuser@gmail.com");
         student.setMobileNumber("8765670");
         student.setRole("ROLE_USER");
    }

    @Test
    void shouldLoadUserNameWhenUserExists(){

        when(studentRepository.findByName("testuser")).thenReturn(Optional.of(student));

        UserDetails userDetails= studentDetailService.loadUserByUsername("testuser");

        assertNotNull(userDetails);
        assertEquals(student.getName(), userDetails.getUsername());
        assertEquals(student.getPassword(), userDetails.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound(){
        when(studentRepository.findByName("unknownname")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                        studentDetailService
                                .loadUserByUsername("unknownname")

        );
    }

}
