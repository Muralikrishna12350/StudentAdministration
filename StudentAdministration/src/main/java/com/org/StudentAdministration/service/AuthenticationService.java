package com.org.StudentAdministration.service;


import com.org.StudentAdministration.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private final AuthenticationManager authManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    public AuthenticationService(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    public String verify(Student student) {
        Authentication authentication=
                authManager.authenticate(new UsernamePasswordAuthenticationToken(student.getName(), student.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(student.getName());
        }
        else return "fail";
    }
}
