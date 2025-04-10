package com.org.StudentAdministration.service;


import com.org.StudentAdministration.entity.Student;
import com.org.StudentAdministration.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentDetailService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(StudentDetailService.class);
    @Autowired
    private StudentRepository studentRepository;

@Override
public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    Student student = studentRepository.findByName(name)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + name));
    return new org.springframework.security.core.userdetails.User(
            student.getName(), student.getPassword(),
            List.of(new SimpleGrantedAuthority(student.getRole()))
    );
}
}
