package com.org.StudentAdministration.controller;

import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.entity.User;
import com.org.StudentAdministration.repository.StudentRepository;
import com.org.StudentAdministration.repository.UserRepository;
import com.org.StudentAdministration.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists!");
        }


         user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);


        Student student = new Student();

        student.setName(user.getName());
        student.setEmail(user.getName() + "@gmail.com");
        student.setMobileNumber("1234567890");
        student.setPassword(user.getPassword());

        studentRepository.save(student); // Save Student

        return ResponseEntity.ok("User and Student registered successfully!");
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        UserDetails userDetails = userRepository.findByName(user.getName())
                .map(u -> new org.springframework.security.core.userdetails.User(
                        u.getName(),
                        u.getPassword(),
                        Collections.emptyList()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getName());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}

