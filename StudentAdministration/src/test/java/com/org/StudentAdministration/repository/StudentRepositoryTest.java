package com.org.StudentAdministration.repository;

import com.org.StudentAdministration.config.SecurityConfig;
import com.org.StudentAdministration.entity.Student;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(SecurityConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties") 
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testSaveStudent() {
        Student student = new Student();
        student.setId(65);
        student.setName("nani");
        student.setEmail("nani@gmail.com");
        student.setMobileNumber("4567890");
        student.setPassword("nani@123");

        Student savedStudent = studentRepository.save(student);
        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());
        assertEquals("nani", savedStudent.getName());
    }

    @Test
    void testSaveStudentWithEncryptedPassword() {
        Student student = new Student();
        student.setId(43);
        student.setName("kaju");
        student.setEmail("kaju@gmail.com");
        student.setMobileNumber("67677");

        String rawPassword = "kaju@123";
        student.setPassword(passwordEncoder.encode(rawPassword));


        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());

        assertNotEquals(rawPassword, savedStudent.getPassword());

        
        assertTrue(passwordEncoder.matches(rawPassword, savedStudent.getPassword()));
    }
}
