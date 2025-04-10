package com.org.StudentAdministration.repository;

import com.org.StudentAdministration.entity.Student;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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

        // Set raw password
        String rawPassword = "kaju@123";
        student.setPassword(passwordEncoder.encode(rawPassword));


        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());

        // check the stored password is not the plain text password
        assertNotEquals(rawPassword, savedStudent.getPassword());

        // Verify that the stored password matches the raw password when encoded
        assertTrue(passwordEncoder.matches(rawPassword, savedStudent.getPassword()));
    }
}
