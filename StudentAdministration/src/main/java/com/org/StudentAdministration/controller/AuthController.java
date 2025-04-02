package com.org.StudentAdministration.controller;

import com.org.StudentAdministration.entity.LoginRequest;
import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.repository.StudentRepository;
import com.org.StudentAdministration.service.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTService jwtService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Student student) {
		if (studentRepository.findByName(student.getName()).isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("name already registered!");
		}

		student.setPassword(passwordEncoder.encode(student.getPassword()));
		student.setRole("ROLE_USER");
		studentRepository.save(student);

		return ResponseEntity.ok("User registered successfully!");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Optional<Student> studentOpt = studentRepository.findByName(loginRequest.getName());

		if (studentOpt.isPresent()) {
			Student student = studentOpt.get();
			if (passwordEncoder.matches(loginRequest.getPassword(), student.getPassword())) {
				String token = jwtService.generateToken(student.getName());
				return ResponseEntity.ok(Map.of("token", token));
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
	}
}
